package org.truechain.transaction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.account.Account;
import org.truechain.account.Account.AccountType;
import org.truechain.account.Address;
import org.truechain.core.exception.ProtocolException;
import org.truechain.core.exception.VerificationException;
import org.truechain.crypto.ECKey;
import org.truechain.crypto.ECKey.ECDSASignature;
import org.truechain.crypto.Sha256Hash;
import org.truechain.network.NetworkParameters;
import org.truechain.script.Script;
import org.truechain.script.ScriptBuilder;
import org.truechain.script.ScriptChunk;
import org.truechain.utils.Utils;

/**
 * 帐户注册交易
 * @author ln
 *
 */
public class RegisterTransaction extends Transaction {
	
	private final static Logger log = LoggerFactory.getLogger(RegisterTransaction.class);

	//帐户信息
	private Account account;

	public RegisterTransaction(NetworkParameters network, Account account) {
		super(network);
		this.setVersion(VERSION);
		this.setType(TYPE_REGISTER);
		this.account = account;
		this.inputs = new ArrayList<Input>();
		RegisterInput input = new RegisterInput(account);
		this.inputs.add(input);
		
		this.outputs = new ArrayList<Output>();
		RegisterOutput output = new RegisterOutput(account);
		this.outputs.add(output);
	}
	
	public RegisterTransaction(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
		this(params, payloadBytes, 0);
    }
	
	public RegisterTransaction(NetworkParameters params, byte[] payloadBytes, int offset) throws ProtocolException {
		super(params, payloadBytes, offset);
		this.setType(TYPE_REGISTER);
	}
	
	/**
	 * 反序列化交易的输出部分
	 * @return
	 */
	protected TransactionOutput parseOutput() {
        //赎回脚本名的长度
        int signLength = (int)readVarInt();
        byte[] signbs = readBytes(signLength);
        
        Script script = new Script(signbs);
        
		RegisterOutput output = new RegisterOutput(script);
        output.setParent(this);
        output.setAccount(account);
        
        List<ScriptChunk> chunks = script.getChunks();
        
        account.setAddress(Address.fromP2PKHash(network, account.getAccountType().value(), chunks.get(0).data));
        account.setMgPubkeys(new byte[][] { chunks.get(4).data, chunks.get(5).data});
        account.setTrPubkeys(new byte[][] { chunks.get(7).data, chunks.get(8).data});
        
        return output;
	}
	
	/**
	 * 反序列化交易的输入部分
	 * @return 
	 * @return
	 */
	protected <T extends Input> Input parseInput() {
		if(account == null)
			account = new Account();
		RegisterInput input = new RegisterInput(account);
        input.setParent(this);
        
        Utils.checkNotNull(account);
        
        byte type = payload[cursor];
		if(type == AccountType.SYSTEM.value()) {
			account.setAccountType(AccountType.SYSTEM);
		} else if(type == AccountType.CERT.value()) {
			account.setAccountType(AccountType.CERT);
		}
		cursor ++;
        
		//帐户主体
		long bodyLength = readVarInt();
		account.setBody(readBytes((int)bodyLength));
		
        //签名
		long signLength = readVarInt();
		input.setScriptBytes(readBytes((int)signLength));
		
        return input;
	}
	
	/**
	 * 验证交易的合法性
	 */
	public void verfify() throws VerificationException {
		
	}
	
	/**
	 * 更新交易签名
	 * @param prikey1
	 * @param prikey2
	 */
	public void calculateSignature(ECKey key1, ECKey key2) {
		Script script = ScriptBuilder.createEmptyInputScript(Transaction.TYPE_REGISTER, account.getAddress().getHash160());
		inputs.get(0).setScriptSig(script);
		
		Sha256Hash hash = Sha256Hash.of(baseSerialize());
		
		//签名
		ECDSASignature ecSign1 = key1.sign(hash);
		byte[] sign1 = ecSign1.encodeToDER();
		
		ECDSASignature ecSign2 = key2.sign(hash);
		byte[] sign2 = ecSign2.encodeToDER();
		
		inputs.get(0).setScriptSig(
				ScriptBuilder.createRegisterInputScript(account.getAddress().getHash160(), 
						new byte[][] {sign1, sign2}, getType()));
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
