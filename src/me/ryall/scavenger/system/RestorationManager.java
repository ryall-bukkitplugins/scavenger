package me.ryall.scavenger.system;

import java.util.ArrayList;
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
            Scavenger.get().getCommunicationManager().error(_player, "Found an existing set of items for you when trying to save your current items.");
            restorations.remove(_player.getName());
        }

        Restoration restoration = new Restoration();

        restoration.enabled = false;
        restoration.drops = new ArrayList<ItemStack>(_drops);

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

                for (ItemStack item : restoration.drops)
                {
                    _player.getInventory().setItem(_player.getInventory().firstEmpty(), item);
                }

                Scavenger.get().getCommunicationManager().message(_player, "Your items have been restored.");

                restorations.remove(_player.getName());
            }
        }
    }
}
