package net.fexcraft.mod.remotestorage.gui;

import net.fexcraft.lib.mc.gui.GenericContainer;
import net.fexcraft.mod.remotestorage.block.TransferTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class TransferContainer extends GenericContainer {
	
	private TransferTE tile;

	public TransferContainer(EntityPlayer player, int x, int y, int z){
		super(player);
		tile = (TransferTE)player.world.getTileEntity(new BlockPos(x, y, z));
	}

	@Override
	protected void packet(Side side, NBTTagCompound packet, EntityPlayer player){
		//
	}

}
