package net.fexcraft.mod.remotestorage;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.fexcraft.lib.common.json.JsonUtil;
import net.minecraft.entity.player.EntityPlayer;

public class GroupManager {
	
	public static ConcurrentHashMap<String, Group> groups = new ConcurrentHashMap<>();
	private static File file;

	public static void load(File fl){
		groups.clear();
		JsonObject obj = JsonUtil.get(file = fl);
		if(!obj.has("format") || obj.has("groups")) return;
		obj.get("groups").getAsJsonObject().entrySet().forEach(entry -> {
			JsonObject grobj = entry.getValue().getAsJsonObject();
			Group group = new Group(entry.getKey());
			if(grobj.has("members")) grobj.get("members").getAsJsonArray().forEach(elm -> group.members.add(UUID.fromString(elm.getAsString())));
			if(grobj.has("staff")) grobj.get("staff").getAsJsonArray().forEach(elm -> group.staff.add(UUID.fromString(elm.getAsString())));
			group.created = grobj.get("created").getAsLong();
			group.creator = UUID.fromString(grobj.get("creator").getAsString());
			groups.put(entry.getKey(), group);
		});
	}

	public static void save(){
		JsonObject obj = new JsonObject();
		obj.addProperty("format", 1);
		obj.add("groups", new JsonArray());
		for(Group group : groups.values()){
			JsonObject grobj = new JsonObject();
			if(group.members.size() > 0){
				JsonArray array = new JsonArray();
				group.members.forEach(mem -> array.add(mem.toString()));
				grobj.add("members", array);
			}
			if(group.staff.size() > 0){
				JsonArray array = new JsonArray();
				group.staff.forEach(mem -> array.add(mem.toString()));
				grobj.add("staff", array);
			}
			grobj.addProperty("created", group.created);
			grobj.addProperty("creator", group.creator.toString());
			obj.get("groups").getAsJsonArray().add(grobj);
		}
		JsonUtil.write(file, obj);
	}
	
	public static class Group {
		
		public ArrayList<UUID> members = new ArrayList<>(), staff = new ArrayList<>();
		private UUID creator;
		public long created;
		public String id;
		
		public Group(String key){
			this.id = key;
		}
		
	}

	public static Group of(EntityPlayer player){
		UUID uuid = player.getGameProfile().getId();
		for(Group group : groups.values()){
			if(group.members.contains(uuid)) return group;
		}
		return null;
	}

}
