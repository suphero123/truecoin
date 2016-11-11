package org.inchain.msgprocess;

import org.inchain.Configure;
import org.inchain.core.Peer;
import org.inchain.message.Message;
import org.inchain.network.NetworkParameters;
import org.inchain.store.BlockStoreProvider;
import org.inchain.store.TransactionStoreProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 新区块广播消息
 * 接收到新的区块之后，验证该区块是否合法，如果合法则进行收录并转播出去
 * 验证该区块是否合法的流程为：
 * 1、该区块基本的验证（包括区块的时间、大小、交易的合法性，梅克尔树根是否正确）。
 * 2、该区块的广播人是否是合法的委托人。
 * 3、该区块是否衔接最新区块，不允许分叉区块。
 * 4
 * @author ln
 *
 */
public class BlockMessageProcess implements MessageProcess {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private BlockStoreProvider blockStoreProvider;
	private TransactionStoreProvider transactionStoreProvider;
	
	public BlockMessageProcess(NetworkParameters network) {
		blockStoreProvider = BlockStoreProvider.getInstace(Configure.DATA_BLOCK, network);
		transactionStoreProvider = TransactionStoreProvider.getInstace(Configure.DATA_TRANSACTION, network);
	}
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		
		if(log.isDebugEnabled()) {
			log.debug("new block : {}", message);
		}
		return null;
	}
}
