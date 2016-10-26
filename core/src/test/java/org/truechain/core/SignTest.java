package org.truechain.core;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.math.ec.FixedPointUtil;
import org.truechain.address.Address;
import org.truechain.core.ECKey.ECDSASignature;
import org.truechain.network.MainNetParams;
import org.truechain.network.NetworkParameters;
import org.truechain.utils.Base16;
import org.truechain.utils.Base58;
import org.truechain.utils.Utils;

public class SignTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		//生成私钥
		SecureRandom random = SecureRandom.getInstanceStrong();
		byte[] priBytes = new byte[32];
		random.nextBytes(priBytes);
//		BigInteger priNumber = new BigInteger(priBytes);
		BigInteger priNumber = new BigInteger(Base16.decode("18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725"));
		System.out.println("pri number : "+priNumber);
		//椭圆曲线加密生成公匙
		ECKey key = ECKey.fromPrivate(Base16.decode("18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725"));
		//
		System.out.println("prikey : "+key.getPrivateKeyAsHex());
		String pubkey = key.getPublicKeyAsHex();
		System.out.println("pubkey : "+pubkey);
		
		byte[] hash160 = Utils.sha256hash160(key.getPubKey());
		
		System.out.println("hash160 : "+Base16.encode(hash160));
		
		
		
		byte[] hash160withver = new byte[hash160.length + 1];
		hash160withver[0] = 0;
		
		System.arraycopy(hash160, 0, hash160withver, 1, hash160.length);
		
		byte[] addbytes = Sha256Hash.hashTwice(hash160withver);

		System.out.println(Base16.encode(addbytes));
		
		byte[] ta = new byte[4];
		System.arraycopy(addbytes, 0, ta, 0, 4);
		System.out.println(Base16.encode(ta));
		
		byte[] address = new byte[hash160withver.length + ta.length];
		System.arraycopy(hash160withver, 0, address, 0, hash160withver.length);
		System.arraycopy(ta, 0, address, hash160withver.length, ta.length);
		
		System.out.println(Base16.encode(address));
		
		System.out.println(Base58.encode(address));
		
		//地址生成测试完成//
		
		
		//测试签名
		NetworkParameters net = MainNetParams.get();
		
		//待签名的消息
		String msg = "this is a test message !";
		
		X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
		
		FixedPointUtil.precompute(CURVE_PARAMS.getG(), 12);
		
		ECDomainParameters CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                CURVE_PARAMS.getH());
		
		
		ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
        ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(priNumber, CURVE);
        signer.init(true, privKey);
        BigInteger[] components = signer.generateSignature(Sha256Hash.hashTwice(Base16.decode(msg)));
        
        ECDSASignature signature = new ECKey.ECDSASignature(components[0], components[1]).toCanonicalised();
//        signature.
        
	}
}
