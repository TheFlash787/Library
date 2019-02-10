package net.modrealms.api.data.dao;

import lombok.Getter;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.BasePlayer;
import net.modrealms.objects.Server;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class BasePlayerDAO {
    @Getter
    private HashMap<UUID, BasePlayer> cachedPlayers = new HashMap<>();
    private final ModRealmsAPI api = net.modrealms.api.ModRealmsAPI.getInstance();
    private final Logger logger = ModRealmsAPI.getInstance().getLogger();
    private final Datastore datastore = api.getMongo().getDatastore();

    public Optional<BasePlayer> getPlayer(UUID uuid) {
        if(this.cachedPlayers.containsKey(uuid)){
            return Optional.of(this.cachedPlayers.get(uuid));
        }
        else{
            Query<BasePlayer> playerQuery = datastore.createQuery(BasePlayer.class).filter("uuid", uuid);
            if(playerQuery.asList().size() >= 1) {
                BasePlayer updatedBasePlayer = playerQuery.get();
                if(playerQuery.asList().size() > 1){
                    logger.error(uuid+" has more than one player object!");
                }
                return Optional.of(updatedBasePlayer);
            }
            return Optional.empty();
        }
    }

    public Optional<BasePlayer> getPlayerById(ObjectId objectId) {
        Query<BasePlayer> playerQuery = datastore.createQuery(BasePlayer.class).filter("_id", objectId);
        if(playerQuery.asList().size() == 1) {
            BasePlayer updatedBasePlayer = playerQuery.get();
            return Optional.of(updatedBasePlayer);
        }
        return Optional.empty();
    }

    public Optional<BasePlayer> getPlayerByDiscordId(String userId) {
        Query<BasePlayer> playerQuery = datastore.createQuery(BasePlayer.class).filter("discord_id", userId);
        if(playerQuery.asList().size() == 1) {
            BasePlayer updatedBasePlayer = playerQuery.get();
            return Optional.of(updatedBasePlayer);
        }
        return Optional.empty();
    }

    public Optional<BasePlayer> getPlayerByName(String name) {

        Query<BasePlayer> playerQuery = datastore.createQuery(BasePlayer.class).field("username").equalIgnoreCase(name);
        if(playerQuery.asList().size() == 1) {
            BasePlayer updatedBasePlayer = playerQuery.get();
            return Optional.of(updatedBasePlayer);
        }
        return Optional.empty();
    }

    public Optional<BasePlayer> updateName(org.spongepowered.api.entity.living.player.Player player,BasePlayer mrPlayer){
        //only returns player if name has been updated
        if(!mrPlayer.getName().equals(player.getName())){
            mrPlayer.setName(player.getName());
            return Optional.of(mrPlayer);
        }
        return Optional.empty();
    }

    public Optional<BasePlayer> updateServer(String server, BasePlayer mrPlayer){
        //only returns player if name has been updated
        if(!mrPlayer.getLastServer().equals(server)){
            mrPlayer.setLastServer(server);
            return Optional.of(mrPlayer);
        }
        return Optional.empty();
    }

    public BasePlayer newPlayer(Player player, Server server) {
        return new BasePlayer(player, server);
    }

    public void updatePlayer(BasePlayer basePlayer) {
        ModRealmsAPI.getInstance().getLogger().info("Updating "+ basePlayer.getName()+" to the database and cache list.");
        this.cachedPlayers.put(basePlayer.getUuid(), basePlayer);
        datastore.save(basePlayer);
    }
}
