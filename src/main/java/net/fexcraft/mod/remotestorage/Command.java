package net.fexcraft.mod.remotestorage;

import net.fexcraft.lib.mc.api.registry.fCommand;
import net.fexcraft.lib.mc.utils.Print;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

@fCommand
public class Command extends CommandBase {

	@Override
	public String getName(){
		return "resto";
	}

	@Override
	public String getUsage(ICommandSender sender){
		return "/resto";
	}
    
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayer == false) return;
		EntityPlayer player = (EntityPlayer)sender;
		//player.openGui(RemoteStorage.INSTANCE, 0, player.world, 0, 0, 0);
		Print.chat(sender, "&2== == == &0[ &6Remote Storage &0]");
		if(args.length == 0){
			Print.chat(sender, "/resto company");
			Print.chat(sender, "/resto company create <name>");
			Print.chat(sender, "/resto company add <player/uuid>");
			Print.chat(sender, "/resto company remove <player/uuid>");
			Print.chat(sender, "/resto company staff add <player/uuid>");
			Print.chat(sender, "/resto company staff remove <player/uuid>");
			Print.chat(sender, "/resto company rename");
			Print.chat(sender, "/resto company leave");
			return;
		}
	}

}
