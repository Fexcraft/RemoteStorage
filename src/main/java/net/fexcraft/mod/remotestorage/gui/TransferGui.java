package net.fexcraft.mod.remotestorage.gui;

import net.fexcraft.lib.mc.gui.GenericGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class TransferGui extends GenericGui<TransferContainer> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("remotestorage:textures/gui/transfer.png");

	public TransferGui(EntityPlayer player, int x, int y, int z){
		super(TEXTURE, new TransferContainer(player, x, y, z), player);
		xSize = 232;
		ySize = 148;
	}
	
	@Override
	public void init(){
		//
	}

}
