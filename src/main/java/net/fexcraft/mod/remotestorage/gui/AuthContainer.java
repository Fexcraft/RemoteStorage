package net.fexcraft.mod.remotestorage.gui;

import net.fexcraft.lib.mc.gui.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

public class AuthContainer extends GenericContainer {

	public AuthContainer(EntityPlayer player, int x, int y, int z){
		super(player);
	}

	@Override
	protected void packet(Side side, NBTTagCompound packet, EntityPlayer player){
		// TODO Auto-generated method stub
		
	}

}
