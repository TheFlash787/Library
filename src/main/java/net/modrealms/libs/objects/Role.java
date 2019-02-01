package net.modrealms.libs.objects;

public enum Role {
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
