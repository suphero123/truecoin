package org.inchain.block;

import java.net.InetSocketAddress;

import org.truechain.account.Account;
import org.truechain.account.AccountTool;
import org.truechain.account.Address;
import org.truechain.crypto.ECKey;
import org.truechain.kits.AccountKit;
import org.truechain.kits.PeerKit;
import org.truechain.network.NetworkParameters;
import org.truechain.network.NodeSeedManager;
import org.truechain.network.Seed;
import org.truechain.network.SeedManager;
import org.truechain.network.TestNetworkParameters;
import org.truechain.transaction.RegisterTransaction;
import org.truechain.utils.Hex;

/**
 * 制作创世块
 * @author ln
 *
 */
public class MakeGengsisBlock {

	public static void main(String[] args) throws Exception {
		makeTestNetGengsisBlock();
	}

	private static void makeTestNetGengsisBlock() throws Exception {
		
		SeedManager seedManager = new NodeSeedManager();
		seedManager.add(new Seed(new InetSocketAddress("127.0.0.1", 6888), true, 25000));
		
		NetworkParameters network = new TestNetworkParameters(seedManager, 8888);

		PeerKit peerKit = new PeerKit(network);
		peerKit.startSyn();
		
		String mgpw = "123456";
		String trpw = "654321";
		
		AccountKit accountKit = new AccountKit(network, peerKit);
		try {
			Thread.sleep(2000l);
			if(accountKit.getAccountList().isEmpty()) {
				accountKit.createNewAccount(mgpw, trpw);
			}
		} finally {
			accountKit.close();
			peerKit.stop();
		}
		
		Account account = accountKit.getAccountList().get(0);
		
		RegisterTransaction tx = new RegisterTransaction(network, account);
		//根据密码计算出私匙
		ECKey seedPri = ECKey.fromPublicOnly(account.getPriSeed());
		byte[] seedPribs = seedPri.getPubKey(false);
		
		tx.calculateSignature(ECKey.fromPrivate(AccountTool.genPrivKey1(seedPribs, mgpw.getBytes())), 
				ECKey.fromPrivate(AccountTool.genPrivKey2(seedPribs, mgpw.getBytes())));
		
		tx.verfifyScript();
		
		//序列化和反序列化
		byte[] txContent = tx.baseSerialize();
		
		System.out.println(Hex.encode(txContent));
		
		RegisterTransaction rtx = new RegisterTransaction(network, txContent);
		System.out.println(rtx.getAccount().getAddress().getHash160AsHex());
		
		rtx.verfify();
		rtx.verfifyScript();
	}
}
