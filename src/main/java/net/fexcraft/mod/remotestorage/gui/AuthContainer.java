package net.fexcraft.mod.remotestorage.gui;

import net.fexcraft.lib.mc.gui.GenericContainer;
import net.fexcraft.mod.remotestorage.RemoteStorage;
import net.fexcraft.mod.remotestorage.block.TransferTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class AuthContainer extends GenericContainer {
	
	private TransferTE tile;

	public AuthContainer(EntityPlayer player, int x, int y, int z){
		super(player);
		tile = (TransferTE)player.world.getTileEntity(new BlockPos(x, y, z));
	}

	@Override
	protected void packet(Side side, NBTTagCompound packet, EntityPlayer player){
		if(side.isServer()){
			tile.auth = packet.getString("auth");
			tile.token = packet.getString("tok");
            player.openGui(RemoteStorage.INSTANCE, 1, player.world, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
		}
	}

}
