package org.truechain.core;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.account.AccountManager;
import org.truechain.address.Address;
import org.truechain.crypto.ECKey;
import org.truechain.network.MainNetParams;
import org.truechain.utils.Hex;
import org.truechain.utils.Utils;

public class AccountTest {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void testAddress() {
		ECKey key = AccountManager.newPriKey();
		
		log.info("pri key is :" + key.getPrivateKeyAsHex());
		log.info("pub key is :" + key.getPublicKeyAsHex());
		log.info("pub key not compressed is :" + key.getPublicKeyAsHex(false));
		
		MainNetParams network = MainNetParams.get();
		
		Address address = AccountManager.newAddress(network, Address.VERSION_DEFAULT);
		log.info("new address is :" + address);
		
		address = Address.fromP2PKHash(network, Address.VERSION_DEFAULT, 
				Utils.sha256hash160(ECKey.fromPrivate(new BigInteger(Hex.decode("18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725")))
						.getPubKey(false)));
		assertEquals(address.getBase58(), "16UwLL9Risc3QfPqBUvKofHmBQ7wMtjvM");
		
		address = AccountManager.newAddressFromPrikey(network, Address.VERSION_DEFAULT, new BigInteger(Hex.decode("18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725")));
		assertEquals(address.getBase58(), "16UwLL9Risc3QfPqBUvKofHmBQ7wMtjvM");
		
		address = Address.fromBase58(network, "179sduXmc57hbYsP5Ar476pJKkdx9CyiXD");
		assertEquals(address.getHash160AsHex(), "437e59f902d96c513ecba8e997f982e40a65b461");
		System.out.println(address.getHash160AsHex());
	}
}
