package org.truechain.crypto;

public enum EncryptionType implements com.google.protobuf.ProtocolMessageEnum {

	/**
     * <code>UNENCRYPTED = 1;</code>
     *
     * <pre>
     * All keys in the wallet are unencrypted
     * </pre>
     */
    UNENCRYPTED(0, 1),
    /**
     * <code>ENCRYPTED_SCRYPT_AES = 2;</code>
     *
     * <pre>
     * All keys are encrypted with a passphrase based KDF of scrypt and AES encryption
     * </pre>
     */
    ENCRYPTED_SCRYPT_AES(1, 2),
    ;

    /**
     * <code>UNENCRYPTED = 1;</code>
     *
     * <pre>
     * All keys in the wallet are unencrypted
     * </pre>
     */
    public static final int UNENCRYPTED_VALUE = 1;
    /**
     * <code>ENCRYPTED_SCRYPT_AES = 2;</code>
     *
     * <pre>
     * All keys are encrypted with a passphrase based KDF of scrypt and AES encryption
     * </pre>
     */
    public static final int ENCRYPTED_SCRYPT_AES_VALUE = 2;


    public final int getNumber() { return value; }

    public static EncryptionType valueOf(int value) {
      switch (value) {
        case 1: return UNENCRYPTED;
        case 2: return ENCRYPTED_SCRYPT_AES;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<EncryptionType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<EncryptionType>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<EncryptionType>() {
            public EncryptionType findValueByNumber(int number) {
              return EncryptionType.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return null;
    }

    private static final EncryptionType[] VALUES = values();

    public static EncryptionType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private EncryptionType(int index, int value) {
      this.index = index;
      this.value = value;
    }
}
