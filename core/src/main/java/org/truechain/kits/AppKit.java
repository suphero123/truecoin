package org.truechain.kits;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.Configure;
import org.truechain.consensus.LocalMining;
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
	//挖矿程序
	private LocalMining mining;
	//结点管理
	private PeerKit peerKit;
	//帐户管理 
	private AccountKit accountKit;
	
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
					log.error("核心程序启动失败", e);
					System.exit(-1);
				}
			}
		}.start();
	}

	/**
	 * 启动核心
	 * @throws IOException 
	 */
	protected void start() throws IOException {
		//初始化节点管理器
		initPeerKit();
		//检查区块数据
		initBlock();
		//初始化帐户信息
		initAccountKit();
		//初始化挖矿
		initMining();
	}
	
	//初始化节点管理器
	private void initPeerKit() {
		peerKit = new PeerKit(network, Configure.MAX_CONNECT_COUNT);
		peerKit.startSyn();
	}

	//初始化帐户信息
	private void initAccountKit() throws IOException {
		accountKit = new AccountKit(network, peerKit);
	}

	//初始化挖矿
	private void initMining() {
		mining = new LocalMining(network, accountKit, peerKit);
		mining.start();
	}

	/**
	 * 停止服务
	 * @throws IOException 
	 */
	public void stop() throws IOException {
		peerKit.stop();
		mining.stop();
		
		blockStoreProvider.close();
		accountKit.close();
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
