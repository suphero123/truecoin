package org.truechain.kits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.Configure;
import org.truechain.network.NetworkParameters;
import org.truechain.store.BlockStoreProvider;

/**
 * 程序核心，所有服务在此启动
 * @author ln
 *
 */
public class AppKit {
	
	private static final Logger log = LoggerFactory.getLogger(AppKit.class);
	
	private final BlockStoreProvider blockStoreProvider;
	private final NetworkParameters network;
	
	
	public AppKit(NetworkParameters network) {
		this.network = network;
		this.blockStoreProvider = BlockStoreProvider.getInstace(Configure.DATA_BLOCK, network);
	}

	//异步启动
	public void startSyn() {
		new Thread(){
			public void run() {
				AppKit.this.start();
			}
		}.start();
	}

	/**
	 * 启动核心
	 */
	protected void start() {
		initBlock();
	}

	private void initBlock() {
		checkGenesisBlock();
	}

	/**
	 * 检查创世块
	 */
	private void checkGenesisBlock() {
		network.getGengsisBlock();
	}
		
	
}
