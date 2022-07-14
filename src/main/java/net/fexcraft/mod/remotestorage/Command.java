package net.fexcraft.mod.remotestorage;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.fexcraft.lib.common.math.Time;
import net.fexcraft.lib.mc.api.registry.fCommand;
import net.fexcraft.lib.mc.utils.Print;
import net.fexcraft.lib.mc.utils.Static;
import net.fexcraft.mod.remotestorage.GroupManager.Group;
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
			Print.chat(sender, "/resto company info");
			Print.chat(sender, "/resto company create <name>");
			Print.chat(sender, "/resto company invite <player/uuid>");
			Print.chat(sender, "/resto company join <company-key>");
			Print.chat(sender, "/resto company remove <player/uuid>");
			Print.chat(sender, "/resto company staff add <player/uuid>");
			Print.chat(sender, "/resto company staff remove <player/uuid>");
			Print.chat(sender, "/resto company setmanager <player/uuid>");
			Print.chat(sender, "/resto company disband");
			Print.chat(sender, "/resto company rename");
			Print.chat(sender, "/resto company leave");
			return;
		}
		Group group = GroupManager.of(player);
		UUID uuid = player.getGameProfile().getId();
		if(!args[0].equals("company") || args.length < 2){
			Print.chat(sender, "Unknown argument.");
			return;
		}
		switch(args[1]){
			case "create":{
				if(group != null){
					Print.chat(sender, "You are already in a company.");
					Print.chat(sender, "Use &e/resto company leave &7or &e/resto company disband");
					return;
				}
				if(args.length < 3){
					Print.chat(sender, "Please enter a company name.");
					return;
				}
				GroupManager.register(get(args, 2), player);
				Print.chat(sender, "Company registered, use '/resto company info' to see details.");
				break;
			}
			case "info":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				Print.chat(sender, "&6Key&0:&7 " + group.key);
				Print.chat(sender, "&6Name&0:&7 " + group.name);
				Print.chat(sender, "&eCreator&0:&7 " + Static.getPlayerNameByUUID(group.creator));
				Print.chat(sender, "&eCreated&0:&7 " + Time.getAsString(group.created));
				Print.chat(sender, "&6Manager&0:&7 " + Static.getPlayerNameByUUID(group.manager));
				Print.chat(sender, "&6Members&0:&7 " + group.members.size());
				Print.chat(sender, "&eStaff&0:&7 " + group.staff.size());
				break;
			}
			case "rename":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(!group.staff.contains(uuid)){
					Print.chat(sender, "You are not company staff.");
					return;
				}
				if(args.length < 3){
					Print.chat(sender, "Please enter a new company name.");
					return;
				}
				group.name = get(args, 2);
				Print.chat(sender, "Company renamed, use '/resto company info' to see details.");
				break;
			}
			case "leave":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(group.staff.contains(uuid) && group.staff.size() < 2){
					Print.chat(sender, "You cannot leave as last staff member.");
					Print.chat(sender, "Disband the company instead.");
					return;
				}
				if(group.manager.equals(uuid)){
					Print.chat(sender, "You cannot leave as manager.");
					Print.chat(sender, "Disband the company instead or assign new manager.");
					return;
				}
				group.members.remove(uuid);
				group.staff.remove(uuid);
				Print.chat(sender, "Company left.");
				break;
			}
			case "disband":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(!group.manager.equals(uuid)){
					Print.chat(sender, "Only Manager can disband a company.");
					return;
				}
				GroupManager.remove(group);
				Print.chat(sender, "Company removed.");
				break;
			}
			case "invite":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(!group.staff.contains(uuid)){
					Print.chat(sender, "You must be staff to manage members.");
					return;
				}
				if(args.length < 3){
					Print.chat(sender, "Please enter a player name or UUID.");
					return;
				}
				try{
					UUID inv = null;
					GameProfile gp = server.getPlayerProfileCache().getGameProfileForUsername(args[2]);
					if(gp != null && gp.getId() != null && gp.getName() != null){
						group.invites.add(inv = gp.getId());
					}
					else{
						group.invites.add(inv = UUID.fromString(args[2]));
					}
					Print.chat(sender, "Invite saved for this server session.");
					EntityPlayer oth = server.getPlayerList().getPlayerByUUID(inv);
					if(oth != null){
						Print.chat(oth, "&You got invited to &6" + group.name + "&7!");
						Print.chat(oth, "&7Use &6/resto company join " + group.key + " &7to join.");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					Print.chat(sender, "Error while parsing name/uuid");
				}
				break;
			}
			case "join":{
				if(group != null){
					Print.chat(sender, "Please leave your current company first.");
					return;
				}
				Group inv = GroupManager.get(args[2]);
				if(inv == null){
					Print.chat(sender, "Company not found.");
					return;
				}
				inv.members.add(uuid);
				Print.chat(sender, "Company joined.");
				break;
			}
			case "remove":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(!group.staff.contains(uuid)){
					Print.chat(sender, "You must be staff to manage members.");
					return;
				}
				if(args.length < 3){
					Print.chat(sender, "Please enter a player name or UUID.");
					return;
				}
				try{
					UUID rem = null;
					GameProfile gp = server.getPlayerProfileCache().getGameProfileForUsername(args[2]);
					if(gp != null && gp.getId() != null && gp.getName() != null){
						rem = gp.getId();
					}
					else{
						rem = UUID.fromString(args[2]);
					}
					if(group.staff.contains(rem)){
						Print.chat(sender, "&7Please use &6/resto company staff remove <name/uuid> &7instead!");
						return;
					}
					if(!group.members.contains(rem)){
						Print.chat(sender, "&7Player is not a company member.");
						return;
					}
					group.members.remove(rem);
					Print.chat(sender, "Player removed.");
				}
				catch(Exception e){
					e.printStackTrace();
					Print.chat(sender, "Error while parsing name/uuid");
				}
				break;
			}
			case "staff":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(!group.manager.equals(uuid)){
					Print.chat(sender, "You must be manager to manage staff.");
					return;
				}
				if(args.length < 3){
					Print.chat(sender, "Please specify if you want to add or remove.");
				}
				if(args.length < 4){
					Print.chat(sender, "Please enter a player name or UUID.");
				}
				if(args[2].equals("add")){
					try{
						UUID stf = null;
						GameProfile gp = server.getPlayerProfileCache().getGameProfileForUsername(args[2]);
						if(gp != null && gp.getId() != null && gp.getName() != null){
							stf = gp.getId();
						}
						else{
							stf = UUID.fromString(args[2]);
						}
						if(!group.members.contains(stf)){
							Print.chat(sender, "&7Player is not a company member.");
							return;
						}
						if(group.staff.contains(stf)){
							Print.chat(sender, "&7Player is already staff!");
							return;
						}
						group.staff.add(stf);
						Print.chat(sender, "Staff added.");
						EntityPlayer oth = server.getPlayerList().getPlayerByUUID(stf);
						if(oth != null){
							Print.chat(oth, "&You got promoted to staff!");
						}
					}
					catch(Exception e){
						e.printStackTrace();
						Print.chat(sender, "Error while parsing name/uuid");
					}
				}
				else if(args[2].equals("remove")){
					UUID rem = null;
					GameProfile gp = server.getPlayerProfileCache().getGameProfileForUsername(args[2]);
					if(gp != null && gp.getId() != null && gp.getName() != null){
						rem = gp.getId();
					}
					else{
						rem = UUID.fromString(args[2]);
					}
					if(!group.staff.contains(rem)){
						Print.chat(sender, "&7Player is not a staff member.");
						return;
					}
					if(group.manager.equals(rem)){
						Print.chat(sender, "&7Player is the manager.");
						return;
					}
					group.staff.remove(rem);
					Print.chat(sender, "Staff removed.");
				}
				else{
					Print.chat(sender, "Invalid argument.");
				}
				break;
			}
			case "setmanager":{
				if(group == null){
					Print.chat(sender, "You are not in a company.");
					return;
				}
				if(!group.manager.equals(uuid)){
					Print.chat(sender, "You must be manager to set a new manager.");
					return;
				}
				if(args.length < 3){
					Print.chat(sender, "Please enter a player name or UUID.");
					return;
				}
				try{
					UUID man = null;
					GameProfile gp = server.getPlayerProfileCache().getGameProfileForUsername(args[2]);
					if(gp != null && gp.getId() != null && gp.getName() != null){
						man = gp.getId();
					}
					else{
						man = UUID.fromString(args[2]);
					}
					if(!group.members.contains(man)){
						Print.chat(sender, "&7Player is not a company member.");
						return;
					}
					if(!group.staff.contains(man)) group.staff.add(man);
					group.manager = man;
					Print.chat(sender, "Manager set.");
					EntityPlayer oth = server.getPlayerList().getPlayerByUUID(man);
					if(oth != null){
						Print.chat(oth, "&You got promoted to manager!");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					Print.chat(sender, "Error while parsing name/uuid");
				}
				break;
			}
			default:{
				Print.chat(sender, "Unknown argument.");
				break;
			}
		}
	}

	private String get(String[] args, int at){
		String str = args[at];
		for(int i = at + 1; i < args.length; i++) str += " " + args[i];
		return str;
	}

}
