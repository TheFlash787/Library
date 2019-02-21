package net.modrealms.objects;

public enum Role {
    RESIGNED(0),
    HELPER(1),
    MODERATOR(2),
    SUPERVISOR(3),
    MANAGER(4),
    ADMINISTRATOR(5),
    OWNER(6);

    private final Integer level;

    Role(Integer level){
        this.level = level;
    }

    public int getLevel(){
        return this.level;
    }
}
