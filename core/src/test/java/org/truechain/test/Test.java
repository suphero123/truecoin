package org.truechain.test;

import java.util.Stack;

import org.truechain.utils.Base16;

public class Test {

	public static void main(String[] args) {
		String txt = "04ffff001d0104455468652054696d65732030332f4a616e2f32303039204368616e63656c6c6f72206f6e206272696e6b206f66207365636f6e64206261696c6f757420666f722062616e6b73";
		byte[] b = Base16.decode(txt);
		System.out.println(new String(b));
		
		
		long num = 5000;
		
		Stack<Byte> result = new Stack<Byte>();
        final boolean neg = num < 0;
        long absvalue = Math.abs(num);

        while (absvalue != 0) {
            result.push((byte) (absvalue & 0xff));
            absvalue >>= 8;
        }
        System.out.println(result);
	}
}
