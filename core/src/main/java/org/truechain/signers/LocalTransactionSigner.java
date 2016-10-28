package org.truechain.signers;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.account.RedeemData;
import org.truechain.core.exception.ScriptException;
import org.truechain.crypto.ECKey;
import org.truechain.crypto.TransactionSignature;
import org.truechain.script.Script;
import org.truechain.script.Script.VerifyFlag;
import org.truechain.transaction.Transaction;
import org.truechain.transaction.TransactionInput;

public class LocalTransactionSigner implements TransactionSigner {
	
    private static final Logger log = LoggerFactory.getLogger(LocalTransactionSigner.class);

    private static final EnumSet<VerifyFlag> ALLOW_VERIFY_FLAGS = EnumSet.of(VerifyFlag.P2SH,
            VerifyFlag.NULLDUMMY);

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean signInputs(Transaction tx, ECKey key) {
        int numInputs = tx.getInputs().size();
        for (int i = 0; i < numInputs; i++) {
            TransactionInput txIn = tx.getInput(i);
            if (txIn.getFrom() == null) {
                log.warn("缺少上次交易的引用,index:{}", i);
                continue;
            }

            try {
                txIn.getScriptSig().correctlySpends(tx, i, txIn.getFrom().getScript(), ALLOW_VERIFY_FLAGS);
                log.warn("已经签名,index:{}", i);
                continue;
            } catch (ScriptException e) {
//            	e.printStackTrace();
            }

            RedeemData redeemData = txIn.getFrom().getRedeemData(key);

            if ((key = redeemData.getFullKey()) == null) {
                log.warn("No local key found for input {}", i);
                continue;
            }

            Script inputScript = txIn.getScriptSig();
            Script redeemScript = redeemData.redeemScript;
            
            byte[] script = redeemScript.getProgram();
            try {
                TransactionSignature signature = tx.calculateSignature(i, key, script, Transaction.SigHash.ALL);
                int sigIndex = 0;
                inputScript = redeemScript.getScriptSigWithSignature(inputScript, signature.encode(), sigIndex);
                txIn.setScriptSig(inputScript);
            } catch (ECKey.MissingPrivateKeyException e) {
                log.warn("No private key in keypair for input {}", i);
            }

        }
        return true;
    }

	@Override
	public byte[] serialize() {
		return null;
	}

	@Override
	public void deserialize(byte[] data) {
		
	}

}