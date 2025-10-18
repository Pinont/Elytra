package com.github.pinont.custom.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerUseFirework extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final ItemStack item;
    private final boolean onElytra;
    private boolean isCancelled = false;

    public PlayerUseFirework(final Player player, final ItemStack item, boolean onElytra) {
        this.player = player;
        this.item = item;
        this.onElytra = onElytra;
    }

    public boolean isOnElytra() {
        return onElytra;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public boolean callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        return this.isCancelled();
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
