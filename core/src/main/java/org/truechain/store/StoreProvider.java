package org.truechain.store;

/**
 * 存储服务
 * @author ln
 *
 */
public interface StoreProvider {

	void put(Store store);
	
	Store get(byte[] key);
	
	void delete(byte[] key);
}
