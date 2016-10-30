package org.truechain.store;

import org.truechain.transaction.Transaction;

public class TransactionStore extends Store {
	
	//转出未确认
	public final static int STATUS_SEND_UNCONFIRMED = 1;
	//转入未确认
	public final static int STATUS_RECE_UNCONFIRMED = 2;
	//转出已确认
	public final static int STATUS_SEND_CONFIRMED = 3;
	//转入已确认
	public final static int STATUS_RECE_CONFIRMED = 4;
	
	//交易内容
	private Transaction transaction;
	//交易状态
	private byte status;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	@Override
	public byte[] getKey() {
		if(key == null) {
			key = transaction.getHash().getBytes();
		}
		return key;
	}
	
}
