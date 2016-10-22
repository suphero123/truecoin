package org.truechain.core;

import java.net.InetSocketAddress;

import org.slf4j.LoggerFactory;
import org.truechain.message.Message;
import org.truechain.message.VersionMessage;
import org.truechain.network.NetworkParameters;

public class Peer extends PeerSocketHandler {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Peer.class);

	private NetworkParameters network;
	
	public Peer(NetworkParameters network, InetSocketAddress address) {
		this(network, new PeerAddress(address));
	}
	
	public Peer(NetworkParameters network, PeerAddress peerAddress) {
		super(network, peerAddress);
		this.network = network;
		this.peerAddress = peerAddress;
	}

	@Override
	protected void processMessage(Message m) throws Exception {
		// TODO Auto-generated method stub
		log.info("receive message {}", m);
	}

	@Override
	public int getMaxMessageSize() {
		return Message.MAX_SIZE;
	}
	
	@Override
	public void connectionClosed() {
		log.info("connectionClosed");
	}

	@Override
	public void connectionOpened() {
		log.info("connectionOpened");
		//发送版本信息
		sendMessage(new VersionMessage(network, 1000, getPeerAddress()));
		log.info("send version message end");
	}
	
	public PeerAddress getPeerAddress() {
		return peerAddress;
	}

}
