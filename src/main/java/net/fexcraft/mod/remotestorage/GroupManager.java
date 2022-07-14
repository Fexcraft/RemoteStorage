package net.fexcraft.mod.remotestorage;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.fexcraft.lib.common.json.JsonUtil;
import net.fexcraft.lib.common.math.Time;
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
			group.manager = UUID.fromString(grobj.get("manager").getAsString());
			group.name = grobj.get("name").getAsString();
			group.key = grobj.get("key").getAsString();
			groups.put(entry.getKey(), group);
		});
	}

	public static void save(){
		JsonObject obj = new JsonObject();
		obj.addProperty("format", 1);
		obj.add("groups", new JsonObject());
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
			grobj.addProperty("manager", group.manager.toString());
			grobj.addProperty("name", group.name);
			obj.get("groups").getAsJsonObject().add(group.key, grobj);
		}
		JsonUtil.write(file, obj);
	}
	
	public static class Group {
		
		public ArrayList<UUID> members = new ArrayList<>(), staff = new ArrayList<>(), invites = new ArrayList<>();
		public UUID creator, manager;
		public long created;
		public String name, key;
		
		public Group(String key){
			this.key = key;
		}
		
		public Group(String name, UUID creator){
			this.key = genKey();
			this.name = name;
			this.creator = creator;
			this.created = Time.getDate();
			this.manager = creator;
		}
		
	}

	public static Group of(EntityPlayer player){
		UUID uuid = player.getGameProfile().getId();
		for(Group group : groups.values()){
			if(group.members.contains(uuid)) return group;
		}
		return null;
	}

	public static Group get(String key){
		for(Group group : groups.values()){
			if(group.key.equals(key)) return group;
		}
		return null;
	}

	public static String genKey(){
		String str = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		while(get(str) != null) str = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		return str;
	}

	public static void register(String name, EntityPlayer player){
		UUID uuid = player.getGameProfile().getId();
		Group group = new Group(name, uuid);
		groups.put(name, group);
		group.members.add(uuid);
		group.staff.add(uuid);
		save();
	}

	public static void remove(Group group){
		groups.remove(group.key);
		save();
	}

}
