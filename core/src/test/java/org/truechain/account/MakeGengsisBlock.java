package org.truechain.account;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.truechain.core.Coin;
import org.truechain.crypto.ECKey;
import org.truechain.crypto.Sha256Hash;
import org.truechain.kits.AccountKit;
import org.truechain.kits.PeerKit;
import org.truechain.network.NetworkParameters;
import org.truechain.network.NodeSeedManager;
import org.truechain.network.Seed;
import org.truechain.network.SeedManager;
import org.truechain.network.TestNetworkParameters;
import org.truechain.script.ScriptBuilder;
import org.truechain.store.BlockStore;
import org.truechain.store.TransactionStore;
import org.truechain.transaction.RegisterTransaction;
import org.truechain.transaction.Transaction;
import org.truechain.transaction.TransactionInput;
import org.truechain.utils.Hex;
import org.truechain.utils.Utils;

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
		
		RegisterTransaction rtx = new RegisterTransaction(network, txContent);
		
		rtx.verfify();
		rtx.verfifyScript();
		
		
		
		BlockStore gengsisBlock = new BlockStore(network);
		
		gengsisBlock.setPreHash(Sha256Hash.wrap(Hex.decode("0000000000000000000000000000000000000000000000000000000000000000")));
		gengsisBlock.setHeight(0);
		gengsisBlock.setTime(1478070769l);
		gengsisBlock.setTxCount(1);

		//交易列表
		List<TransactionStore> txs = new ArrayList<TransactionStore>();
		
		//产出货币总量
		Transaction coinBaseTx = new Transaction(network);
		coinBaseTx.setVersion(Transaction.VERSION);
		coinBaseTx.setType(Transaction.TYPE_COINBASE);
		
		TransactionInput input = new TransactionInput();
		coinBaseTx.addInput(input);
		input.setScriptSig(ScriptBuilder.createCoinbaseInputScript("this a gengsis tx".getBytes()));
		
		coinBaseTx.addOutput(Coin.valueOf(100000000l), Address.fromBase58(network, "ThYf64mTNLSCKhEW1KVyVkAECRanhWeJC"));
		coinBaseTx.verfify();
		coinBaseTx.verfifyScript();
		
		txs.add(new TransactionStore(network, coinBaseTx));
		
		//注册创世帐户
		RegisterTransaction regTx = new RegisterTransaction(network, Hex.decode("01000000010100a546304402206b016c3e4bcd0fcee8fd3be6dba9784605898d2adc02126fecf3c4a62354971602201e05648e97dcf624c29e6cbccc561d9445b26d6a3a69c7ed281cb73a10be91e7473045022100b918ec394cd37cbab228979867202f157e99f8ce762a086a7ebc8662eecd9a8e022058978dbb0e67b7f8004ae6c8295287066db0b1727d37f344410d60bf19cd1a50511424de55a2b2d32ed83c87b86a381e918ada34927b01a31424de55a2b2d32ed83c87b86a381e918ada34927b88c163210327260fbbb392bc13d9e8f2aabdf85d8c328eeec2114e37eb6d519fd1a0435cec2103cf8ca50b7711fb12a2a091ca5da5dd7493b77e342c205ebb0970469a400cd7e26721024148d14d898644570b4c44047d6687c1862cdbd0f813caf15a170550ad8c9d0621039844c6aa78fee792ce6ff793758b3c0d3c4217c6145f7c37844b5479b933d4e368ac00000000"));
		regTx.verfify();
		regTx.verfifyScript();
		
		txs.add(new TransactionStore(network, regTx));
		
		gengsisBlock.setTxs(txs);
		
		Sha256Hash merkleHash = gengsisBlock.getMerkleHash();
		System.out.println(merkleHash);
		Utils.checkState("e16cd594314bdec068d1641b275039876015e03dca939551a379f329a2715172".equals(Hex.encode(merkleHash.getBytes())), "the gengsis block merkle hash is error");
		
		System.out.println(Hex.encode(gengsisBlock.getHash().getBytes()));
		Utils.checkState("44b58a203c87000c3b3f1957d5365598a30a146f930b2447494dbd00639e0040".equals(Hex.encode(gengsisBlock.getHash().getBytes())), "the gengsis block hash is error");
		
		System.out.println(Hex.encode(gengsisBlock.baseSerialize()));
		
	}
}
