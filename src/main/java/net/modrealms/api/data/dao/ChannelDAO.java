package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.Channel;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;

public class ChannelDAO {

    private static final ModRealmsAPI conversify = ModRealmsAPI.getInstance();
    private static final Datastore datastore = conversify.getMongo().getDatastore();

    public Optional<Channel> getChannel(String name){
        Query<Channel> playerQuery = datastore.createQuery(Channel.class).filter("_id",name);
        if(playerQuery.asList().size() >= 1){
            Channel updatedChannel = playerQuery.get();
            return Optional.of(updatedChannel);
        }
        return Optional.empty();
    }

    public Channel newChannel(String name){
        return new Channel(name);
    }

    public void updateChannel(Channel Channel){
        System.out.println("Updating "+Channel.getId()+" to database and cache list");
        datastore.save(Channel);
    }

}
