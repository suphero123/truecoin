package org.truechain.transaction;

/**
 * 交易输入，本次的输入是上次的输出
 * @author ln
 *
 */
public class TransactionInput {

	private Transaction parent;
	//上次的输出
	private TransactionOutput from;
	
	private long sequence;

	public TransactionInput() {
	}
	
	public TransactionInput(TransactionOutput from) {
		super();
		this.from = from;
	}
	public TransactionOutput getFrom() {
		return from;
	}
	public void setFrom(TransactionOutput from) {
		this.from = from;
	}
	public Transaction getParent() {
		return parent;
	}
	public void setParent(Transaction parent) {
		this.parent = parent;
	}
	public boolean hasSequence() {
        return sequence > 0;
    }
}
