package org.truechain.network;

import java.util.ArrayList;
import java.util.List;

import org.truechain.account.Address;
import org.truechain.core.Coin;
import org.truechain.crypto.Sha256Hash;
import org.truechain.message.DefaultMessageSerializer;
import org.truechain.message.MessageSerializer;
import org.truechain.script.ScriptBuilder;
import org.truechain.store.BlockStore;
import org.truechain.store.TransactionStore;
import org.truechain.transaction.RegisterTransaction;
import org.truechain.transaction.Transaction;
import org.truechain.transaction.TransactionInput;
import org.truechain.utils.Hex;
import org.truechain.utils.Utils;

/**
 * 测试网络
 * @author ln
 *
 */
public class TestNetworkParameters extends NetworkParameters {

	private static TestNetworkParameters instance;
    public static synchronized TestNetworkParameters get() {
        if (instance == null) {
            instance = new TestNetworkParameters();
        }
        return instance;
    }
    
    public TestNetworkParameters() {
    	this.seedManager = new RemoteSeedManager();
    	this.port = 8322; 
    	init();
	}
    

	public TestNetworkParameters(SeedManager seedManager, int port) {
    	this.seedManager = seedManager;
    	this.port = port;
    	init();
	}
    
	private void init() {
		int[] codes = new int[254];
		for (int i = 0; i < 254; i++) {
			codes[i] = i;
		}
		this.acceptableAddressCodes = codes;
	}
	
	/**
	 * 测试网络的创世块
	 */
	@Override
	public BlockStore getGengsisBlock() {
		BlockStore gengsisBlock = new BlockStore(this);
		
		gengsisBlock.setPreHash(Sha256Hash.wrap(Hex.decode("0000000000000000000000000000000000000000000000000000000000000000")));
		gengsisBlock.setHeight(0);
		gengsisBlock.setTime(1478070769l);

		//交易列表
		List<TransactionStore> txs = new ArrayList<TransactionStore>();
		
		//产出货币总量
		Transaction coinBaseTx = new Transaction(this);
		coinBaseTx.setVersion(Transaction.VERSION);
		coinBaseTx.setType(Transaction.TYPE_COINBASE);
		
		TransactionInput input = new TransactionInput();
		coinBaseTx.addInput(input);
		input.setScriptSig(ScriptBuilder.createCoinbaseInputScript("this a gengsis tx".getBytes()));
		
		coinBaseTx.addOutput(Coin.valueOf(100000000l), Address.fromBase58(this, "ThYf64mTNLSCKhEW1KVyVkAECRanhWeJC"));
		coinBaseTx.verfify();
		coinBaseTx.verfifyScript();
		
		txs.add(new TransactionStore(this, coinBaseTx));
		
		//注册创世帐户
		RegisterTransaction regTx = new RegisterTransaction(this, Hex.decode("01000000010100a546304402206b016c3e4bcd0fcee8fd3be6dba9784605898d2adc02126fecf3c4a62354971602201e05648e97dcf624c29e6cbccc561d9445b26d6a3a69c7ed281cb73a10be91e7473045022100b918ec394cd37cbab228979867202f157e99f8ce762a086a7ebc8662eecd9a8e022058978dbb0e67b7f8004ae6c8295287066db0b1727d37f344410d60bf19cd1a50511424de55a2b2d32ed83c87b86a381e918ada34927b01a31424de55a2b2d32ed83c87b86a381e918ada34927b88c163210327260fbbb392bc13d9e8f2aabdf85d8c328eeec2114e37eb6d519fd1a0435cec2103cf8ca50b7711fb12a2a091ca5da5dd7493b77e342c205ebb0970469a400cd7e26721024148d14d898644570b4c44047d6687c1862cdbd0f813caf15a170550ad8c9d0621039844c6aa78fee792ce6ff793758b3c0d3c4217c6145f7c37844b5479b933d4e368ac00000000"));
		regTx.verfify();
		regTx.verfifyScript();
		
		txs.add(new TransactionStore(this, regTx));
		
		gengsisBlock.setTxs(txs);
		gengsisBlock.setTxCount(txs.size());
		
		Sha256Hash merkleHash = gengsisBlock.getMerkleHash();
		Utils.checkState("e16cd594314bdec068d1641b275039876015e03dca939551a379f329a2715172".equals(Hex.encode(merkleHash.getBytes())), "the gengsis block merkle hash is error");
		
		Utils.checkState("3728b98205462ba133d5bc0128c2d6f45c95136011994d2165ec24961e8d912a".equals(Hex.encode(gengsisBlock.getHash().getBytes())), "the gengsis block hash is error");
		
		return gengsisBlock;
	}
	
	public static void main(String[] args) {
		TestNetworkParameters network = get();
		network.getGengsisBlock();
	}
	
	@Override
	public int getProtocolVersionNum(ProtocolVersion version) {
		return version.getVersion();
	}

	@Override
	public MessageSerializer getSerializer(boolean parseRetain) {
		return new DefaultMessageSerializer(this);
	}

}
