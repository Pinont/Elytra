package com.github.pinont.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public interface Box {

    String getWorldName();
    int getXMin();
    int getYMin();
    int getZMin();
    int getXMax();
    int getYMax();
    int getZMax();

    default World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }

    default Location getCornerMinLocation() {
        return new Location(getWorld(), getXMin(), getYMin(), getZMin());
    }

    default Location getCornerMaxLocation() {
        return new Location(getWorld(), getXMax(), getYMax(), getZMax());
    }
}
