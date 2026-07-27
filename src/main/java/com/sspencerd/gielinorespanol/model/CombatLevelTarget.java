package com.sspencerd.gielinorespanol.model;

public class CombatLevelTarget {

    private final String name;
    private final String level;
    private final boolean hasCombatLevel;


    public CombatLevelTarget(String name, String level, boolean hasCombatLevel) {
        this.name = name;
        this.level = level;
        this.hasCombatLevel = hasCombatLevel;
    }
    public String getName() {
        return name;
    }
    public String getLevel() {
        return level;
    }
    public boolean hasCombatLevel() {
        return hasCombatLevel;
    }
}
