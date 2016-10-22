package org.truechain.core;

import static org.truechain.utils.Utils.checkNotNull;
import static org.truechain.utils.Utils.checkState;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.net.AbstractTimeoutHandler;
import org.truechain.net.MessageWriteTarget;
import org.truechain.net.StreamConnection;


public abstract class PeerSocketHandler extends AbstractTimeoutHandler implements StreamConnection {

	private static final Logger log = LoggerFactory.getLogger(PeerSocketHandler.class);
	
    // If we close() before we know our writeTarget, set this to true to call writeTarget.closeConnection() right away.
    private boolean closePending = false;
    // writeTarget will be thread-safe, and may call into PeerGroup, which calls us, so we should call it unlocked
 	protected MessageWriteTarget writeTarget = null;
 	
 	private Lock lock = new ReentrantLock();
    
	@Override
    protected void timeoutOccurred() {
        log.info("{}: Timed out", "");
        close();
    }

    public void sendMessage(byte[] message) throws NotYetConnectedException {
        lock.lock();
        try {
            if (writeTarget == null)
                throw new NotYetConnectedException();
        } finally {
            lock.unlock();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
//            serializer.serialize(message, out);
            writeTarget.writeBytes(message);
        } catch (IOException e) {
        	close();
        	log.info(" - " + e.getMessage());
        }
    }
    
	@Override
    public void setWriteTarget(MessageWriteTarget writeTarget) {
		checkNotNull(writeTarget);
        lock.lock();
        boolean closeNow = false;
        try {
        	checkState(this.writeTarget == null);
            closeNow = closePending;
            this.writeTarget = writeTarget;
        } finally {
            lock.unlock();
        }
        if (closeNow)
            writeTarget.closeConnection();
    }
	
	
	/**
     * Closes the connection to the peer if one exists, or immediately closes the connection as soon as it opens
     */
    public void close() {
        lock.lock();
        try {
            if (writeTarget == null) {
                closePending = true;
                return;
            }
        } finally {
            lock.unlock();
        }
        writeTarget.closeConnection();
    }
}

