package org.truechain.account;

import java.math.BigInteger;

import org.truechain.address.Address;
import org.truechain.crypto.ECKey;
import org.truechain.network.NetworkParameters;
import org.truechain.utils.Utils;

/**
 * 账户管理
 * @author ln
 *
 */
public final class AccountManager {

	/**
	 * 生成一个新的私匙/公匙对
	 * @return
	 */
	public final static ECKey newPriKey() {
		return new ECKey();
	}
	
	/**
	 * 生成一个新的地址
	 * @param netword
	 * @return
	 */
	public final static Address newAddress(NetworkParameters network) {
		return newAddress(network, Address.VERSION_DEFAULT);
	}
	
	public final static Address newAddress(NetworkParameters network, ECKey key) {
		return newAddress(network, Address.VERSION_DEFAULT, key);
	}
	
	public final static Address newAddress(NetworkParameters network, int version) {
		ECKey key = newPriKey();
		return Address.fromP2PKHash(network, version, Utils.sha256hash160(key.getPubKey(false)));
	}
	
	public final static Address newAddress(NetworkParameters network, int version, ECKey key) {
		return Address.fromP2PKHash(network, version, Utils.sha256hash160(key.getPubKey(false)));
	}
	
	public final static Address newAddressFromPrikey(NetworkParameters network, int version, BigInteger pri) {
		ECKey key = ECKey.fromPrivate(pri);
		return Address.fromP2PKHash(network, version, Utils.sha256hash160(key.getPubKey(false)));
	}
}
