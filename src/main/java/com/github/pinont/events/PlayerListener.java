package com.github.pinont.events;

import com.github.pinont.custom.events.PlayerUseFirework;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AutoRegister
public class PlayerListener implements Listener {

    @EventHandler
    public void useFireworkOnElytra(PlayerUseFirework event) {
        if (!event.isOnElytra()) return;

        event.getPlayer().setCooldown(event.getItem(), 500);
    }

}
