package org.truechain.account;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户
 * @author ln
 *
 */
public class Account {

	public static enum AccountType {
		SYSTEM(1),		//系统帐户
		CONTRACT(2),	//合约帐户
		APP(3),			//应用账户
		CERT(9),		//认证帐户
		;
		
		private final int value;
        private AccountType(final int value) {
            this.value = value;
        }
        public byte value() {
            return (byte) this.value;
        }
	}
	
	//账户类型
	private AccountType accountType;
	//帐户地址
	private Address address;
	//帐户主体
	private byte[] body;
	
	public Account() {
		accountType = AccountType.SYSTEM;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
}
