package org.truechain.transaction;

import org.truechain.address.Address;
import org.truechain.core.Coin;
import org.truechain.crypto.ECKey;
import org.truechain.script.Script;
import org.truechain.script.ScriptBuilder;

/**
 * 交易输出，本次的输出是下次花费时的输入
 * @author ln
 *
 */
public class TransactionOutput {

	private Transaction parent;
	//下次的花费
	private TransactionInput spentBy;
	
	private long value;

    private byte[] scriptBytes;
    
    private Script script;

	
	public TransactionOutput(Transaction parent, Coin value, Address to) {
		this(parent, value, ScriptBuilder.createOutputScript(to).getProgram());
	}
	public TransactionOutput(Transaction parent, Coin value, ECKey to) {
		this(parent, value, ScriptBuilder.createOutputScript(to).getProgram());
	}
	public TransactionOutput(Transaction parent, Coin value, byte[] scriptBytes) {
		this.parent = parent;
		this.value = value.value;
        this.scriptBytes = scriptBytes;
        this.script = new Script(scriptBytes);
	}
	
	public Transaction getParent() {
		return parent;
	}
	public void setParent(Transaction parent) {
		this.parent = parent;
	}
	public TransactionInput getSpentBy() {
		return spentBy;
	}
	public void setSpentBy(TransactionInput spentBy) {
		this.spentBy = spentBy;
	}
	public Script getScript() {
		return script;
	}
	public void setScript(Script script) {
		this.script = script;
	}
	
}
