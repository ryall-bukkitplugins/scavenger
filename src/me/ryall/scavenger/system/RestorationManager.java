package me.ryall.scavenger.system;

import java.util.ArrayList;
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

                // First restore armour, if any exists (armour is stored last in the list).
                int numDrops = restoration.drops.size();
                
                for (int i = 1; i <= numDrops; i++)
                {
                    ItemStack stack = restoration.drops.get(numDrops - i);
                    
                    // Continue until we have restored 4 armour slots or found a non-armour item.
                    if (i < 5 && stack.getTypeId() >= ARMOUR_START_INDEX && stack.getTypeId() <= ARMOUR_END_INDEX)
                    {
                        switch (stack.getTypeId() % ARMOUR_MODULUS)
                        {
                        case ARMOUR_HEAD:
                            _player.getInventory().setHelmet(stack); 
                            break;
                        case ARMOUR_CHEST:
                            _player.getInventory().setChestplate(stack); 
                            break;
                        case ARMOUR_LEGS:
                            _player.getInventory().setLeggings(stack); 
                            break;
                        case ARMOUR_FEET:
                            _player.getInventory().setBoots(stack); 
                            break;
                        }
                        
                        restoration.drops.remove(numDrops - i);
                    }
                    else
                        break;
                }
                
                // Second restore the player's remaining inventory.
                for (ItemStack stack : restoration.drops)
                {
                    int slot = _player.getInventory().firstEmpty();
                    
                    if (slot != -1)
                        _player.getInventory().setItem(_player.getInventory().firstEmpty(), stack);
                }

                Scavenger.get().getCommunicationManager().message(_player, "Your inventory has been restored.");

                restorations.remove(_player.getName());
            }
        }
    }
}
