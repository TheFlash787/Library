package net.modrealms.api.data.dao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.CPlayer;
import net.modrealms.objects.Party;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;
import java.util.UUID;

public class PartyDAO {

    private static final ModRealmsAPI conversify = ModRealmsAPI.getInstance();
    private static final Datastore datastore = conversify.getMongo().getDatastore();

    public Optional<Party> getParty(ObjectId id){
        Query<Party> playerQuery = datastore.createQuery(Party.class).filter("_id",id);
        if(playerQuery.asList().size() >= 1){
            Party updatedParty = playerQuery.get();
            return Optional.of(updatedParty);
        }
        return Optional.empty();
    }

    public Party newParty(UUID leader){
        return new Party(leader);
    }

    public void updateParty(Party Party){
        ModRealmsAPI.getInstance().getLogger().info("Updating "+Party.getId()+" to database and cache list");
        datastore.save(Party);
    }

    public void deleteParty(Party party,ProxiedPlayer player){
        if(! datastore.createQuery(Party.class).filter("_id",party.getId()).asList().isEmpty()){
            //delete from Creator
            CPlayer creator = ModRealmsAPI.getInstance().getDaoManager().getCPlayerDAO().getPlayer(party.getLeader()).get();
            creator.setPartyId(null);
            ModRealmsAPI.getInstance().getDaoManager().getCPlayerDAO().updatePlayer(creator);
            //Delete from datastore
            datastore.delete(party);

            if(player!=null){
                player.sendMessage(new ComponentBuilder("You have successfully left the party. As the leader has left the party, it has been deleted.").color(ChatColor.GREEN).create());
            }
        }else{
            ProxyServer.getInstance().getLogger().severe("User tried to delete a party but it did not exist.");

            if(player!=null){
                player.sendMessage(new ComponentBuilder("Sorry, but this party has already been deleted from the database.").color(ChatColor.RED).create());
            }
        }
    }

}
