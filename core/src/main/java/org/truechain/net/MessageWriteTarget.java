package org.truechain.net;

import java.io.IOException;

public interface MessageWriteTarget {
	
    void writeBytes(byte[] message) throws IOException;
    
    void closeConnection();
}
