package org.inchain.msgprocess;

import org.inchain.core.Peer;
import org.inchain.crypto.Sha256Hash;
import org.inchain.mempool.MempoolContainer;
import org.inchain.mempool.MempoolContainerMap;
import org.inchain.message.Message;
import org.inchain.transaction.RegisterTransaction;
import org.inchain.transaction.Transaction;
import org.inchain.utils.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交易消息
 * @author ln
 *
 */
public class TransactionMessageProcess implements MessageProcess {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionMessageProcess.class);
	
	private MempoolContainer mempool = MempoolContainerMap.getInstace();
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		
		Transaction tx = (Transaction) message;
		
		if(log.isDebugEnabled()) {
			log.debug("transaction message {}", Hex.encode(tx.baseSerialize()));
		}
		//验证交易的合法性
		tx.verfify();
		tx.verfifyScript();
		
		Sha256Hash id = tx.getHash();
		
		if(log.isDebugEnabled()) {
			log.debug("verify success! tx id : {}", id);
		}	
		
		//转发交易
		//TODO
		
		//加入内存池
		mempool.add(id, tx);
		
		return new MessageProcessResult();
	}
}
