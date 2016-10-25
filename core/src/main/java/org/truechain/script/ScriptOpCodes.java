/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.truechain.script;

import java.util.HashMap;
import java.util.Map;

/**
 * Various constants that define the assembly-like scripting language that forms part of the Bitcoin protocol.
 * See {@link org.bitcoinj.script.Script} for details. Also provides a method to convert them to a string.
 */
public class ScriptOpCodes {
    // push value
    public static final int OP_0 = 0x00; // push empty vector
    public static final int OP_FALSE = OP_0;
    public static final int OP_PUSHDATA1 = 0x4c;
    public static final int OP_PUSHDATA2 = 0x4d;
    public static final int OP_PUSHDATA4 = 0x4e;
    public static final int OP_1NEGATE = 0x4f;
    public static final int OP_RESERVED = 0x50;
    public static final int OP_1 = 0x51;
    public static final int OP_TRUE = OP_1;
    public static final int OP_2 = 0x52;
    public static final int OP_3 = 0x53;
    public static final int OP_4 = 0x54;
    public static final int OP_5 = 0x55;
    public static final int OP_6 = 0x56;
    public static final int OP_7 = 0x57;
    public static final int OP_8 = 0x58;
    public static final int OP_9 = 0x59;
    public static final int OP_10 = 0x5a;
    public static final int OP_11 = 0x5b;
    public static final int OP_12 = 0x5c;
    public static final int OP_13 = 0x5d;
    public static final int OP_14 = 0x5e;
    public static final int OP_15 = 0x5f;
    public static final int OP_16 = 0x60;

    // control
    public static final int OP_NOP = 0x61;
    public static final int OP_VER = 0x62;
    public static final int OP_IF = 0x63;
    public static final int OP_NOTIF = 0x64;
    public static final int OP_VERIF = 0x65;
    public static final int OP_VERNOTIF = 0x66;
    public static final int OP_ELSE = 0x67;
    public static final int OP_ENDIF = 0x68;
    public static final int OP_VERIFY = 0x69;
    public static final int OP_RETURN = 0x6a;

    // stack ops
    public static final int OP_TOALTSTACK = 0x6b;
    public static final int OP_FROMALTSTACK = 0x6c;
    public static final int OP_2DROP = 0x6d;
    public static final int OP_2DUP = 0x6e;
    public static final int OP_3DUP = 0x6f;
    public static final int OP_2OVER = 0x70;
    public static final int OP_2ROT = 0x71;
    public static final int OP_2SWAP = 0x72;
    public static final int OP_IFDUP = 0x73;
    public static final int OP_DEPTH = 0x74;
    public static final int OP_DROP = 0x75;
    public static final int OP_DUP = 0x76;
    public static final int OP_NIP = 0x77;
    public static final int OP_OVER = 0x78;
    public static final int OP_PICK = 0x79;
    public static final int OP_ROLL = 0x7a;
    public static final int OP_ROT = 0x7b;
    public static final int OP_SWAP = 0x7c;
    public static final int OP_TUCK = 0x7d;

    // splice ops
    public static final int OP_CAT = 0x7e;
    public static final int OP_SUBSTR = 0x7f;
    public static final int OP_LEFT = 0x80;
    public static final int OP_RIGHT = 0x81;
    public static final int OP_SIZE = 0x82;

    // bit logic
    public static final int OP_INVERT = 0x83;
    public static final int OP_AND = 0x84;
    public static final int OP_OR = 0x85;
    public static final int OP_XOR = 0x86;
    public static final int OP_EQUAL = 0x87;
    public static final int OP_EQUALVERIFY = 0x88;
    public static final int OP_RESERVED1 = 0x89;
    public static final int OP_RESERVED2 = 0x8a;

    // numeric
    public static final int OP_1ADD = 0x8b;
    public static final int OP_1SUB = 0x8c;
    public static final int OP_2MUL = 0x8d;
    public static final int OP_2DIV = 0x8e;
    public static final int OP_NEGATE = 0x8f;
    public static final int OP_ABS = 0x90;
    public static final int OP_NOT = 0x91;
    public static final int OP_0NOTEQUAL = 0x92;
    public static final int OP_ADD = 0x93;
    public static final int OP_SUB = 0x94;
    public static final int OP_MUL = 0x95;
    public static final int OP_DIV = 0x96;
    public static final int OP_MOD = 0x97;
    public static final int OP_LSHIFT = 0x98;
    public static final int OP_RSHIFT = 0x99;
    public static final int OP_BOOLAND = 0x9a;
    public static final int OP_BOOLOR = 0x9b;
    public static final int OP_NUMEQUAL = 0x9c;
    public static final int OP_NUMEQUALVERIFY = 0x9d;
    public static final int OP_NUMNOTEQUAL = 0x9e;
    public static final int OP_LESSTHAN = 0x9f;
    public static final int OP_GREATERTHAN = 0xa0;
    public static final int OP_LESSTHANOREQUAL = 0xa1;
    public static final int OP_GREATERTHANOREQUAL = 0xa2;
    public static final int OP_MIN = 0xa3;
    public static final int OP_MAX = 0xa4;
    public static final int OP_WITHIN = 0xa5;

