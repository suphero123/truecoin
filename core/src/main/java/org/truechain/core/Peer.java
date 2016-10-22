package org.truechain.core;

import java.nio.ByteBuffer;

import org.slf4j.LoggerFactory;
import org.truechain.net.MessageWriteTarget;
import org.truechain.net.StreamConnection;

public class Peer extends PeerSocketHandler {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Peer.class);

	@Override
	public void connectionClosed() {
		log.info("connectionClosed");
	}

	@Override
	public void connectionOpened() {
		log.info("connectionOpened");
	}

	@Override
	public int receiveBytes(ByteBuffer buff) throws Exception {
		try{
			byte[] b = new byte[buff.limit()];
			buff.get(b, 0, buff.limit());
			
			String message = new String(b);
			log.info("receiveBytes: "+message);
			
			if(!message.startsWith("has")) {
				sendMessage(("has receive message : "+message).getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buff.limit();
	}

	@Override
	public int getMaxMessageSize() {
		log.info("getMaxMessageSize");		
		return 0;
	}

}
