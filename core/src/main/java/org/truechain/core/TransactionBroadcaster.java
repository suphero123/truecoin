package org.truechain.core;

import org.truechain.transaction.Transaction;

public interface TransactionBroadcaster {
    TransactionBroadcast broadcastTransaction(final Transaction tx);
}
