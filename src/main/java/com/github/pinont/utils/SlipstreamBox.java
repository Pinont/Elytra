package com.github.pinont.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.github.pinont.utils.Utils.convertLocationsToBox;
import static com.github.pinont.utils.Utils.convertLocationsToSlipstream;

public class SlipstreamBox extends BoundingBox {
    private ArrayList<Slipstream> slipstreams = new ArrayList<>();

    public SlipstreamBox(String name) {
        super(name);
    }

    public ArrayList<Slipstream> getSlipstreams() {
        return slipstreams;
    }

    public Player getOwnerAt(Location location) {
        for (Slipstream slipstream : slipstreams) {
            if (super.contains(location)) {
                return slipstream.getOwner();
            }
        }
        return null;
    }

    public void add(Location location1, Location location2, Player player) {
        super.add(location1, location2);
        slipstreams.add(convertLocationsToSlipstream(location1, location2, player));
    }

    public void remove(Location location1, Location location2, Player player) {
        Box box = convertLocationsToBox(location1, location2);
        if (box == null) return;
        super.remove(location1, location2);
        slipstreams.removeIf(slipstream -> slipstream.getWorldName().equals(box.getWorldName()) &&
                slipstream.getXMin() == box.getXMin() &&
                slipstream.getYMin() == box.getYMin() &&
                slipstream.getZMin() == box.getZMin() &&
                slipstream.getXMax() == box.getXMax() &&
                slipstream.getYMax() == box.getYMax() &&
                slipstream.getZMax() == box.getZMax() &&
                slipstream.getOwner().equals(player));
    }
}