    // crypto
    public static final int OP_RIPEMD160 = 0xa6;
    public static final int OP_SHA1 = 0xa7;
    public static final int OP_SHA256 = 0xa8;
    public static final int OP_HASH160 = 0xa9;
    public static final int OP_HASH256 = 0xaa;
    public static final int OP_CODESEPARATOR = 0xab;
    public static final int OP_CHECKSIG = 0xac;
    public static final int OP_CHECKSIGVERIFY = 0xad;
    public static final int OP_CHECKMULTISIG = 0xae;
    public static final int OP_CHECKMULTISIGVERIFY = 0xaf;

    // block state
    /** Check lock time of the block. Introduced in BIP 65, replacing OP_NOP2 */
    public static final int OP_CHECKLOCKTIMEVERIFY = 0xb1;

    // expansion
    public static final int OP_NOP1 = 0xb0;
    /** Deprecated by BIP 65 */
    @Deprecated
    public static final int OP_NOP2 = OP_CHECKLOCKTIMEVERIFY;
    public static final int OP_NOP3 = 0xb2;
    public static final int OP_NOP4 = 0xb3;
    public static final int OP_NOP5 = 0xb4;
    public static final int OP_NOP6 = 0xb5;
    public static final int OP_NOP7 = 0xb6;
    public static final int OP_NOP8 = 0xb7;
    public static final int OP_NOP9 = 0xb8;
    public static final int OP_NOP10 = 0xb9;
    public static final int OP_INVALIDOPCODE = 0xff;
    
    static class TableMap<K, V> extends HashMap<K, V> {
		private static final long serialVersionUID = -8453899459419473892L;

		public TableMap<K, V> add(K key, V value) {
			add(key, value);
			return this;
		}
    }

