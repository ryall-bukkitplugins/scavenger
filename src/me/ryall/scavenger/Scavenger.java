package me.ryall.scavenger;

import java.util.logging.Logger;

import me.ryall.scavenger.communication.CommunicationManager;
import me.ryall.scavenger.listeners.EntityListener;
import me.ryall.scavenger.listeners.PlayerListener;
import me.ryall.scavenger.listeners.ServerListener;
import me.ryall.scavenger.settings.PermissionManager;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Scavenger extends JavaPlugin
{
    public static String PLUGIN_NAME = "Scavenger";
    public static String LOG_HEADER = "[" + PLUGIN_NAME + "] ";
    private static Scavenger instance = null;
    
    private Logger log;
    private ServerListener serverListener;
    private EntityListener entityListener;
    private PlayerListener playerListener;
    //private ConfigManager configManager;
    private PermissionManager permissionManager;
    private CommunicationManager communicationManager;
    
    public static Scavenger get()
    {
        return instance;
    }
    
    public void onEnable()
    {
        instance = this;
        log = Logger.getLogger("Minecraft");
        
        serverListener = new ServerListener();
        entityListener = new EntityListener();
        playerListener = new PlayerListener();
        
        //configManager = new ConfigManager();
        permissionManager = new PermissionManager();
        communicationManager = new CommunicationManager();
        
        registerEvents();
        
        logInfo("Started");
    }

    public void onDisable()
    {
        logInfo("Stopped");
    }
    
    public void registerEvents()
    {
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, serverListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Lowest, this);
    }

    /*public ConfigManager getConfigManager()
    {
        return configManager;
    }*/
    
    public PermissionManager getPermissionManager()
    {
        return permissionManager;
    }
    
    public CommunicationManager getCommunicationManager()
    {
        return communicationManager;
    }
    
    public void logInfo(String _message)
    {
        log.info(LOG_HEADER + _message);
    }
    
    public void logError(String _message)
    {
        log.severe(LOG_HEADER + _message);
    }
}