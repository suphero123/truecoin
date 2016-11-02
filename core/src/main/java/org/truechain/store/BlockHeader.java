package org.truechain.store;

import org.truechain.crypto.Sha256Hash;

/**
 * 区块头信息
 * @author ln
 *
 */
public class BlockHeader {

	//区块hash
	private Sha256Hash hash;
	//上一区块hash
	private Sha256Hash preHash;
	//梅克尔树根节点hash
	private Sha256Hash merkleHash;
	//交易数
	private long txCount;
	//时间戳
	private long time;
	//区块高度
	private long height;
	
	
	public Sha256Hash getHash() {
		return hash;
	}
	public void setHash(Sha256Hash hash) {
		this.hash = hash;
	}
	public Sha256Hash getPreHash() {
		return preHash;
	}
	public void setPreHash(Sha256Hash preHash) {
		this.preHash = preHash;
	}
	public Sha256Hash getMerkleHash() {
		return merkleHash;
	}
	public void setMerkleHash(Sha256Hash merkleHash) {
		this.merkleHash = merkleHash;
	}
	public long getTxCount() {
		return txCount;
	}
	public void setTxCount(long txCount) {
		this.txCount = txCount;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getHeight() {
		return height;
	}
	public void setHeight(long height) {
		this.height = height;
	}
}
