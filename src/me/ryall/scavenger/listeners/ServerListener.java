package me.ryall.scavenger.listeners;

import me.ryall.scavenger.Scavenger;

import org.bukkit.event.server.PluginEnableEvent;

public class ServerListener extends org.bukkit.event.server.ServerListener
{
    public void onPluginEnable(PluginEnableEvent _event)
    {
        Scavenger.get().getPermissionManager().load();
    }
}
