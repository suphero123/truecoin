package org.truechain.network;

import org.truechain.message.DefaultMessageSerializer;
import org.truechain.message.MessageSerializer;

public class TestNetworkParameters extends NetworkParameters {

	@Override
	public int getProtocolVersionNum(ProtocolVersion version) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MessageSerializer getSerializer(boolean parseRetain) {
		return new DefaultMessageSerializer();
	}

}
