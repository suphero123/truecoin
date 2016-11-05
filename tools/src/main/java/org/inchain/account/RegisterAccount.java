package org.inchain.account;

import java.io.File;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.Configure;
import org.truechain.kits.AccountKit;
import org.truechain.kits.PeerKit;
import org.truechain.network.NetworkParameters;
import org.truechain.network.NodeSeedManager;
import org.truechain.network.Seed;
import org.truechain.network.SeedManager;
import org.truechain.network.TestNetworkParameters;

public class RegisterAccount {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws Exception {
		
		SeedManager seedManager = new NodeSeedManager();
		seedManager.add(new Seed(new InetSocketAddress("127.0.0.1", 8322), true, 25000));
		
		NetworkParameters network = new TestNetworkParameters(seedManager, 8888);
		
		//测试前先清空帐户目录
		File dir = new File(Configure.DATA_ACCOUNT);
		if(dir.listFiles() != null) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
		}
		
		PeerKit peerKit = new PeerKit(network);
		peerKit.startSyn();
		
		AccountKit accountKit = new AccountKit(network, peerKit);
		try {
			Thread.sleep(2000l);
			if(accountKit.getAccountList().isEmpty()) {
				accountKit.createNewAccount("123456", "0123456");
			}
		} finally {
//			accountKit.close();
//			peerKit.stop();
		}
	}
	
}
