package org.truechain.msgprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.core.Peer;
import org.truechain.crypto.Sha256Hash;
import org.truechain.mempool.MempoolContainer;
import org.truechain.mempool.MempoolContainerMap;
import org.truechain.message.Message;
import org.truechain.transaction.RegisterTransaction;
import org.truechain.transaction.Transaction;
import org.truechain.utils.Hex;

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
