package net.modrealms.objects;

import lombok.Getter;

public enum DonatorRole {
    STAR(1,1),
    METEORITE(2,4),
    ASTEROID(3,8),
    COMET(4,24),
    COSMOS(5,72);

    @Getter
    private final int level;
    @Getter
    private final int loadHours;

    DonatorRole(int level, int loadHours){
        this.level = level;
        this.loadHours = loadHours;
    }
}
