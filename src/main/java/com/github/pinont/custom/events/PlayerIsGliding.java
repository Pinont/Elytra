package com.github.pinont.custom.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerIsGliding extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private final Player player;
    private final ItemStack elytraItem;
    private final Location from;
    private final Location to;

    public PlayerIsGliding(Player player, ItemStack elytraItem, Location from, Location to) {
        this.player = player;
        this.elytraItem = elytraItem;
        this.from = from;
        this.to = to;
    }

    public double getGlidingSpeed() {
        if (from == null || to == null) return 0.0;
        if (from.getWorld() == null || to.getWorld() == null) return 0.0;
        if (!from.getWorld().equals(to.getWorld())) return 0.0;
        return to.distanceSquared(from);
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getElytraItem() {
        return elytraItem;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
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
        if (b) {
            player.setGliding(false);
        }
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
