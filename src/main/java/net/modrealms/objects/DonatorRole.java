package net.modrealms.objects;

public enum DonatorRole {
    STAR(1),
    METEORITE(2),
    ASTEROID(3),
    COMET(4),
    COSMOS(5);

    private final int level;

    DonatorRole(int level){
        this.level = level;
    }

    public int getLevel(){
        return this.level;
    }
}
