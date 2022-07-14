package net.fexcraft.mod.remotestorage.gui;

import net.fexcraft.lib.mc.gui.GenericContainer;
import net.fexcraft.mod.remotestorage.GroupManager;
import net.fexcraft.mod.remotestorage.GroupManager.Group;
import net.fexcraft.mod.remotestorage.RemoteStorage;
import net.fexcraft.mod.remotestorage.block.TransferTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class AuthContainer extends GenericContainer {
	
	private TransferTE tile;
	private Group group;

	public AuthContainer(EntityPlayer player, int x, int y, int z){
		super(player);
		tile = (TransferTE)player.world.getTileEntity(new BlockPos(x, y, z));
		if(!player.world.isRemote) group = GroupManager.of(player);
	}

	@Override
	protected void packet(Side side, NBTTagCompound packet, EntityPlayer player){
		if(side.isServer()){
			tile.auth = packet.getString("org");
			tile.token = packet.getString("tok");
			tile.company = group.key;
            player.openGui(RemoteStorage.INSTANCE, 1, player.world, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
		}
	}

}
