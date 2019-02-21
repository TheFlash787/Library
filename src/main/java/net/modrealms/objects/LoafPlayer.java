package net.modrealms.objects;

import lombok.Data;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Embedded;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Reference;

import java.util.*;
import java.util.stream.Collectors;

@Entity("loaf-players") @Data
public class LoafPlayer {
    @Id
    private ObjectId id;
    private UUID uuid;
    private String name;
    @Reference
    private BasePlayer basePlayer;
    private int balance;
    @Embedded
    private List<Booster> boosters;

    public LoafPlayer(){}

    public LoafPlayer(BasePlayer player){
        this.id = new ObjectId();
        this.name = player.getName();
        this.uuid = player.getUuid();
        this.basePlayer = player;
        this.balance = 0;
        this.boosters = new ArrayList<>();
    }

    public List<Booster> getBoosters(){
        if(this.boosters == null){
            this.boosters = new ArrayList<>();
        }
        return this.boosters;
    }

    public Booster getHighestBooster(){
        return this.boosters.stream().max(Comparator.comparing(Booster::getHours)).get();
    }

    public Optional<Booster> getActiveBooster(){
        for(Booster booster: this.getBoosters()){
            if(booster.getStartDate() != null){
                return Optional.of(booster);
            }
        }
        return Optional.empty();
    }

    public int getLoadHours(){
        int hours = this.getBasePlayer().getDonatorRole() != null ? this.getBasePlayer().getDonatorRole().getLoadHours() : 0;
        if(!this.getBoosters().isEmpty()){
            if(this.getHighestBooster().getStartDate() != null){
                hours = hours + this.getHighestBooster().getHours();
            }
        }
        return hours;
    }
}