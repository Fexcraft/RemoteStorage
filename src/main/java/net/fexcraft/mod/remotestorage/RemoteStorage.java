package net.fexcraft.mod.remotestorage;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RemoteStorage.MODID, name = RemoteStorage.NAME, version = RemoteStorage.VERSION)
public class RemoteStorage{
	
    public static final String MODID = "remotestorage";
    public static final String NAME = "Remote Storage";
    public static final String VERSION = "1.0";
    
    public static String SERVER, TOKEN;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        Configuration config = new Configuration();
        config.load();
        SERVER = config.getString("server", "general", "http://fishy.somee.com/", "Adress of storage server.");
        TOKEN = config.getString("token", "general", "00000", "Token of this minecraft server to interact with the storage server.");
        config.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        //
    }
    
    public Logger getLogger(){
    	return logger;
    }
    
}
