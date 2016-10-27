package org.truechain.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.truechain.address.Address;
import org.truechain.core.Coin;
import org.truechain.core.exception.ProtocolException;
import org.truechain.crypto.ECKey;
import org.truechain.crypto.Sha256Hash;
import org.truechain.message.Message;
import org.truechain.network.NetworkParameters;
import org.truechain.script.Script;

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
	
	private long lockTime;
	
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

	@Override
	protected void parse() throws ProtocolException {
		
	}

	public Sha256Hash hashForSignature(int index, byte[] connectedScript, byte sighashFlags) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 添加输入
	 * @param output
	 */
	public TransactionInput addInput(TransactionOutput output) {
		return addInput(new TransactionInput());
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
    
    public List<TransactionInput> getInput() {
		return inputs;
	}
    
    public List<TransactionOutput> getOutput() {
		return outputs;
	}

    public long getLockTime() {
		return lockTime;
	}
    public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}

	
}
