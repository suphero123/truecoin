package org.truechain.msgprocess;

import org.slf4j.LoggerFactory;
import org.truechain.core.Peer;
import org.truechain.message.Message;
import org.truechain.message.PingMessage;
import org.truechain.message.PongMessage;

public class PingMessageProcess implements MessageProcess {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PingMessageProcess.class);
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		log.info("{} {}", peer.getAddress(), message);
		peer.sendMessage(new PongMessage(((PingMessage)message).getNonce()));
		return null;
	}
}
