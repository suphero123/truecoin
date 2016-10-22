package org.truechain.network;

import org.truechain.message.DefaultMessageSerializer;
import org.truechain.message.MessageSerializer;

public class MainNetParams extends NetworkParameters {
	
	
	
	private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }
    
    public MainNetParams() {
    	this.seedManager = new RemoteSeedManager();
	}
    
    public MainNetParams(SeedManager seedManager, int port) {
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
