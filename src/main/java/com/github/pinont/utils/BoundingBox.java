package com.github.pinont.utils;

import com.github.pinont.singularitylib.api.utils.Console;
import org.bukkit.Location;

import java.util.ArrayList;

import static com.github.pinont.utils.Utils.convertLocationsToBox;

public class BoundingBox {

    private final ArrayList<Box> box;
    private final String name;

    public BoundingBox(String name) {
        this.name = name;
        box = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Box> getBox() {
        return box;
    }

    public void add(Location loc1, Location loc2) {
        Box corner = convertLocationsToBox(loc1, loc2);
        if (corner == null) return;
        box.add(corner);
    }

    public void remove(Location loc1, Location loc2) {
        Box corner = convertLocationsToBox(loc1, loc2);
        if (corner == null) return;
        box.removeIf(c -> c.getWorldName().equals(corner.getWorldName()) &&
                c.getXMin() == corner.getXMin() &&
                c.getYMin() == corner.getYMin() &&
                c.getZMin() == corner.getZMin() &&
                c.getXMax() == corner.getXMax() &&
                c.getYMax() == corner.getYMax() &&
                c.getZMax() == corner.getZMax());
    }

    public void add(Box corner) {
        this.box.add(corner);
    }

    public void remove(Box corner) {
        this.box.remove(corner);
    }

    public void clear() {
        this.box.clear();
    }

    public boolean contains(Location loc) {
        loc.set(Math.floor(loc.getX()), Math.floor(loc.getY()), Math.floor(loc.getZ()));
        for (Box corner : box) {
            if (loc.getWorld() != null && corner.getWorld() != null && loc.getWorld().equals(corner.getWorld())) {
                if (loc.getX() >= corner.getXMin() && loc.getX() <= corner.getXMax() &&
                    loc.getY() >= corner.getYMin() && loc.getY() <= corner.getYMax() &&
                    loc.getZ() >= corner.getZMin() && loc.getZ() <= corner.getZMax()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean overlaps(BoundingBox other) {
        for (Box corner : other.box) {
            if (this.contains(corner.getCornerMinLocation()) || this.contains(corner.getCornerMaxLocation())) {
                return true;
            }
        }
        return false;
    }
}
