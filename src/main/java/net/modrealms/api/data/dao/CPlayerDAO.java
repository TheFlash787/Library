package net.modrealms.api.data.dao;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.CPlayer;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;
import java.util.UUID;

public class CPlayerDAO {

    private static final ModRealmsAPI conversify = ModRealmsAPI.getInstance();
    private static final Datastore datastore = conversify.getMongo().getDatastore();

    public Optional<CPlayer> getPlayer(UUID uuid){
        Query<CPlayer> playerQuery = datastore.createQuery(CPlayer.class).filter("_id",uuid);
        if(playerQuery.asList().size() >= 1){
            CPlayer updatedCPlayer = playerQuery.get();
            if(playerQuery.asList().size()>1){
            }
            return Optional.of(updatedCPlayer);
        }
        return Optional.empty();
    }

    public Optional<CPlayer> getPlayerByName(String name){
        Query<CPlayer> playerQuery = datastore.createQuery(CPlayer.class).filter("name",name);
        if(playerQuery.asList().size() >= 1){
            CPlayer updatedCPlayer = playerQuery.get();
            if(playerQuery.asList().size()>1){
            }
            return Optional.of(updatedCPlayer);
        }
        return Optional.empty();
    }

    public CPlayer newPlayer(ProxiedPlayer player){
        return new CPlayer(player);
    }

    public void updatePlayer(CPlayer CPlayer){
        ModRealmsAPI.getInstance().getLogger().info("Updating "+CPlayer.getName()+" to database and cache list");
        datastore.save(CPlayer);
    }

}
