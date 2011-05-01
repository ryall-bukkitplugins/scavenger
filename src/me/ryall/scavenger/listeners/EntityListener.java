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
        /*if (event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getEntity();
        }*/
    }
    
    public void onEntityDeath(EntityDeathEvent event) 
    {
        if (event.getEntity() instanceof Player)
        {
            Scavenger.get().getCommunicationManager().message((Player)event.getEntity(), "Scavenging your dropped items."); 
            
            RestorationManager.collect((Player)event.getEntity(), event.getDrops());
        }
    }
}
