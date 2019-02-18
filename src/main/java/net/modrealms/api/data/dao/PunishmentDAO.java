package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.Ban;
import net.modrealms.objects.Kick;
import net.modrealms.objects.Mute;
import net.modrealms.objects.Warning;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import javax.annotation.Nullable;
import java.util.Optional;

public class PunishmentDAO {
    private static final ModRealmsAPI conversify = ModRealmsAPI.getInstance();
    private static final Datastore datastore = conversify.getMongo().getDatastore();

    public Optional<Ban> getBanById(ObjectId id){
        Query<Ban> banQuery = datastore.createQuery(Ban.class).filter("_id",id);
        if(banQuery.asList().size() >= 1){
            Ban updatedBan = banQuery.get();
            return Optional.of(updatedBan);
        }
        return Optional.empty();
    }

    public Ban newBan(String reason,String staff,String player,@Nullable String duration,@Nullable String ip){
        return new Ban(reason, staff, player, duration, ip);
    }

    public void updateBan(Ban ban){
        ModRealmsAPI.getInstance().getLogger().info("Updating ban to database and cache list");
        datastore.save(ban);
    }

    public Optional<Kick> getKickById(ObjectId id){
        Query<Kick> kickQuery = datastore.createQuery(Kick.class).filter("_id",id);
        if(kickQuery.asList().size() >= 1){
            Kick updatedKick = kickQuery.get();
            return Optional.of(updatedKick);
        }
        return Optional.empty();
    }

    public Kick newKick(String reason,String staff,String player){
        return new Kick(reason, staff, player);
    }

    public void updateKick(Kick kick){
        ModRealmsAPI.getInstance().getLogger().info("Updating ban to database and cache list");
        datastore.save(kick);
    }

    public Optional<Warning> getWarningById(ObjectId id){
        Query<Warning> warningQuery = datastore.createQuery(Warning.class).filter("_id",id);
        if(warningQuery.asList().size() >= 1){
            Warning updatedWarning = warningQuery.get();
            return Optional.of(updatedWarning);
        }
        return Optional.empty();
    }

    public Warning newWarning(String reason, String staff, String player, @Nullable String server){
        return new Warning(reason, staff, player, server);
    }

    public void updateWarning(Warning warning){
        ModRealmsAPI.getInstance().getLogger().info("Updating warning to database and cache list");
        datastore.save(warning);
    }

    public Optional<Mute> getMuteById(ObjectId id){
        Query<Mute> muteQuery = datastore.createQuery(Mute.class).filter("_id",id);
        if(muteQuery.asList().size() >= 1){
            Mute updatedMute = muteQuery.get();
            return Optional.of(updatedMute);
        }
        return Optional.empty();
    }

    public Mute newMute(String reason,String staff,String player,@Nullable String duration,@Nullable String ip){
        return new Mute(reason, staff, player, duration, ip);
    }

    public void updateMute(Mute mute){
        ModRealmsAPI.getInstance().getLogger().info("Updating mute to database and cache list");
        datastore.save(mute);
    }
}