    private static final Map<Integer, String> opCodeMap = new TableMap<Integer, String>()
        .add(OP_0, "0")
        .add(OP_PUSHDATA1, "PUSHDATA1")
        .add(OP_PUSHDATA2, "PUSHDATA2")
        .add(OP_PUSHDATA4, "PUSHDATA4")
        .add(OP_1NEGATE, "1NEGATE")
        .add(OP_RESERVED, "RESERVED")
        .add(OP_1, "1")
        .add(OP_2, "2")
        .add(OP_3, "3")
        .add(OP_4, "4")
        .add(OP_5, "5")
        .add(OP_6, "6")
        .add(OP_7, "7")
        .add(OP_8, "8")
        .add(OP_9, "9")
        .add(OP_10, "10")
        .add(OP_11, "11")
        .add(OP_12, "12")
        .add(OP_13, "13")
        .add(OP_14, "14")
        .add(OP_15, "15")
        .add(OP_16, "16")
        .add(OP_NOP, "NOP")
        .add(OP_VER, "VER")
        .add(OP_IF, "IF")
        .add(OP_NOTIF, "NOTIF")
        .add(OP_VERIF, "VERIF")
        .add(OP_VERNOTIF, "VERNOTIF")
        .add(OP_ELSE, "ELSE")
        .add(OP_ENDIF, "ENDIF")
        .add(OP_VERIFY, "VERIFY")
        .add(OP_RETURN, "RETURN")
        .add(OP_TOALTSTACK, "TOALTSTACK")
        .add(OP_FROMALTSTACK, "FROMALTSTACK")
        .add(OP_2DROP, "2DROP")
        .add(OP_2DUP, "2DUP")
        .add(OP_3DUP, "3DUP")
        .add(OP_2OVER, "2OVER")
        .add(OP_2ROT, "2ROT")
        .add(OP_2SWAP, "2SWAP")
        .add(OP_IFDUP, "IFDUP")
        .add(OP_DEPTH, "DEPTH")
        .add(OP_DROP, "DROP")
        .add(OP_DUP, "DUP")
        .add(OP_NIP, "NIP")
        .add(OP_OVER, "OVER")
        .add(OP_PICK, "PICK")
        .add(OP_ROLL, "ROLL")
        .add(OP_ROT, "ROT")
        .add(OP_SWAP, "SWAP")
        .add(OP_TUCK, "TUCK")
        .add(OP_CAT, "CAT")
        .add(OP_SUBSTR, "SUBSTR")
        .add(OP_LEFT, "LEFT")
        .add(OP_RIGHT, "RIGHT")
        .add(OP_SIZE, "SIZE")
        .add(OP_INVERT, "INVERT")
        .add(OP_AND, "AND")
        .add(OP_OR, "OR")
        .add(OP_XOR, "XOR")
        .add(OP_EQUAL, "EQUAL")
        .add(OP_EQUALVERIFY, "EQUALVERIFY")
        .add(OP_RESERVED1, "RESERVED1")
        .add(OP_RESERVED2, "RESERVED2")
        .add(OP_1ADD, "1ADD")
        .add(OP_1SUB, "1SUB")
        .add(OP_2MUL, "2MUL")
        .add(OP_2DIV, "2DIV")
        .add(OP_NEGATE, "NEGATE")
        .add(OP_ABS, "ABS")
        .add(OP_NOT, "NOT")
        .add(OP_0NOTEQUAL, "0NOTEQUAL")
        .add(OP_ADD, "ADD")
        .add(OP_SUB, "SUB")
        .add(OP_MUL, "MUL")
        .add(OP_DIV, "DIV")
        .add(OP_MOD, "MOD")
        .add(OP_LSHIFT, "LSHIFT")
        .add(OP_RSHIFT, "RSHIFT")
        .add(OP_BOOLAND, "BOOLAND")
        .add(OP_BOOLOR, "BOOLOR")
        .add(OP_NUMEQUAL, "NUMEQUAL")
        .add(OP_NUMEQUALVERIFY, "NUMEQUALVERIFY")
        .add(OP_NUMNOTEQUAL, "NUMNOTEQUAL")
        .add(OP_LESSTHAN, "LESSTHAN")
        .add(OP_GREATERTHAN, "GREATERTHAN")
        .add(OP_LESSTHANOREQUAL, "LESSTHANOREQUAL")
        .add(OP_GREATERTHANOREQUAL, "GREATERTHANOREQUAL")
        .add(OP_MIN, "MIN")
        .add(OP_MAX, "MAX")
        .add(OP_WITHIN, "WITHIN")
        .add(OP_RIPEMD160, "RIPEMD160")
        .add(OP_SHA1, "SHA1")
        .add(OP_SHA256, "SHA256")
        .add(OP_HASH160, "HASH160")
        .add(OP_HASH256, "HASH256")
        .add(OP_CODESEPARATOR, "CODESEPARATOR")
        .add(OP_CHECKSIG, "CHECKSIG")
        .add(OP_CHECKSIGVERIFY, "CHECKSIGVERIFY")
        .add(OP_CHECKMULTISIG, "CHECKMULTISIG")
        .add(OP_CHECKMULTISIGVERIFY, "CHECKMULTISIGVERIFY")
        .add(OP_NOP1, "NOP1")
        .add(OP_CHECKLOCKTIMEVERIFY, "CHECKLOCKTIMEVERIFY")
        .add(OP_NOP3, "NOP3")
        .add(OP_NOP4, "NOP4")
        .add(OP_NOP5, "NOP5")
        .add(OP_NOP6, "NOP6")
        .add(OP_NOP7, "NOP7")
        .add(OP_NOP8, "NOP8")
        .add(OP_NOP9, "NOP9")
        .add(OP_NOP10, "NOP10");

