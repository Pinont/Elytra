package com.github.pinont.custom.events.handlers;

import com.github.pinont.custom.events.PlayerIsGliding;
import com.github.pinont.custom.events.PlayerUseFirework;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.enums.PlayerInventorySlotType;
import com.github.pinont.singularitylib.api.utils.Common;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AutoRegister
public class PlayerHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Common.isAir(event.getItem()) && !(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!Objects.requireNonNull(event.getItem()).getType().equals(Material.FIREWORK_ROCKET)) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (player.getCooldown(item) > 0) return;
        boolean isOnElytra = Common.getItemInSlot(PlayerInventorySlotType.ARMOR_CHEST, player).getType().equals(Material.ELYTRA) && player.isGliding();
        PlayerUseFirework playerUseFirework = new PlayerUseFirework(player, item, isOnElytra);
        event.setCancelled(playerUseFirework.callEvent());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        playerIsGliding(event);
    }

    private void playerIsGliding(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.isGliding()) return;
        PlayerIsGliding playerIsGliding = new PlayerIsGliding(player, Common.getItemInSlot(PlayerInventorySlotType.ARMOR_CHEST, player), event.getFrom(), event.getTo());
        event.setCancelled(playerIsGliding.callEvent());
    }

}
