package me.ryall.scavenger.economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.ryall.scavenger.Scavenger;

public class EconomyManager
{
    private EconomyInterface economy;

    public EconomyManager()
    {
        load();
    }
    
    public EconomyInterface getInterface()
    {
        return economy;
    }

    public void load()
    {
        if (economy == null)
        {
            String adapter = Scavenger.get().getConfigManager().getEconomyAdapter();

            if (!adapter.isEmpty())
            {
                Plugin plugin = Scavenger.get().getServer().getPluginManager().getPlugin(adapter);

                if (plugin != null)
                {
                    if (adapter.toLowerCase().equals("iconomy"))
                    {
                        if (plugin.getDescription().getVersion().startsWith("5"))
                            economy = new IConomyAdapter(plugin);
                        else 
                            economy = new IConomyFourAdapter(plugin);
                    }

                    if (economy != null)
                        Scavenger.get().logInfo("Economy adapter attached: " + economy.getName());
                }
            }
        }
    }
    
    public boolean charge(Player _player, double _price)
    {
        if (Scavenger.get().getConfigManager().isEconomyEnabled())
        {
            // Ignore invalid prices.
            if (_price > 0)
            {
                if (!economy.canAfford(_player.getName(), _price))
                {
                    return false;
                }

                if (!economy.subtract(_player.getName(), _price))
                {
                    Scavenger.get().getCommunicationManager().error(_player, "Failed to charge your account.");
                    return false;
                }
            }
        }

        return true;
    }
    
    public void refund(Player _player, double _price)
    {
        if (Scavenger.get().getConfigManager().isEconomyEnabled())
        {
            if (_price > 0)
            {
                if (!economy.add(_player.getName(), _price))
                {
                    Scavenger.get().getCommunicationManager().error(_player, "Failed to refund your account.");
                    return;
                }
            }
        }
    }
}
