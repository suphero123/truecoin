package org.truechain.message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class DefaultMessageSerializer extends MessageSerializer {

	@Override
	public Message deserialize(ByteBuffer in) throws ProtocolException, IOException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void seekPastMagicBytes(ByteBuffer in) throws BufferUnderflowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serialize(String name, byte[] message, OutputStream out)
			throws IOException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serialize(Message message, OutputStream out) throws IOException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isParseRetainMode() {
		// TODO Auto-generated method stub
		return false;
	}

}
