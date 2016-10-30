package org.truechain.store;

public abstract class Store {
	
	protected byte[] key;

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}
}
