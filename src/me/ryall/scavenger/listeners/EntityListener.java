package me.ryall.scavenger.listeners;

import me.ryall.scavenger.Scavenger;
import me.ryall.scavenger.system.RestorationManager;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener
{
    public void onEntityDamage(EntityDamageEvent event)
    {
    }

    public void onEntityDeath(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getEntity();
            
            if (Scavenger.get().getPermissionManager().hasScavengePermission(player))
                RestorationManager.collect((Player)event.getEntity(), event.getDrops());
        }
    }
}
