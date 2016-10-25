package org.truechain.network;

import org.truechain.message.MessageSerializer;

import com.sun.istack.internal.Nullable;

public abstract class NetworkParameters {
	
    public static final String ID_MAINNET = "org.ricechain.production";
    public static final String ID_TESTNET = "org.ricechain.test";
    
	//种子管理器
	protected SeedManager seedManager;
	
	protected String id;
	//端口
	protected int port;
	protected long packetMagic;
	
    protected int p2shHeader;
    protected int addressHeader;
	
    protected int dumpedPrivateKeyHeader;
    
    //允许的地址前缀
    protected int[] acceptableAddressCodes;
	
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

	@Nullable
    public static NetworkParameters fromID(String id) {
        if (id.equals(ID_MAINNET)) {
            return MainNetParams.get();
        } else if (id.equals(ID_TESTNET)) {
            return TestNetworkParameters.get();
        } else {
            return null;
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

	public int getBestBlockHeight() {
		return 1000;
	}
	
	/** First byte of a base58 encoded dumped private key. See {@link org.bitcoinj.core.DumpedPrivateKey}. */
    public int getDumpedPrivateKeyHeader() {
        return dumpedPrivateKeyHeader;
    }
    
    public String getId() {
		return id;
	}
    
    /**
     * The version codes that prefix addresses which are acceptable on this network. Although Satoshi intended these to
     * be used for "versioning", in fact they are today used to discriminate what kind of data is contained in the
     * address and to prevent accidentally sending coins across chains which would destroy them.
     */
    public int[] getAcceptableAddressCodes() {
        return acceptableAddressCodes;
    }
    
    public int getP2shHeader() {
		return p2shHeader;
	}
    
    public int getAddressHeader() {
		return addressHeader;
	}
}
