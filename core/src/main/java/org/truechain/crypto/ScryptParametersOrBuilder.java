package org.truechain.crypto;

import com.google.protobuf.MessageOrBuilder;

public interface ScryptParametersOrBuilder extends MessageOrBuilder {

    /**
     * <code>required bytes salt = 1;</code>
     *
     * <pre>
     * Salt to use in generation of the wallet password (8 bytes)
     * </pre>
     */
    boolean hasSalt();
    /**
     * <code>required bytes salt = 1;</code>
     *
     * <pre>
     * Salt to use in generation of the wallet password (8 bytes)
     * </pre>
     */
    com.google.protobuf.ByteString getSalt();

    /**
     * <code>optional int64 n = 2 [default = 16384];</code>
     *
     * <pre>
     * CPU/ memory cost parameter
     * </pre>
     */
    boolean hasN();
    /**
     * <code>optional int64 n = 2 [default = 16384];</code>
     *
     * <pre>
     * CPU/ memory cost parameter
     * </pre>
     */
    long getN();

    /**
     * <code>optional int32 r = 3 [default = 8];</code>
     *
     * <pre>
     * Block size parameter
     * </pre>
     */
    boolean hasR();
    /**
     * <code>optional int32 r = 3 [default = 8];</code>
     *
     * <pre>
     * Block size parameter
     * </pre>
     */
    int getR();

    /**
     * <code>optional int32 p = 4 [default = 1];</code>
     *
     * <pre>
     * Parallelisation parameter
     * </pre>
     */
    boolean hasP();
    /**
     * <code>optional int32 p = 4 [default = 1];</code>
     *
     * <pre>
     * Parallelisation parameter
     * </pre>
     */
    int getP();
  
}
