package org.truechain.consensus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.Configure;
import org.truechain.crypto.Sha256Hash;
import org.truechain.mempool.MempoolContainer;
import org.truechain.mempool.MempoolContainerMap;
import org.truechain.network.NetworkParameters;
import org.truechain.store.Block;
import org.truechain.store.BlockStoreProvider;
import org.truechain.transaction.Transaction;

/**
 * 本地打包交易，当被委托时自动执行
 * @author ln
 *
 */
public final class LocalMining implements Mining {
	
	private final static Logger log = LoggerFactory.getLogger(LocalMining.class);
	
	//运行状态，0没运行，1准备就绪，2打包中，3等待停止，4停止，5异常结束
	private int runState = 0;
	
	private NetworkParameters network;
	private MempoolContainer mempool = MempoolContainerMap.getInstace();
	
	private BlockStoreProvider blockStoreProvider;
	
	public LocalMining(NetworkParameters network) {
		this.network = network;
		
		blockStoreProvider = BlockStoreProvider.getInstace(Configure.DATA_BLOCK, network);
	}
	
	/**
	 * mining 
	 */
	private void mining() {
		runState = 2;
		
		//如果区块高度不是最新的，那么同步至最新的再开始
		//TODO
		
		Block bestBlock = blockStoreProvider.getBestBlock();
		
		//每次最多处理1000个交易
		Transaction[] txs = mempool.getNewest(1000);
		
		for (Transaction tx : txs) {
			Sha256Hash txid = tx.getHash();
			
			//如果某笔交易验证失败，则不打包进区块
			try {
				tx.verfify();
				tx.verfifyScript();
			} catch (Exception e) {
				log.warn("tx {} verify fail", txid, e);
			}
		}
		
		runState = 1;
	}
	
	@Override
	public void start() {
		//这里需要引入共识机制来确定该由谁来打包交易
		//TODO
		
		//假设目前每轮都由自己打包
		runState = 1;
		
		while(true) {
			if(runState == 1) {
				mining();
			}
			try {
				Thread.sleep(500l);
			} catch (InterruptedException e) {
				log.error("mining err", e);
			}
		}
	}
	
	@Override
	public void stop() {
		//强制停止
		runState = 4;
	}
	
	@Override
	public int status() {
		return runState;
	}
	
}
