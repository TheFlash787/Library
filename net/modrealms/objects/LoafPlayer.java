package net.modrealms.objects;

import lombok.Data;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Reference;

import java.util.UUID;

@Entity("loaf-players") @Data
public class LoafPlayer {
    @Id
    private ObjectId id;
    private UUID uuid;
    private String name;
    @Reference
    private BasePlayer basePlayer;
    private int balance;

    public LoafPlayer(){}

    public LoafPlayer(BasePlayer player){
        this.id = new ObjectId();
        this.name = player.getName();
        this.uuid = player.getUuid();
        this.basePlayer = player;
        this.balance = 0;
    }
}