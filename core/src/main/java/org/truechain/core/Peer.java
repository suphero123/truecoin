package org.truechain.core;

import java.net.InetSocketAddress;

import org.slf4j.LoggerFactory;
import org.truechain.message.Message;
import org.truechain.message.PingMessage;
import org.truechain.message.VersionMessage;
import org.truechain.msgprocess.DefaultMessageProcessFactory;
import org.truechain.msgprocess.MessageProcess;
import org.truechain.msgprocess.MessageProcessFactory;
import org.truechain.msgprocess.MessageProcessResult;
import org.truechain.network.NetworkParameters;

public class Peer extends PeerSocketHandler {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Peer.class);

	private NetworkParameters network;

	//节点版本信息
	private VersionMessage peerVersionMessage;
	//节点握手完成
	private boolean handshake = false;
	
	private static MessageProcessFactory messageProcessFactory = DefaultMessageProcessFactory.getInstance();
	
	public Peer(NetworkParameters network, InetSocketAddress address) {
		this(network, new PeerAddress(address));
	}
	
	public Peer(NetworkParameters network, PeerAddress peerAddress) {
		super(network, peerAddress);
		this.network = network;
		this.peerAddress = peerAddress;
	}

	@Override
	protected void processMessage(Message message) throws Exception {
		MessageProcess messageProcess = messageProcessFactory.getFactory(message);
		MessageProcessResult result = messageProcess.process(message, this);
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
		sendMessage(new VersionMessage(network, network.getBestBlockHeight(), getPeerAddress()));
		log.info("send version message end");
	}
	
	public PeerAddress getPeerAddress() {
		return peerAddress;
	}

	public NetworkParameters getNetwork() {
		return network;
	}
	
	public VersionMessage getPeerVersionMessage() {
		return peerVersionMessage;
	}
	public void setPeerVersionMessage(VersionMessage peerVersionMessage) {
		this.peerVersionMessage = peerVersionMessage;
	}

	public boolean isHandshake() {
		return handshake;
	}

	public void setHandshake(boolean handshake) {
		this.handshake = handshake;
	}

	public void ping() {
		sendMessage(new PingMessage((long)(Math.random()*Long.MAX_VALUE)));
	}
}
