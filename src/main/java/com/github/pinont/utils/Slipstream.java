package com.github.pinont.utils;

import org.bukkit.entity.Player;

public interface Slipstream extends Box {
    Player getOwner();

    default Box getBox() {
        return this;
    }
}
