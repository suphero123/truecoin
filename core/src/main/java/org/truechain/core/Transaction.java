package org.truechain.core;

import java.math.BigInteger;
import java.util.ArrayList;

import org.truechain.core.exception.ProtocolException;
import org.truechain.crypto.TransactionSignature;
import org.truechain.message.Message;
import org.truechain.network.NetworkParameters;
import org.truechain.script.Script;

public class Transaction extends Message {
	
	public static final int LOCKTIME_THRESHOLD = 500000000;
	public static final BigInteger LOCKTIME_THRESHOLD_BIG = BigInteger.valueOf(LOCKTIME_THRESHOLD);
	
	private NetworkParameters network;
	
	private long lockTime;
	
	private long version;
    private ArrayList<TransactionInput> inputs;
    private ArrayList<TransactionOutput> outputs;

	@Override
	protected void parse() throws ProtocolException {
		
	}
	
	/**
     * <p>Calculates a signature hash, that is, a hash of a simplified form of the transaction. How exactly the transaction
     * is simplified is specified by the type and anyoneCanPay parameters.</p>
     *
     * <p>This is a low level API and when using the regular {@link Wallet} class you don't have to call this yourself.
     * When working with more complex transaction types and contracts, it can be necessary. When signing a P2SH output
     * the redeemScript should be the script encoded into the scriptSig field, for normal transactions, it's the
     * scriptPubKey of the output you're signing for.</p>
     *
     * @param inputIndex input the signature is being calculated for. Tx signatures are always relative to an input.
     * @param redeemScript the bytes that should be in the given input during signing.
     * @param type Should be SigHash.ALL
     * @param anyoneCanPay should be false.
     */
    public Sha256Hash hashForSignature(int inputIndex, byte[] redeemScript,
                                                    SigHash type, boolean anyoneCanPay) {
        byte sigHashType = (byte) TransactionSignature.calcSigHashValue(type, anyoneCanPay);
        return hashForSignature(inputIndex, redeemScript, sigHashType);
    }

    /**
     * <p>Calculates a signature hash, that is, a hash of a simplified form of the transaction. How exactly the transaction
     * is simplified is specified by the type and anyoneCanPay parameters.</p>
     *
     * <p>This is a low level API and when using the regular {@link Wallet} class you don't have to call this yourself.
     * When working with more complex transaction types and contracts, it can be necessary. When signing a P2SH output
     * the redeemScript should be the script encoded into the scriptSig field, for normal transactions, it's the
     * scriptPubKey of the output you're signing for.</p>
     *
     * @param inputIndex input the signature is being calculated for. Tx signatures are always relative to an input.
     * @param redeemScript the script that should be in the given input during signing.
     * @param type Should be SigHash.ALL
     * @param anyoneCanPay should be false.
     */
    public Sha256Hash hashForSignature(int inputIndex, Script redeemScript,
                                                    SigHash type, boolean anyoneCanPay) {
        int sigHash = TransactionSignature.calcSigHashValue(type, anyoneCanPay);
        return hashForSignature(inputIndex, redeemScript.getProgram(), (byte) sigHash);
    }

    /**
     * This is required for signatures which use a sigHashType which cannot be represented using SigHash and anyoneCanPay
     * See transaction c99c49da4c38af669dea436d3e73780dfdb6c1ecf9958baa52960e8baee30e73, which has sigHashType 0
     */
    public Sha256Hash hashForSignature(int inputIndex, byte[] connectedScript, byte sigHashType) {
    	return null;
    }
    
	/**
     * These constants are a part of a scriptSig signature on the inputs. They define the details of how a
     * transaction can be redeemed, specifically, they control how the hash of the transaction is calculated.
     */
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

    public TransactionInput getInput(long index) {
        return inputs.get((int)index);
    }
    
    public long getLockTime() {
        return lockTime;
    }
    
    public NetworkParameters getNetwork() {
		return network;
	}
}
