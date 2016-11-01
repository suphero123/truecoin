package org.truechain.transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.truechain.account.Account;
import org.truechain.core.UnsafeByteArrayOutputStream;
import org.truechain.core.exception.ProtocolException;
import org.truechain.crypto.ECKey;
import org.truechain.crypto.ECKey.ECDSASignature;
import org.truechain.crypto.Sha256Hash;
import org.truechain.network.NetworkParameters;
import org.truechain.script.Script;
import org.truechain.script.ScriptBuilder;
import org.truechain.script.ScriptOpCodes;
import org.truechain.transaction.Transaction.SigHash;
import org.truechain.utils.Utils;

/**
 * 帐户注册交易
 * @author ln
 *
 */
public class RegisterTransaction extends Transaction {

	//交易输入
	private List<RegisterInput> inputs;
	//交易输出
	private List<RegisterOutput> outputs;
	
	//帐户信息
	private Account account;

	public RegisterTransaction(NetworkParameters network, Account account) {
		super(network);
		this.setVersion(VERSION_REGISTER);
		this.account = account;
		this.inputs = new ArrayList<RegisterInput>();
		RegisterInput input = new RegisterInput(account);
		this.inputs.add(input);
		
		this.outputs = new ArrayList<RegisterOutput>();
		RegisterOutput output = new RegisterOutput(account);
		this.outputs.add(output);
	}
	
	public RegisterTransaction(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes);
    }
	

	@Override
	protected TransactionInput parseInput() {
		return super.parseInput();
	}
	
	@Override
	protected TransactionOutput parseOutput() {
		return super.parseOutput();
	}

	/**
	 * 更新交易签名
	 * @param prikey1
	 * @param prikey2
	 */
	public void calculateSignature(ECKey key1, ECKey key2) {
		Sha256Hash hash = Sha256Hash.of(baseSerialize());
		//签名
		ECDSASignature ecSign1 = key1.sign(hash);
		byte[] sign1 = ecSign1.encodeToDER();
		
		ECDSASignature ecSign2 = key2.sign(hash);
		byte[] sign2 = ecSign2.encodeToDER();
		
		inputs.get(0).setScriptSig(
				ScriptBuilder.createRegisterInputScript(account.getAddress().getHash160(), 
						new byte[][] {sign1, sign2}, getVersion()));
		
	}
}
