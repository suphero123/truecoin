package org.truechain.transaction;

import java.io.IOException;
import java.io.OutputStream;

import org.truechain.account.Account;
import org.truechain.script.ScriptBuilder;
import org.truechain.utils.Utils;

public class RegisterOutput extends TransactionOutput {

	private Account account;
	
	public RegisterOutput(Account account) {
		this.account = account;
		this.setScript(ScriptBuilder.createRegisterOutScript(account.getAddress().getHash160(), 
				account.getMgPubkeys(), account.getTrPubkeys()));
	}
	
	/**
	 * 序列化交易输出
	 * @param stream
	 * @throws IOException 
	 */
	public void serialize(OutputStream stream) throws IOException {
		Utils.checkNotNull(account);
		ScriptBuilder.createRegisterOutScript(account.getAddress().getHash160(), account.getMgPubkeys(), account.getTrPubkeys());
	}
}
