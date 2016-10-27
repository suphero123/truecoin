package org.truechain.test;

import java.io.UnsupportedEncodingException;

import org.truechain.crypto.Sha256Hash;
import org.truechain.utils.Utils;

public class SHA256Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] hash =  Sha256Hash.hashTwice("sdfsadf".getBytes());
		for (int i = 0; i < hash.length; i++) {
			System.out.println(hash[i]);
		}
		System.out.println(Utils.toString(hash,"utf-8"));
	}
}
