package org.truechain.signers;

import org.truechain.crypto.ECKey;
import org.truechain.transaction.Transaction;

public interface TransactionSigner {

    boolean isReady();

    byte[] serialize();

    void deserialize(byte[] data);

    boolean signInputs(Transaction tx, ECKey key);

}
