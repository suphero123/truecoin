package org.truechain.transaction;

import java.io.IOException;
import java.io.OutputStream;

import org.truechain.account.Account;
import org.truechain.core.VarInt;
import org.truechain.utils.Utils;

public class RegisterInput extends TransactionInput {

	private Account account;
	
	public RegisterInput(Account account) {
		this.account = account;
	}
	
	/**
	 * 序列化交易输入
	 * @param stream
	 * @throws IOException 
	 */
	public void serialize(OutputStream stream) throws IOException {
		//上一交易的引用
		Utils.checkNotNull(account);
		stream.write(account.getAccountType().value());
		//帐户主体
		byte[] body = account.getBody();
		if(body != null) {
			stream.write(new VarInt(body.length).encode());
			stream.write(body);
		}
        //签名
		byte[] sign = getScriptBytes();
		stream.write(new VarInt(sign.length).encode());
		stream.write(sign);
	}
	
}
