package org.truechain.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.truechain.Configure;
import org.truechain.account.Address;
import org.truechain.core.Coin;
import org.truechain.crypto.Sha256Hash;
import org.truechain.network.NetworkParameters;
import org.truechain.network.TestNetworkParameters;
import org.truechain.script.ScriptBuilder;
import org.truechain.transaction.Transaction;
import org.truechain.transaction.TransactionInput;
import org.truechain.utils.Hex;

public class BlockStoreProvderTest {

	private NetworkParameters network;
	private BlockStoreProvider storeProvider;
	
	@Before
	public void init() {
		network = TestNetworkParameters.get();
		storeProvider = new BlockStoreProvider(Configure.DATA_BLOCK, network);
	}
	
	@After
	public void close() throws IOException {
		storeProvider.close();
	}
	
	@Test
	public void testInit() throws Exception {
		assertNotNull(network);
		assertNotNull(storeProvider);
	}
	
	@Test
	public void testSave() throws IOException {
		BlockStore testBlock = new BlockStore(network);
		
		testBlock.setPreHash(Sha256Hash.wrap(Hex.decode("3728b98205462ba133d5bc0128c2d6f45c95136011994d2165ec24961e8d912a")));
		testBlock.setHeight(1);
		testBlock.setTime(1478164677l);

		//交易列表
		List<TransactionStore> txs = new ArrayList<TransactionStore>();
		
		//coinbase
		Transaction coinBaseTx = new Transaction(network);
		coinBaseTx.setVersion(Transaction.VERSION);
		coinBaseTx.setType(Transaction.TYPE_COINBASE);
		coinBaseTx.setLockTime(1478164688l);
		
		TransactionInput input = new TransactionInput();
		coinBaseTx.addInput(input);
		input.setScriptSig(ScriptBuilder.createCoinbaseInputScript("this a gengsis tx".getBytes()));
		
		coinBaseTx.addOutput(Coin.valueOf(100l), Address.fromBase58(network, "ThYf64mTNLSCKhEW1KVyVkAECRanhWeJC"));
		coinBaseTx.verfify();
		coinBaseTx.verfifyScript();
		
		txs.add(new TransactionStore(network, coinBaseTx, testBlock.getHeight(), 0));
		
		testBlock.setTxs(txs);
		testBlock.setTxCount(txs.size());
		
		Sha256Hash merkleHash = testBlock.getMerkleHash();
		testBlock.setMerkleHash(merkleHash);
		
		System.out.println(testBlock.getHash());
		
		storeProvider.saveBlock(testBlock);
		
	}
	
	@Test
	public void testZGetBlockHeader() {
		Sha256Hash hash = Sha256Hash.wrap(Hex.decode("2813ae608bd070b01b2f711b8eb4ef2cf226b91b5b9df1f888e6af7d8060d1e7"));
		BlockHeaderStore header = storeProvider.getHeader(hash.getBytes());
		assertNotNull(header);
		
		assertEquals("e20dacb0a8e2ac580120cf4db083533e6f9c2871b990ce07fa8ad84837850385", Hex.encode(header.getMerkleHash().getBytes()));
		
		assertEquals("3728b98205462ba133d5bc0128c2d6f45c95136011994d2165ec24961e8d912a", Hex.encode(header.getPreHash().getBytes()));
		
		List<Sha256Hash> txHashs = header.getTxHashs();
		
		assertEquals(1, txHashs.size());
		
		TransactionStore tx = storeProvider.getTransaction(txHashs.get(0).getBytes());
		assertNotNull(tx);
		assertEquals("e20dacb0a8e2ac580120cf4db083533e6f9c2871b990ce07fa8ad84837850385", Hex.encode(tx.getKey()));
	}
	
	@Test
	public void testZGetBlockHeaderByTx() {
		TransactionStore tx = storeProvider.getTransaction(Hex.decode("e20dacb0a8e2ac580120cf4db083533e6f9c2871b990ce07fa8ad84837850385"));
		assertNotNull(tx);
		assertEquals("e20dacb0a8e2ac580120cf4db083533e6f9c2871b990ce07fa8ad84837850385", Hex.encode(tx.getKey()));

		BlockHeaderStore header = storeProvider.getHeaderByHeight(tx.getHeight());
		assertNotNull(header);
		assertEquals("e20dacb0a8e2ac580120cf4db083533e6f9c2871b990ce07fa8ad84837850385", Hex.encode(header.getMerkleHash().getBytes()));
	}
	
	@Test
	public void testZGetBlock() {

		BlockStore blockStore = storeProvider.getBlock(Hex.decode("2813ae608bd070b01b2f711b8eb4ef2cf226b91b5b9df1f888e6af7d8060d1e7"));
		
		assertNotNull(blockStore);
	}
}
