package org.truechain.store;

import org.truechain.message.BlockMessage;

public class BlockStore extends Store {

	private BlockMessage block;

	public BlockMessage getBlock() {
		return block;
	}

	public void setBlock(BlockMessage block) {
		this.block = block;
	}
}
