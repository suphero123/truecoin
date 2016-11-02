package org.truechain.msgprocess;

import org.slf4j.LoggerFactory;
import org.truechain.core.Peer;
import org.truechain.message.Message;

public class UnknownMessageProcess implements MessageProcess {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(UnknownMessageProcess.class);
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		log.warn("receive unknown message {}", message);
		return null;
	}
}
