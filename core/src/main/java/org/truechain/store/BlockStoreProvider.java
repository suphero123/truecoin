package org.truechain.store;

import org.truechain.network.NetworkParameters;

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
	 * 获取最新块的头信息
	 * @return
	 */
	public BlockHeader getBestBlockHeader() {
		
		return null;
	}
	
	/**
	 * 获取最新区块的完整信息
	 * @return
	 */
	public Block getBestBlock() {
		return null;
	}
}
