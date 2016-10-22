package org.truechain.net;

import java.net.InetSocketAddress;

import org.truechain.core.PeerGroup;
import org.truechain.core.Transaction;
import org.truechain.network.MainNetParams;
import org.truechain.network.NetworkParameters;
import org.truechain.network.NodeSeedManager;
import org.truechain.network.RemoteSeedManager;
import org.truechain.network.Seed;
import org.truechain.network.SeedManager;

public class PeerGroupTest2 {

	public static void main(String[] args) {
		
//		SeedManager seedManager = new RemoteSeedManager();
//		seedManager.add(new Seed(new InetSocketAddress("192.168.1.181", 6888)));

		SeedManager seedManager = new NodeSeedManager();
		seedManager.add(new Seed(new InetSocketAddress("192.168.1.181", 6888), true, 25000));
		
		NetworkParameters params = new MainNetParams(seedManager, 8888);
		
		PeerGroup peerGroup = new PeerGroup(params, 10);
		
		peerGroup.startSyn();

		peerGroup.broadcastTransaction(new Transaction());

		while(true) {
			try {
				Thread.sleep(100000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			peerGroup.broadcastTransaction(new Transaction());
		}
	}
}
