package org.truechain.test;

import org.truechain.utils.Base16;

public class Base16Test {

	public static void main(String[] args) {
		String res = Base16.encode("12#$%089*)(8df0sa0SfLKALf0sa9ss3456".getBytes());
		System.out.println(res);
	}
}
