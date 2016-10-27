package org.truechain.net;

import org.truechain.core.PeerGroup;
import org.truechain.network.MainNetParams;
import org.truechain.network.NetworkParameters;
import org.truechain.network.RemoteSeedManager;

public class PeerGroupTest {

	public static void main(String[] args) {
		
		RemoteSeedManager seedManager = new RemoteSeedManager();
		
		NetworkParameters params = new MainNetParams(seedManager, 6888);
		
		PeerGroup peerGroup = new PeerGroup(params, 5);
		
		peerGroup.startSyn();
		
//		try {
//			Thread.sleep(10000l);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		peerGroup.broadcastTransaction(new Transaction());
	}
}
