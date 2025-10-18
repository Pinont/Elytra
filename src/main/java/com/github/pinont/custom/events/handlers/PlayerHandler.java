package com.github.pinont.custom.events.handlers;

import com.github.pinont.custom.events.PlayerUseFirework;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.enums.PlayerInventorySlotType;
import com.github.pinont.singularitylib.api.utils.Common;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

@AutoRegister
public class PlayerHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Common.isAir(event.getItem()) && !(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (Objects.requireNonNull(event.getItem()).getType().equals(Material.FIREWORK_ROCKET)) return;
        PlayerUseFirework playerUseFirework = new PlayerUseFirework(event.getPlayer(), event.getItem(), Common.getItemInSlot(PlayerInventorySlotType.ARMOR_CHEST, event.getPlayer()).getType().equals(Material.ELYTRA));
        event.setCancelled(playerUseFirework.callEvent());
    }

}
