package org.truechain.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.truechain.crypto.Sha256Hash;
import org.truechain.network.NetworkParameters;
import org.truechain.utils.Utils;

/**
 * 区块提供服务
 * @author ln
 *
 */
public class BlockStoreProvider extends BaseStoreProvider {

	private static BlockStoreProvider INSTACE;
	
	//单例
	public static BlockStoreProvider getInstace(String dir, NetworkParameters network) {
		return getInstace(dir, network, -1, -1);
	}
	public static BlockStoreProvider getInstace(String dir, NetworkParameters network, long leveldbReadCache, int leveldbWriteCache) {
		if(INSTACE == null) {
			synchronized (locker) {
				if(INSTACE == null)
					INSTACE = new BlockStoreProvider(dir, network);
			}
		}
		return INSTACE;
	}
	
	protected BlockStoreProvider(String dir, NetworkParameters network) {
		super(dir, network);
	}

	@Override
	protected byte[] toByte(Store store) {
		return null;
	}

	@Override
	protected Store pase(byte[] content) {
		return null;
	}
	
	/**
	 * 保存区块完整的区块信息
	 * @param block
	 * @throws IOException 
	 */
	public void saveBlock(BlockStore block) throws IOException {
		//保存块头
		db.put(block.getHash().getBytes(), block.serializeHeader());
		//保存交易
		for (int i = 0; i < block.getTxCount(); i++) {
			TransactionStore tx = block.getTxs().get(i);
	        
			db.put(tx.getTransaction().getHash().getBytes(), tx.baseSerialize());
		}
		
		byte[] heightBytes = new byte[4]; 
		Utils.uint32ToByteArrayBE(block.getHeight(), heightBytes, 0);
		
		db.put(heightBytes, block.getKey());
	}

	/**
	 * 获取区块头信息
	 * @param hash
	 * @return
	 */
	public BlockHeaderStore getHeader(byte[] hash) {
		byte[] content = db.get(hash);
		if(content == null) {
			return null;
		}
		BlockHeaderStore header = new BlockHeaderStore(network, content);
		header.setHash(Sha256Hash.wrap(hash));
		
		return header;
	}
	
	/**
	 * 获取区块头信息
	 * @param hash
	 * @return
	 */
	public BlockHeaderStore getHeaderByHeight(long height) {
		byte[] heightBytes = new byte[4]; 
		Utils.uint32ToByteArrayBE(height, heightBytes, 0);
		
		byte[] hash = db.get(heightBytes);
		if(hash == null) {
			return null;
		}
		return getHeader(hash);
	}

	/**
	 * 获取完整的区块信息
	 * @param hash
	 * @return
	 */
	public BlockStore getBlock(byte[] hash) {
		
		BlockHeaderStore header = getHeader(hash);
		//交易列表
		List<TransactionStore> txs = new ArrayList<TransactionStore>();
		
		for (Sha256Hash txHash : header.getTxHashs()) {
			txs.add(getTransaction(txHash.getBytes()));
		}
		
		BlockStore block = new BlockStore(network);
		block.setTxs(txs);
		block.setVersion(header.getVersion());
		block.setHash(header.getHash());
		block.setHeight(header.getHeight());
		block.setKey(header.getKey());
		block.setMerkleHash(header.getMerkleHash());
		block.setPreHash(header.getPreHash());
		block.setTime(header.getTime());
		block.setTxCount(header.getTxCount());
		
		return block;
	}
	
	/**
	 * 获取一笔交易
	 * @param hash
	 * @return
	 */
	public TransactionStore getTransaction(byte[] hash) {
		byte[] content = db.get(hash);
		TransactionStore store = new TransactionStore(network, content);
		store.setKey(hash);
		
		return store;
	}
	
	/**
	 * 获取最新块的头信息
	 * @return
	 */
	public BlockHeaderStore getBestBlockHeader() {
		
		return null;
	}
	
	/**
	 * 获取最新区块的完整信息
	 * @return
	 */
	public BlockStore getBestBlock() {
		return null;
	}
}
