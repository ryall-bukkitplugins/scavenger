package me.ryall.scavenger.system;

import java.util.HashMap;
import java.util.List;

import me.ryall.scavenger.Scavenger;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RestorationManager
{
    private static HashMap<String, Restoration> restorations = new HashMap<String, Restoration>();

    public static boolean hasRestoration(Player _player)
    {
        return restorations.containsKey(_player.getName());
    }

    public static void collect(Player _player, List<ItemStack> _drops)
    {
        if (hasRestoration(_player))
        {
            Scavenger.get().getCommunicationManager().error(_player, "Restoration already exists, ignoring.");
            return;
        }

        Restoration restoration = new Restoration();

        restoration.enabled = false;
        restoration.inventory = _player.getInventory().getContents();
        restoration.armour = _player.getInventory().getArmorContents();
        
        restorations.put(_player.getName(), restoration);

        _drops.clear();
        
        if (Scavenger.get().getConfigManager().shouldNotify())
            Scavenger.get().getCommunicationManager().message(_player, "Gathered your dropped items.");
    }

    public static void enable(Player _player)
    {
        if (hasRestoration(_player))
        {
            Restoration restoration = restorations.get(_player.getName());
            restoration.enabled = true;
        }
    }

    public static void restore(Player _player)
    {
        if (hasRestoration(_player))
        {
            Restoration restoration = restorations.get(_player.getName());
            
            if (restoration.enabled)
            {
                _player.getInventory().clear();
                
                _player.getInventory().setContents(restoration.inventory);
                _player.getInventory().setArmorContents(restoration.armour);

                if (Scavenger.get().getConfigManager().shouldNotify())
                    Scavenger.get().getCommunicationManager().message(_player, "Your inventory has been restored.");

                restorations.remove(_player.getName());
            }
        }
    }
}
