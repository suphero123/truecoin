package org.truechain.net;

import org.truechain.kits.PeerKit;
import org.truechain.network.NetworkParameters;
import org.truechain.network.RemoteSeedManager;
import org.truechain.network.TestNetworkParameters;

public class PeerGroupTest {

	public static void main(String[] args) {
		
		RemoteSeedManager seedManager = new RemoteSeedManager();
		
		NetworkParameters params = new TestNetworkParameters(seedManager, 6888);
		
		PeerKit peerGroup = new PeerKit(params, 5);
		
		peerGroup.startSyn();
		
//		try {
//			Thread.sleep(10000l);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		peerGroup.broadcastTransaction(new Transaction());
	}
}
