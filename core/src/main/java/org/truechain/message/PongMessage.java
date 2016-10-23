package org.truechain.message;

import java.io.IOException;
import java.io.OutputStream;

import org.truechain.core.exception.ProtocolException;
import org.truechain.network.NetworkParameters;
import org.truechain.utils.Utils;

public class PongMessage extends Message {
    private long nonce;

    public PongMessage(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }
    
    public PongMessage(long nonce) {
        this.nonce = nonce;
    }
    
    @Override
    protected void parse() throws ProtocolException {
        nonce = readInt64();
        length = 8;
    }
    
    @Override
    public void serializeToStream(OutputStream stream) throws IOException {
        Utils.int64ToByteStreamLE(nonce, stream);
    }
    
    public long getNonce() {
        return nonce;
    }

	@Override
	public String toString() {
		return "PongMessage [nonce=" + nonce + "]";
	}
}
