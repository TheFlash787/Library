package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.Loaf;
import net.modrealms.objects.WorldLoc;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoafDAO {
    private static final ModRealmsAPI api = ModRealmsAPI.getInstance();
    private static final Datastore datastore = api.getMongo().getDatastore();

    public Optional<Loaf> getLoaf(UUID uuid) {
        Query<Loaf> loafQuery = datastore.createQuery(Loaf.class).field("uuid").equalIgnoreCase(uuid);
        if(loafQuery.asList().size() == 1) {
            Loaf updatedLoaf = loafQuery.get();
            return Optional.of(updatedLoaf);
        }
        return Optional.empty();
    }

    public Optional<Loaf> getLoafWithLoc(WorldLoc loc) {
        Query<Loaf> loafQuery = datastore.createQuery(Loaf.class);
        for(Loaf loaf: loafQuery.asList()){
            if(loaf.getLocationData().getChunkX() == loc.getChunkX() && loaf.getLocationData().getChunkZ() == loc.getChunkZ()){
                return Optional.of(loaf);
            }
        }
        return Optional.empty();
    }

    public List<Loaf> getLoavesByOwner(UUID owner) {
        List<Loaf> list = new ArrayList<>();
        Query<Loaf> loafQuery = datastore.createQuery(Loaf.class);
        for(Loaf loaf: loafQuery.asList()){
            if(loaf.getOwner().getUuid().equals(owner)){
                list.add(loaf);
            }
        }
        return list;
    }

    public void updateLoaf(Loaf loaf) {
        //MRLogger.DEBUG("Updating "+ loaf.getLoafname()+" to database and cache list");
        datastore.save(loaf);
    }
}
