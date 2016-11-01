package org.truechain.net;

import java.net.InetSocketAddress;

import org.truechain.kits.PeerKit;
import org.truechain.network.MainNetParams;
import org.truechain.network.NetworkParameters;
import org.truechain.network.NodeSeedManager;
import org.truechain.network.Seed;
import org.truechain.network.SeedManager;
import org.truechain.transaction.Transaction;

public class PeerGroupTest2 {

	public static void main(String[] args) {
		
//		SeedManager seedManager = new RemoteSeedManager();
//		seedManager.add(new Seed(new InetSocketAddress("192.168.1.181", 6888)));

		SeedManager seedManager = new NodeSeedManager();
		seedManager.add(new Seed(new InetSocketAddress("127.0.0.1", 6888), true, 25000));
		
		NetworkParameters network = new MainNetParams(seedManager, 8888);
		
		PeerKit peerGroup = new PeerKit(network, 10);
		
		peerGroup.startSyn();

		while(true) {
			peerGroup.broadcastTransaction(new Transaction(network));
			try {
				Thread.sleep(100000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
