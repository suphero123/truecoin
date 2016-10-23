package org.truechain.msgprocess;

import org.slf4j.LoggerFactory;
import org.truechain.core.Peer;
import org.truechain.message.Message;

public class PongMessageProcess implements MessageProcess {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PongMessageProcess.class);
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		log.info("{} {}", peer.getAddress(), message);
        
		return null;
	}
}
