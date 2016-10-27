package org.truechain.message;

import org.truechain.core.exception.ProtocolException;
import org.truechain.network.NetworkParameters;
import org.truechain.utils.Hex;

/**
 * <p>Instances of this class are not safe for use by multiple threads.</p>
 */
public class UnknownMessage extends EmptyMessage {

    private String name;

    public UnknownMessage(NetworkParameters params, String name, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Unknown message [" + name + "]: " + (payload == null ? "" : Hex.encode(payload));
    }
}
