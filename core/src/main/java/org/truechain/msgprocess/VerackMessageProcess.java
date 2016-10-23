package org.truechain.msgprocess;

import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.truechain.core.Peer;
import org.truechain.core.exception.ProtocolException;
import org.truechain.message.Message;
import org.truechain.message.VersionMessage;

public class VerackMessageProcess implements MessageProcess {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(VerackMessageProcess.class);
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		
		if (peer.getPeerVersionMessage() != null) {
            throw new ProtocolException("got a version ack before version");
        }
		VersionMessage versionMessage = (VersionMessage) message;
		
		peer.setPeerVersionMessage(versionMessage);
        if (peer.isHandshake()) {
            throw new ProtocolException("got more than one version ack");
        }

        long peerTime = versionMessage.time * 1000;
        log.info("Connect success host={}, version={}, subVer='{}', services=0x{}, time={}, blocks={}",
        		peer.getAddress(),
                versionMessage.clientVersion,
                versionMessage.subVer,
                versionMessage.localServices,
                String.format(Locale.getDefault(), "%tF %tT", peerTime, peerTime),
                versionMessage.bestHeight);
        
        peer.setHandshake(true);
        
		return null;
	}
}
