package com.github.pinont.events;

import com.github.pinont.custom.events.PlayerIsGliding;
import com.github.pinont.custom.events.PlayerUseFirework;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.utils.Common;
import com.github.pinont.utils.SlipstreamBox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@AutoRegister
public class PlayerListener implements Listener {

    SlipstreamBox slipstream = new SlipstreamBox("slipstream");

    @EventHandler
    public void useFireworkOnElytra(PlayerUseFirework event) {
        if (!event.isOnElytra()) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem().clone();
        Bukkit.getScheduler().runTaskLater(Common.plugin, () -> {
            if (player == null) return;
            player.setCooldown(item, 50);
        }, 1);
    }

    @EventHandler
    public void onPlayerGliding(PlayerIsGliding event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        double speed = event.getGlidingSpeed();
        if (slipstream.contains(location)) { // player is in other player slipstream
            player.sendActionBar(new Common().colorize("Slipstream (By " + slipstream.getOwnerAt(location).getName() + "): " + speed));
            if (!slipstream.getOwnerAt(location).equals(player)) player.setVelocity(player.getLocation().getDirection().multiply(1.4));
            return;
        }

        if (speed > 1) {
            generateSlipstream(player, speed, location, 3);
        }
    }

    private void generateSlipstream(Player owner, double speed, Location origin, int size) {
        if (size <= 2) return;

        int key = (int) Math.max(1, Math.min(3, Math.floor(speed)));
        int duration = key;
        int delay = Math.max(50 - (duration * 10), 40);

        double finalSpeed = speed - 1;
        size = size + 1;
        if (finalSpeed <= 0) {
            size = 2;
        }
        int finalSize = size;
        Location corner1 = origin.clone().add(Math.floor(size / 2f), Math.floor(size / 2f), Math.floor(size / 2f));
        Location corner2 = origin.clone().add(-1 * Math.floor(size / 2f), -1 * Math.floor(size / 2f), -1 * Math.floor(size / 2f));

        Bukkit.getScheduler().runTaskLater(Common.plugin, () -> {
            slipstream.add(corner1, corner2, owner);
            Bukkit.getScheduler().runTaskLater(Common.plugin, () -> {
                generateSlipstream(owner, finalSpeed, origin, finalSize);
                slipstream.remove(corner1, corner2, owner);
            }, delay);
        }, 3);
    }
}
