package me.ryall.scavenger;

import java.util.logging.Logger;

import me.ryall.scavenger.communication.CommunicationManager;
import me.ryall.scavenger.economy.EconomyManager;
import me.ryall.scavenger.listeners.EntityListener;
import me.ryall.scavenger.listeners.PlayerListener;
import me.ryall.scavenger.listeners.ServerListener;
import me.ryall.scavenger.settings.ConfigManager;
import me.ryall.scavenger.settings.PermissionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
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
    private ConfigManager configManager;
    private PermissionManager permissionManager;
    private CommunicationManager communicationManager;
    private EconomyManager economyManager;

    public static Scavenger get()
    {
        return instance;
    }

    public void onEnable()
    {
        instance = this;
        log = Logger.getLogger("Minecraft");

        Plugin plugin = getServer().getPluginManager().getPlugin(PLUGIN_NAME);
        logInfo("Starting: v" + plugin.getDescription().getVersion());
        
        serverListener = new ServerListener();
        entityListener = new EntityListener();
        playerListener = new PlayerListener();

        configManager = new ConfigManager();
        permissionManager = new PermissionManager();
        communicationManager = new CommunicationManager();
        economyManager = new EconomyManager();

        registerEvents();
    }

    public void onDisable()
    {
        logInfo("Stopping");
    }

    public void registerEvents()
    {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvent(Event.Type.PLUGIN_ENABLE, serverListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Lowest, this);
    }
    
    public boolean onCommand(CommandSender _sender, Command _command, String _label, String[] _args)
    {
        if (_label.equals("scavenger") || _label.equals("scv"))
        {
            /*if (_sender instanceof Player)
            {
                Player player = (Player)_sender;

                if (_args.length == 1)
                {
                    if (_args[0].equals("restore"))
                    {
                        RestorationManager.enable(player);
                        RestorationManager.restore(player);
                        
                        return true;
                    }
                }
            } else
                logError("Commands can only be executed in-game.");*/

            return true;
        }

        return false;
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }
    
    public PermissionManager getPermissionManager()
    {
        return permissionManager;
    }

    public CommunicationManager getCommunicationManager()
    {
        return communicationManager;
    }
    
    public EconomyManager getEconomyManager()
    {
        return economyManager;
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