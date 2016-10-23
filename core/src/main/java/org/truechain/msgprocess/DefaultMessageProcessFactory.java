package org.truechain.msgprocess;

import org.truechain.message.*;

public class DefaultMessageProcessFactory implements MessageProcessFactory {

	private static final MessageProcessFactory INSTANCE = new DefaultMessageProcessFactory();

	private DefaultMessageProcessFactory() {
	}
	
	public static MessageProcessFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public MessageProcess getFactory(Message message) {
		if(message instanceof PingMessage) {
			return new PingMessageProcess();
		} else if(message instanceof PongMessage) {
			return new PongMessageProcess();
		} else if(message instanceof VerackMessage) {
			return new VerackMessageProcess();
		} else if(message instanceof VersionMessage) {
			return new VersionMessageProcess();
		} else {
			return new UnknownMessageProcess();
		}
	}
}
