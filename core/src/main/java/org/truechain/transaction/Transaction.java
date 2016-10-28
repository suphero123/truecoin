package org.truechain.transaction;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.truechain.account.AccountManager;
import org.truechain.address.Address;
import org.truechain.core.Coin;
import org.truechain.core.UnsafeByteArrayOutputStream;
import org.truechain.core.VarInt;
import org.truechain.core.exception.ProtocolException;
import org.truechain.core.exception.VerificationException;
import org.truechain.crypto.ECKey;
import org.truechain.crypto.Sha256Hash;
import org.truechain.crypto.TransactionSignature;
import org.truechain.message.Message;
import org.truechain.network.NetworkParameters;
import org.truechain.script.Script;
import org.truechain.script.ScriptBuilder;
import org.truechain.script.ScriptOpCodes;
import org.truechain.utils.Utils;

/**
 * 交易
 * @author ln
 *
 */
public class Transaction extends Message {

	//锁定时间标识，小于该数表示为块数，大于则为秒级时间戳
	public static final int LOCKTIME_THRESHOLD = 500000000;
    public static final BigInteger LOCKTIME_THRESHOLD_BIG = BigInteger.valueOf(LOCKTIME_THRESHOLD);
    //允许的交易最大值
    public static final int MAX_STANDARD_TX_SIZE = 100000;
    
	//交易输入
	private List<TransactionInput> inputs;
	//交易输出
	private List<TransactionOutput> outputs;
	
	//tx hash
	private Sha256Hash hash;
	private long lockTime;
	//交易版本
	private long version;
	
	public enum SigHash {
        ALL(1),
        NONE(2),
        SINGLE(3),
        ANYONECANPAY(0x80), // Caution: Using this type in isolation is non-standard. Treated similar to ANYONECANPAY_ALL.
        ANYONECANPAY_ALL(0x81),
        ANYONECANPAY_NONE(0x82),
        ANYONECANPAY_SINGLE(0x83),
        UNSET(0); // Caution: Using this type in isolation is non-standard. Treated similar to ALL.

        public final int value;

        /**
         * @param value
         */
        private SigHash(final int value) {
            this.value = value;
        }

        /**
         * @return the value as a byte
         */
        public byte byteValue() {
            return (byte) this.value;
        }
    }
	
	public Transaction(NetworkParameters network) {
		super(network);
		inputs = new ArrayList<TransactionInput>();
        outputs = new ArrayList<TransactionOutput>();
	}
	
