package me.ryall.scavenger.listeners;

import me.ryall.scavenger.system.RestorationManager;

import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener extends org.bukkit.event.player.PlayerListener
{
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        RestorationManager.enable(event.getPlayer());
    }

    public void onPlayerMove(PlayerMoveEvent event)
    {
        RestorationManager.restore(event.getPlayer());
    }
}
