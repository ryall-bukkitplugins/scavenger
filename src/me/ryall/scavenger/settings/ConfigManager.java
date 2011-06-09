package me.ryall.scavenger.settings;

import org.bukkit.util.config.Configuration;

import me.ryall.scavenger.Scavenger;

public class ConfigManager
{
    private Configuration config;

    public ConfigManager()
    {
        config = Scavenger.get().getConfiguration();
        config.load();
    }
    
    public boolean shouldNotify()
    {
        return config.getBoolean("Global.Notify", true);
    }
}