	public Transaction(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

	/**
	 * 序列化
	 */
	protected void serializeToStream(OutputStream stream) throws IOException {
		Utils.uint32ToByteStreamLE(version, stream);
        stream.write(new VarInt(inputs.size()).encode());
        for (TransactionInput in : inputs)
            in.serialize(stream);
        stream.write(new VarInt(outputs.size()).encode());
        for (TransactionOutput out : outputs)
            out.serialize(stream);
        Utils.uint32ToByteStreamLE(lockTime, stream);
    }
	
	/**
	 * 反序列化交易
	 */
	@Override
	protected void parse() throws ProtocolException {
		cursor = offset;
		
		version = readUint32();

		//交易输入数量
        long numInputs = readVarInt();
        inputs = new ArrayList<TransactionInput>((int) numInputs);
        for (int i = 0; i < numInputs; i++) {
        	
            TransactionInput input = new TransactionInput();
            input.setParent(this);
            
            //上笔交易的引用
            TransactionOutput pre = new TransactionOutput();
            Transaction t = new Transaction(network);
            pre.setParent(t);
            pre.getParent().setHash(Sha256Hash.wrap(readBytes(32)));
            pre.setIndex((int)readUint32());
            
            input.setFrom(pre);
            
            //输入签名的长度
            int signLength = (int)readVarInt();
            input.setScriptBytes(readBytes(signLength));
            input.setSequence(readUint32());

            //通过公匙生成赎回脚本
            ECKey key = ECKey.fromPublicOnly(input.getScriptSig().getPubKey());

            //TODO 根据交易类型，生成对应的赎回脚本
            
    		Script script = ScriptBuilder.createOutputScript(
    				AccountManager.newAddressFromKey(network, (int)version, key));
    		
            pre.setScript(script);
            
            inputs.add(input);
        }

		//交易输出数量
        long numOutputs = readVarInt();
        outputs = new ArrayList<TransactionOutput>((int) numOutputs);
        for (int i = 0; i < numOutputs; i++) {
        	
            TransactionOutput output = new TransactionOutput();
            output.setParent(this);
            output.setIndex(i);
            output.setValue(readInt64());
            //赎回脚本名的长度
            int signLength = (int)readVarInt();
            output.setScriptBytes(readBytes(signLength));
            
            outputs.add(output);
        }
        lockTime = readUint32();
        length = cursor - offset;
	}

	/**
	 * 验证交易的合法性
	 */
	public void verfify() throws VerificationException {
		
	}

	public Sha256Hash hashForSignature(int index, byte[] redeemScript, byte sigHashType) {
		try {
//            Transaction tx = this.network.getDefaultSerializer().makeTransaction(this.baseSerialize());
            Transaction tx = this;

            //清空输入脚本
            for (int i = 0; i < tx.inputs.size(); i++) {
                tx.getInputs().get(i).clearScriptBytes();
            }

            //清除上次交易脚本里的操作码
            redeemScript = Script.removeAllInstancesOfOp(redeemScript, ScriptOpCodes.OP_CODESEPARATOR);

            TransactionInput input = tx.inputs.get(index);
            input.setScriptBytes(redeemScript);

            if ((sigHashType & 0x1f) == SigHash.NONE.value) {
            	//TODO
            	
            } else if ((sigHashType & 0x1f) == SigHash.SINGLE.value) {
            	//TODO
            }

            ByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(tx.length == UNKNOWN_LENGTH ? 256 : tx.length + 4);
            tx.serializeToStream(bos);
            //把hash的类型加到最后
            Utils.uint32ToByteStreamLE(0x000000ff & sigHashType, bos);
            //计算交易内容的sha256 hash
            Sha256Hash hash = Sha256Hash.twiceOf(bos.toByteArray());
            bos.close();
            return hash;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	public Sha256Hash hashForSignature(int inputIndex, byte[] redeemScript, SigHash type) {
		byte sigHashType = (byte) TransactionSignature.calcSigHashValue(type);
		return hashForSignature(inputIndex, redeemScript, sigHashType);
	}
	
	/**
	 * 签名交易
	 * @param inputIndex
	 * @param key			密匙
	 * @param redeemScript	上次交易的赎回脚本
	 * @param hashType		hash类型
	 * @return
	 */
	public TransactionSignature calculateSignature(int inputIndex, ECKey key, byte[] redeemScript, SigHash hashType) {
		Sha256Hash hash = hashForSignature(inputIndex, redeemScript, hashType);
		return new TransactionSignature(key.sign(hash), hashType);
	}
	
	/**
	 * 添加输入
	 * @param output
	 */
	public TransactionInput addInput(TransactionOutput output) {
		return addInput(new TransactionInput(output));
	}

	/**
	 * 添加输入
	 * @param input
	 */
    public TransactionInput addInput(TransactionInput input) {
        input.setParent(this);
        inputs.add(input);
        return input;
    }
    
    /**
     * 添加输出
     * @param output
     * @return
     */
	public TransactionOutput addOutput(TransactionOutput output) {
		output.setParent(this);
		outputs.add(output);
        return output;
	}
	
	/**
	 * 输出到指定地址
	 * @param value
	 * @param address
	 * @return
	 */
	public TransactionOutput addOutput(Coin value, Address address) {
        return addOutput(new TransactionOutput(this, value, address));
    }

	/**
	 * 输出到pubkey
	 * @param value
	 * @param pubkey
	 * @return
	 */
	public TransactionOutput addOutput(Coin value, ECKey pubkey) {
        return addOutput(new TransactionOutput(this, value, pubkey));
    }

	/**
	 * 输出到脚本
	 * @param value
	 * @param script
	 * @return
	 */
    public TransactionOutput addOutput(Coin value, Script script) {
        return addOutput(new TransactionOutput(this, value, script.getProgram()));
    }
    
    public TransactionInput getInput(int index) {
        return inputs.get(index);
    }

    public TransactionOutput getOutput(int index) {
        return outputs.get(index);
    }
    
    public List<TransactionInput> getInputs() {
		return inputs;
	}
    
    public List<TransactionOutput> getOutputs() {
		return outputs;
	}

    public long getLockTime() {
		return lockTime;
	}
    public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}

	public Sha256Hash getHash() {
		if (hash == null) {
            hash = Sha256Hash.wrapReversed(Sha256Hash.hashTwice(unsafeBitcoinSerialize()));
        }
		return hash;
	}
	public void setHash(Sha256Hash hash) {
		this.hash = hash;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public long getVersion() {
		return version;
	}
	
}