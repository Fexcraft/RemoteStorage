package net.fexcraft.mod.remotestorage.gui;

import net.fexcraft.lib.mc.gui.GenericGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class AuthGui extends GenericGui<AuthContainer> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("remotestorage:textures/gui/auth.png");

	public AuthGui(EntityPlayer player, int x, int y, int z){
		super(TEXTURE, new AuthContainer(player, x, y, z), player);
		xSize = 146;
		ySize = 41;
	}
	
	@Override
	public void init(){
		buttons.put("confirm", new BasicButton("confirm", guiLeft + 128, guiTop + 6, 128, 6, 11, 5, true){
			@Override
			public boolean onclick(int mx, int my, int mb){
				NBTTagCompound com = new NBTTagCompound();
				com.setString("org", fields.get("org").getText());
				com.setString("tok", fields.get("tok").getText());
				return true;
			}
		});
		fields.put("org", new TextField(0, fontRenderer, guiLeft + 9, guiTop + 13, 128, 7, false));
		fields.put("tok", new TextField(0, fontRenderer, guiLeft + 9, guiTop + 25, 128, 7, false));
	}

}
