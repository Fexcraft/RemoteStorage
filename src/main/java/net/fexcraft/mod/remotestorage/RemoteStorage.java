package net.fexcraft.mod.remotestorage;

import org.apache.logging.log4j.Logger;

import net.fexcraft.lib.mc.registry.FCLRegistry;
import net.fexcraft.mod.remotestorage.block.TransferTE;
import net.fexcraft.mod.remotestorage.gui.GuiHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = RemoteStorage.MODID, name = RemoteStorage.NAME, version = RemoteStorage.VERSION, dependencies = "required-after:fcl;")
public class RemoteStorage {
	
    public static final String MODID = "remotestorage";
    public static final String NAME = "Remote Storage";
    public static final String VERSION = "1.0";
    
    @Mod.Instance(MODID)
    public static RemoteStorage INSTANCE;
    public static String SERVER, TOKEN;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        SERVER = config.getString("server", "general", "http://fishy.somee.com/", "Adress of storage server.");
        TOKEN = config.getString("token", "general", "00000", "Token of this minecraft server to interact with the storage server.");
        config.save();
        
        FCLRegistry.newAutoRegistry(MODID); 
        GameRegistry.registerTileEntity(TransferTE.class, new ResourceLocation(MODID, "transfer"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
    
    public Logger getLogger(){
    	return logger;
    }
    
}