    private static final Map<String, Integer> opCodeNameMap = new TableMap<String, Integer>()
        .add("0", OP_0)
        .add("PUSHDATA1", OP_PUSHDATA1)
        .add("PUSHDATA2", OP_PUSHDATA2)
        .add("PUSHDATA4", OP_PUSHDATA4)
        .add("1NEGATE", OP_1NEGATE)
        .add("RESERVED", OP_RESERVED)
        .add("1", OP_1)
        .add("2", OP_2)
        .add("3", OP_3)
        .add("4", OP_4)
        .add("5", OP_5)
        .add("6", OP_6)
        .add("7", OP_7)
        .add("8", OP_8)
        .add("9", OP_9)
        .add("10", OP_10)
        .add("11", OP_11)
        .add("12", OP_12)
        .add("13", OP_13)
        .add("14", OP_14)
        .add("15", OP_15)
        .add("16", OP_16)
        .add("NOP", OP_NOP)
        .add("VER", OP_VER)
        .add("IF", OP_IF)
        .add("NOTIF", OP_NOTIF)
        .add("VERIF", OP_VERIF)
        .add("VERNOTIF", OP_VERNOTIF)
        .add("ELSE", OP_ELSE)
        .add("ENDIF", OP_ENDIF)
        .add("VERIFY", OP_VERIFY)
        .add("RETURN", OP_RETURN)
        .add("TOALTSTACK", OP_TOALTSTACK)
        .add("FROMALTSTACK", OP_FROMALTSTACK)
        .add("2DROP", OP_2DROP)
        .add("2DUP", OP_2DUP)
        .add("3DUP", OP_3DUP)
        .add("2OVER", OP_2OVER)
        .add("2ROT", OP_2ROT)
        .add("2SWAP", OP_2SWAP)
        .add("IFDUP", OP_IFDUP)
        .add("DEPTH", OP_DEPTH)
        .add("DROP", OP_DROP)
        .add("DUP", OP_DUP)
        .add("NIP", OP_NIP)
        .add("OVER", OP_OVER)
        .add("PICK", OP_PICK)
        .add("ROLL", OP_ROLL)
        .add("ROT", OP_ROT)
        .add("SWAP", OP_SWAP)
        .add("TUCK", OP_TUCK)
        .add("CAT", OP_CAT)
        .add("SUBSTR", OP_SUBSTR)
        .add("LEFT", OP_LEFT)
        .add("RIGHT", OP_RIGHT)
        .add("SIZE", OP_SIZE)
        .add("INVERT", OP_INVERT)
        .add("AND", OP_AND)
        .add("OR", OP_OR)
        .add("XOR", OP_XOR)
        .add("EQUAL", OP_EQUAL)
        .add("EQUALVERIFY", OP_EQUALVERIFY)
        .add("RESERVED1", OP_RESERVED1)
        .add("RESERVED2", OP_RESERVED2)
        .add("1ADD", OP_1ADD)
        .add("1SUB", OP_1SUB)
        .add("2MUL", OP_2MUL)
        .add("2DIV", OP_2DIV)
        .add("NEGATE", OP_NEGATE)
        .add("ABS", OP_ABS)
        .add("NOT", OP_NOT)
        .add("0NOTEQUAL", OP_0NOTEQUAL)
        .add("ADD", OP_ADD)
        .add("SUB", OP_SUB)
        .add("MUL", OP_MUL)
        .add("DIV", OP_DIV)
        .add("MOD", OP_MOD)
        .add("LSHIFT", OP_LSHIFT)
        .add("RSHIFT", OP_RSHIFT)
        .add("BOOLAND", OP_BOOLAND)
        .add("BOOLOR", OP_BOOLOR)
        .add("NUMEQUAL", OP_NUMEQUAL)
        .add("NUMEQUALVERIFY", OP_NUMEQUALVERIFY)
        .add("NUMNOTEQUAL", OP_NUMNOTEQUAL)
        .add("LESSTHAN", OP_LESSTHAN)
        .add("GREATERTHAN", OP_GREATERTHAN)
        .add("LESSTHANOREQUAL", OP_LESSTHANOREQUAL)
        .add("GREATERTHANOREQUAL", OP_GREATERTHANOREQUAL)
        .add("MIN", OP_MIN)
        .add("MAX", OP_MAX)
        .add("WITHIN", OP_WITHIN)
        .add("RIPEMD160", OP_RIPEMD160)
        .add("SHA1", OP_SHA1)
        .add("SHA256", OP_SHA256)
        .add("HASH160", OP_HASH160)
        .add("HASH256", OP_HASH256)
        .add("CODESEPARATOR", OP_CODESEPARATOR)
        .add("CHECKSIG", OP_CHECKSIG)
        .add("CHECKSIGVERIFY", OP_CHECKSIGVERIFY)
        .add("CHECKMULTISIG", OP_CHECKMULTISIG)
        .add("CHECKMULTISIGVERIFY", OP_CHECKMULTISIGVERIFY)
        .add("NOP1", OP_NOP1)
        .add("CHECKLOCKTIMEVERIFY", OP_CHECKLOCKTIMEVERIFY)
        .add("NOP2", OP_NOP2)
        .add("NOP3", OP_NOP3)
        .add("NOP4", OP_NOP4)
        .add("NOP5", OP_NOP5)
        .add("NOP6", OP_NOP6)
        .add("NOP7", OP_NOP7)
        .add("NOP8", OP_NOP8)
        .add("NOP9", OP_NOP9)
        .add("NOP10", OP_NOP10);

    /**
     * Converts the given OpCode into a string (eg "0", "PUSHDATA", or "NON_OP(10)")
     */
    public static String getOpCodeName(int opcode) {
        if (opCodeMap.containsKey(opcode))
            return opCodeMap.get(opcode);

        return "NON_OP(" + opcode + ")";
    }

    /**
     * Converts the given pushdata OpCode into a string (eg "PUSHDATA2", or "PUSHDATA(23)")
     */
    public static String getPushDataName(int opcode) {
        if (opCodeMap.containsKey(opcode))
            return opCodeMap.get(opcode);

        return "PUSHDATA(" + opcode + ")";
    }

    /**
     * Converts the given OpCodeName into an int
     */
    public static int getOpCode(String opCodeName) {
        if (opCodeNameMap.containsKey(opCodeName))
            return opCodeNameMap.get(opCodeName);

        return OP_INVALIDOPCODE;
    }
}
