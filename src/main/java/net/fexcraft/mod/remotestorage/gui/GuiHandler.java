package net.fexcraft.mod.remotestorage.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if(ID == 0) return new AuthContainer(player, x, y, z);
		if(ID == 1) return new TransferContainer(player, x, y, z);
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if(ID == 0) return new AuthGui(player, x, y, z);
		if(ID == 1) return new TransferGui(player, x, y, z);
		return null;
	}
	
}