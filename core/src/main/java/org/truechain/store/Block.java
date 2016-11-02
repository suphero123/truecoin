package org.truechain.store;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.truechain.core.VarInt;
import org.truechain.core.exception.ProtocolException;
import org.truechain.crypto.Sha256Hash;
import org.truechain.message.Message;
import org.truechain.network.NetworkParameters;
import org.truechain.transaction.Transaction;
import org.truechain.utils.Utils;

/**
 * 区块完整信息
 * @author ln
 *
 */
public class Block extends Message {

	//区块版本
	private long version;
	//区块头信息
	private BlockHeader blockHeader;
	//交易列表
	private List<Transaction> txs;
	
	public Block(NetworkParameters network) {
		super(network);
	}

	@Override
	protected void parse() throws ProtocolException {
		
	}

	/**
	 * 序列化区块
	 */
	protected void serializeToStream(OutputStream stream) throws IOException {
		Utils.uint32ToByteStreamLE(version, stream);
		stream.write(blockHeader.getPreHash().getBytes());
		stream.write(blockHeader.getMerkleHash().getBytes());
		
		Utils.uint32ToByteStreamLE(blockHeader.getTime(), stream);
		
		//交易数量
		stream.write(new VarInt(blockHeader.getTxCount()).encode());
		for (int i = 0; i < blockHeader.getTxCount(); i++) {
			Transaction tx = txs.get(i);
			stream.write(tx.baseSerialize());
		}
    }
	
	/**
	 * 计算区块hash
	 * @return
	 */
	public Sha256Hash getHash() {
		Sha256Hash id = Sha256Hash.twiceOf(baseSerialize());
		if(blockHeader.getHash() != null) {
			Utils.checkState(blockHeader.getHash().equals(id), "区块信息不正确");
		} else {
			blockHeader.setHash(id);
		}
		return blockHeader.getHash();
	}

	/**
	 * 计算区块的梅克尔树根
	 * @return
	 */
	public Sha256Hash getMerkleHash() {
		
		List<byte[]> tree = new ArrayList<byte[]>();
        for (Transaction t : txs) {
            tree.add(t.getHash().getBytes());
        }
        int levelOffset = 0;
        for (int levelSize = txs.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            for (int left = 0; left < levelSize; left += 2) {
                int right = Math.min(left + 1, levelSize - 1);
                byte[] leftBytes = Utils.reverseBytes(tree.get(levelOffset + left));
                byte[] rightBytes = Utils.reverseBytes(tree.get(levelOffset + right));
                tree.add(Utils.reverseBytes(Sha256Hash.hashTwice(leftBytes, 0, 32, rightBytes, 0, 32)));
            }
            levelOffset += levelSize;
        }
        Sha256Hash merkleHash = Sha256Hash.wrap(tree.get(tree.size() - 1));
        if(blockHeader.getMerkleHash() == null) {
        	blockHeader.setMerkleHash(merkleHash);
        } else {
        	Utils.checkState(blockHeader.getMerkleHash().equals(merkleHash), "the merkle hash is error");
        }
		return merkleHash;
	}
	
	public BlockHeader getBlockHeader() {
		return blockHeader;
	}
	public void setBlockHeader(BlockHeader blockHeader) {
		this.blockHeader = blockHeader;
	}
	public List<Transaction> getTxs() {
		return txs;
	}
	public void setTxs(List<Transaction> txs) {
		this.txs = txs;
	}

}
