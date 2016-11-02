package org.truechain.transaction;

import java.io.IOException;
import java.io.OutputStream;

import org.truechain.account.Account;
import org.truechain.core.VarInt;
import org.truechain.script.Script;
import org.truechain.script.ScriptBuilder;
import org.truechain.utils.Utils;

public class RegisterOutput extends TransactionOutput {

	private Account account;
	
	public RegisterOutput(Account account) {
		this.account = account;
		this.setScript(ScriptBuilder.createRegisterOutScript(account.getAddress().getHash160(), 
				account.getMgPubkeys(), account.getTrPubkeys()));
	}
	
	public RegisterOutput(Script script) {
		this.setScript(script);
	}
	
	/**
	 * 序列化交易输出
	 * @param stream
	 * @throws IOException 
	 */
	public void serialize(OutputStream stream) throws IOException {
		Utils.checkNotNull(account);
		
		setScript(ScriptBuilder.createRegisterOutScript(account.getAddress().getHash160(), 
				account.getMgPubkeys(), account.getTrPubkeys()));
		byte[] sb = getScriptBytes();
		stream.write(new VarInt(sb.length).encode());
		stream.write(sb);
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
}
