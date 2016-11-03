package org.truechain.kits;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.Configure;
import org.truechain.core.exception.VerificationException;
import org.truechain.network.NetworkParameters;
import org.truechain.store.BlockHeaderStore;
import org.truechain.store.BlockStore;
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
				try {
					AppKit.this.start();
				} catch (IOException e) {
					log.error("", e);
					//TODO
				}
			}
		}.start();
	}

	/**
	 * 启动核心
	 * @throws IOException 
	 */
	protected void start() throws IOException {
		initBlock();
	}

	/*
	 * 初始化区块信息
	 */
	private void initBlock() throws IOException {
		
		checkGenesisBlock();
		
		checkPoint();
	}

	/*
	 * 效验区块链的正确性和完整性
	 */
	private void checkPoint() {
		//TODO
		
	}

	/*
	 * 检查创世块
	 * 如果创世块不存在，则新增，然后下载区块
	 * 如果存在，则检查是否正确，不正确则直接抛出异常
	 */
	private void checkGenesisBlock() throws IOException {
		BlockStore gengsisBlock = network.getGengsisBlock();
		
		BlockHeaderStore localGengsisBlockHeader = blockStoreProvider.getHeader(gengsisBlock.getHash().getBytes());
		
		//存在，判断区块信息是否正确
		if(localGengsisBlockHeader != null && !localGengsisBlockHeader.equals((BlockHeaderStore)gengsisBlock)) {
			throw new VerificationException("the genesis block check error!");
		} else if(localGengsisBlockHeader == null) {
			//新增
			blockStoreProvider.saveBlock(gengsisBlock);
		}
	}
}
