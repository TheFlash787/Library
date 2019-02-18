package net.modrealms.api.data.dao;

import lombok.Getter;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.BasePlayer;
import net.modrealms.objects.LoafPlayer;
import net.modrealms.objects.Server;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class LoafPlayerDAO {
    @Getter
    private HashMap<UUID, LoafPlayer> cachedPlayers = new HashMap<>();
    private final ModRealmsAPI api = net.modrealms.api.ModRealmsAPI.getInstance();
    private final Datastore datastore = api.getMongo().getDatastore();

    public Optional<LoafPlayer> getPlayer(UUID uuid) {
        if(this.cachedPlayers.containsKey(uuid)){
            return Optional.of(this.cachedPlayers.get(uuid));
        }
        else{
            Query<LoafPlayer> playerQuery = datastore.createQuery(LoafPlayer.class).filter("uuid", uuid);
            if(playerQuery.asList().size() >= 1) {
                LoafPlayer updatedLoafPlayer = playerQuery.get();
                if(playerQuery.asList().size() > 1){
                    ModRealmsAPI.getInstance().getLogger().info(uuid+" has more than one player object!");
                }
                return Optional.of(updatedLoafPlayer);
            }
            return Optional.empty();
        }
    }

    public Optional<LoafPlayer> getPlayerById(ObjectId objectId) {
        Query<LoafPlayer> playerQuery = datastore.createQuery(LoafPlayer.class).filter("_id", objectId);
        if(playerQuery.asList().size() == 1) {
            LoafPlayer updatedLoafPlayer = playerQuery.get();
            return Optional.of(updatedLoafPlayer);
        }
        return Optional.empty();
    }

    public Optional<LoafPlayer> getPlayerByName(String name) {

        Query<LoafPlayer> playerQuery = datastore.createQuery(LoafPlayer.class).field("username").equalIgnoreCase(name);
        if(playerQuery.asList().size() == 1) {
            LoafPlayer updatedLoafPlayer = playerQuery.get();
            return Optional.of(updatedLoafPlayer);
        }
        return Optional.empty();
    }

    public Optional<LoafPlayer> updateName(org.spongepowered.api.entity.living.player.Player player,LoafPlayer mrPlayer){
        //only returns player if name has been updated
        if(!mrPlayer.getName().equals(player.getName())){
            mrPlayer.setName(player.getName());
            return Optional.of(mrPlayer);
        }
        return Optional.empty();
    }

    public LoafPlayer newPlayer(BasePlayer player) {
        return new LoafPlayer(player);
    }

    public void updatePlayer(LoafPlayer basePlayer) {
        ModRealmsAPI.getInstance().getLogger().info("Updating "+ basePlayer.getName()+" to the database and cache list.");
        datastore.save(basePlayer);
    }
}
