package org.truechain.network;

import org.truechain.message.DefaultMessageSerializer;
import org.truechain.message.MessageSerializer;

public class TestNetworkParameters extends NetworkParameters {

	private static TestNetworkParameters instance;
    public static synchronized TestNetworkParameters get() {
        if (instance == null) {
            instance = new TestNetworkParameters();
        }
        return instance;
    }
    
    public TestNetworkParameters() {
    	this.seedManager = new RemoteSeedManager();
    	
    	int[] codes = new int[128];
    	for (int i = 0; i < 128; i++) {
			codes[i] = i;
		}
    	this.acceptableAddressCodes = codes;
	}
    
    public TestNetworkParameters(SeedManager seedManager, int port) {
    	this.seedManager = seedManager;
    	this.port = port;
	}
    
	@Override
	public int getProtocolVersionNum(ProtocolVersion version) {
		return version.getVersion();
	}

	@Override
	public MessageSerializer getSerializer(boolean parseRetain) {
		return new DefaultMessageSerializer(this);
	}

}
