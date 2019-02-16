package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.Announcement;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;

public class AnnouncementDAO {

    private static final ModRealmsAPI conversify = ModRealmsAPI.getInstance();
    private static final Datastore datastore = conversify.getMongo().getDatastore();

    public Optional<Announcement> getAnnouncement(String name){
        Query<Announcement> playerQuery = datastore.createQuery(Announcement.class).filter("_id",name);
        if(playerQuery.asList().size() >= 1){
            Announcement updatedAnnouncement = playerQuery.get();
            return Optional.of(updatedAnnouncement);
        }
        return Optional.empty();
    }

    public Announcement newAnnouncement(String message){
        return new Announcement(message);
    }

    public void updateAnnouncement(Announcement Announcement){
        System.out.println("Updating "+Announcement.getId()+" to database and cache list");
        datastore.save(Announcement);
    }

}
