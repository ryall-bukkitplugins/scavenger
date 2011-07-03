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
    
    public boolean isEconomyEnabled()
    {
        return config.getBoolean("Economy.Enabled", true);
    }
    
    public String getEconomyAdapter()
    {
        return config.getString("Economy.Adapter", "iConomy");
    }
    
    public double getEconomyRestoreCost()
    {
        return config.getDouble("Economy.RestoreCost", 10.0);
    }
}
