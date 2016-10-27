package org.truechain.core;

import org.junit.Test;
import org.truechain.account.AccountManager;
import org.truechain.address.Address;
import org.truechain.crypto.ECKey;
import org.truechain.network.MainNetParams;
import org.truechain.script.Script;
import org.truechain.script.ScriptBuilder;
import org.truechain.transaction.Transaction;
import org.truechain.transaction.TransactionInput;
import org.truechain.transaction.TransactionOutput;

public class TranslationTest {

	@Test
	public void testTranslation() {
		
		MainNetParams network = MainNetParams.get();
		
        Address addr = AccountManager.newAddress(network);

		//上次交易
		Transaction out = new Transaction(network);
		Transaction tx = new Transaction(network);
		
		Script script = ScriptBuilder.createOutputScript(addr);
		
		TransactionOutput output = new TransactionOutput(tx, Coin.COIN, script.getProgram());
		
		out.addOutput(output);
		
		//本次输入
		TransactionInput input = tx.addInput(output);

		//输出到该地址
		ECKey key = new ECKey();
		
		Address to = AccountManager.newAddress(network, key);
		//添加输出
		tx.addOutput(new TransactionOutput(tx, Coin.COIN, to));
		
		//签名交易
		
		
	}
}
