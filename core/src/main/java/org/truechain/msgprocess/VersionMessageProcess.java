package org.truechain.msgprocess;

import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.truechain.core.Peer;
import org.truechain.core.exception.ProtocolException;
import org.truechain.message.Message;
import org.truechain.message.VerackMessage;
import org.truechain.message.VersionMessage;

public class VersionMessageProcess implements MessageProcess {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(VersionMessageProcess.class);
	
	@Override
	public MessageProcessResult process(Message message, Peer peer) {
		VersionMessage versionMessage = (VersionMessage) message;
		
		if (peer.getPeerVersionMessage() != null)
            throw new ProtocolException("Got two version messages from peer");
		peer.setPeerVersionMessage(versionMessage);
		
        // Switch to the new protocol version.
        long peerTime = versionMessage.time * 1000;
        log.info("Got host={}, version={}, subVer='{}', services=0x{}, time={}, blocks={}",
        		peer.getAddress(),
                versionMessage.clientVersion,
                versionMessage.subVer,
                versionMessage.localServices,
                String.format(Locale.getDefault(), "%tF %tT", peerTime, peerTime),
                versionMessage.bestHeight);
        
        if (versionMessage.bestHeight < 0)
            // In this case, it's a protocol violation.
            throw new ProtocolException("Peer reports invalid best height: " + versionMessage.bestHeight);
        
        //回应自己的版本信息
		peer.sendMessage(new VerackMessage(peer.getNetwork(), peer.getNetwork().getBestBlockHeight(), 
				peer.getPeerAddress()));
		
		return null;
	}
}
