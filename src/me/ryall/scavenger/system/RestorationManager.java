package me.ryall.scavenger.system;

import java.util.HashMap;
import java.util.List;

import me.ryall.scavenger.Scavenger;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RestorationManager
{
    public static final int ARMOUR_START_INDEX = 298;
    public static final int ARMOUR_END_INDEX = 317;
    
    public static final int ARMOUR_MODULUS = 4;
    public static final int ARMOUR_HEAD = 2;
    public static final int ARMOUR_CHEST = 3;
    public static final int ARMOUR_LEGS = 0;
    public static final int ARMOUR_FEET = 1;
    
    private static HashMap<String, Restoration> restorations = new HashMap<String, Restoration>();

    public static boolean hasRestoration(Player _player)
    {
        return restorations.containsKey(_player.getName());
    }

    public static void collect(Player _player, List<ItemStack> _drops)
    {
        if (hasRestoration(_player))
        {
            Scavenger.get().getCommunicationManager().error(_player, "Found an existing set of items for you when trying to save your current items.");
            restorations.remove(_player.getName());
        }

        Restoration restoration = new Restoration();

        restoration.enabled = false;
        restoration.inventory = _player.getInventory().getContents();
        restoration.armour = _player.getInventory().getArmorContents();
        
        restorations.put(_player.getName(), restoration);

        _drops.clear();
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

                Scavenger.get().getCommunicationManager().message(_player, "Your inventory has been restored.");

                restorations.remove(_player.getName());
            }
        }
    }
}
