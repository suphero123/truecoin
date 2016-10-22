package org.truechain.message;

import java.net.ProtocolException;

import org.truechain.network.NetworkParameters;


public class VersionMessage extends Message {

	public VersionMessage(NetworkParameters params) {
	    super(params);
	}
	
	public VersionMessage(NetworkParameters params, int newBestHeight) {
	    super(params);
	}
	
	@Override
	protected void parse() throws ProtocolException {
		
	}
}
