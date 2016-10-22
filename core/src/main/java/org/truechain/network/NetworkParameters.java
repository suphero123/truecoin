package org.truechain.network;

import org.truechain.message.MessageSerializer;

public abstract class NetworkParameters {
	//种子管理器
	protected SeedManager seedManager;
	
	//端口
	protected int port;
	protected long packetMagic;
	
	protected transient MessageSerializer defaultSerializer = null;
	
	public abstract int getProtocolVersionNum(final ProtocolVersion version);
	
	/**
     * Return the default serializer for this network. This is a shared serializer.
     * @return 
     */
    public final MessageSerializer getDefaultSerializer() {
        // Construct a default serializer if we don't have one
        if (null == this.defaultSerializer) {
            // Don't grab a lock unless we absolutely need it
            synchronized(this) {
                // Now we have a lock, double check there's still no serializer
                // and create one if so.
                if (null == this.defaultSerializer) {
                    // As the serializers are intended to be immutable, creating
                    // two due to a race condition should not be a problem, however
                    // to be safe we ensure only one exists for each network.
                    this.defaultSerializer = getSerializer(false);
                }
            }
        }
        return defaultSerializer;
    }
    
    /**
     * Construct and return a custom serializer.
     */
    public abstract MessageSerializer getSerializer(boolean parseRetain);
    
	public static enum ProtocolVersion {
        CURRENT(1);

        private final int version;

        ProtocolVersion(final int version) {
            this.version = version;
        }

        public int getVersion() {
            return version;
        }
    }

	public int getPort() {
		return port;
	}
	
	public SeedManager getSeedManager() {
		return seedManager;
	}
	
	public long getPacketMagic() {
        return packetMagic;
    }
}
