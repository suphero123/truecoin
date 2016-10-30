package org.truechain.store;

import org.truechain.network.NetworkParameters;

/**
 * 区块提供服务
 * @author ln
 *
 */
public class BlockStoreProvider extends BaseStoreProvider {

	public BlockStoreProvider(String dir, NetworkParameters network) {
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
}
