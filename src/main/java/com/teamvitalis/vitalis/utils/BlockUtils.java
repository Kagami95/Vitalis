package com.teamvitalis.vitalis.utils;

import org.bukkit.block.Block;

public class BlockUtils 
{
	public static boolean isTransparent(Block block) {
		return (!block.getType().isSolid() && !block.isLiquid());
	}
}
