package org.truechain.core;

import org.truechain.kits.AppKit;
import org.truechain.network.TestNetworkParameters;

public class AppKitTest {

	public static void main(String[] args) {
		
		TestNetworkParameters network = TestNetworkParameters.get();
		
		AppKit kit = new AppKit(network);
		kit.startSyn();
	}
}
