package com.github.pinont.utils;

import com.github.pinont.singularitylib.api.utils.Console;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {
    public static Box convertLocationsToBox(Location loc1, Location loc2) {
        if (loc1.getWorld() == null || loc2.getWorld() == null || !loc1.getWorld().equals(loc2.getWorld())) {
            Console.logError("Cannot create Box from Locations in different worlds or null worlds.");
            return null;
        }

        return new Box() {
            @Override
            public String getWorldName() {
                return loc1.getWorld().getName();
            }

            @Override
            public int getXMin() {
                return Math.min(loc1.getBlockX(), loc2.getBlockX());
            }

            @Override
            public int getYMin() {
                return Math.min(loc1.getBlockY(), loc2.getBlockY());
            }

            @Override
            public int getZMin() {
                return Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            }

            @Override
            public int getXMax() {
                return Math.max(loc1.getBlockX(), loc2.getBlockX());
            }

            @Override
            public int getYMax() {
                return Math.max(loc1.getBlockY(), loc2.getBlockY());
            }

            @Override
            public int getZMax() {
                return Math.max(loc1.getBlockZ(), loc2.getBlockZ());
            }
        };
    }

    public static Slipstream convertLocationsToSlipstream(Location loc1, Location loc2, Player owner) {
        Box box = convertLocationsToBox(loc1, loc2);
        if (box == null) {
            return null;
        }
        return new Slipstream() {
            @Override
            public Player getOwner() {
                return owner;
            }

            @Override
            public String getWorldName() {
                return box.getWorldName();
            }

            @Override
            public int getXMin() {
                return box.getXMin();
            }

            @Override
            public int getYMin() {
                return box.getYMin();
            }

            @Override
            public int getZMin() {
                return box.getZMin();
            }

            @Override
            public int getXMax() {
                return box.getXMax();
            }

            @Override
            public int getYMax() {
                return box.getYMax();
            }

            @Override
            public int getZMax() {
                return box.getZMax();
            }
        };
    }
}
