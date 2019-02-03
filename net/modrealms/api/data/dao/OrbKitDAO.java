package net.modrealms.api.data.dao;

import lombok.Getter;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.OrbKit;
import net.modrealms.objects.ProgressMilestone;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Optional;

public class OrbKitDAO {
    @Inject
    Logger logger;

    @Getter
    private HashMap<String, ObjectId> cachedOrbKits = new HashMap<>();

    private static final ModRealmsAPI utils = ModRealmsAPI.getInstance();
    private static final Datastore datastore = utils.getMongo().getDatastore();

    public Optional<OrbKit> getOrbKit(String name) {
        Query<OrbKit> kitQuery = datastore.createQuery(OrbKit.class).filter("name", name);
        if(kitQuery.asList().size() >= 1) {
            OrbKit updatedOrbKit = kitQuery.get();
            if(kitQuery.asList().size() > 1){
                logger.error("OrbKit "+name+" has more than one object!");
            }
            return Optional.of(updatedOrbKit);
        }
        return Optional.empty();
    }

    public Optional<OrbKit> getOrbKitById(ObjectId objectId) {
        Query<OrbKit> kitQuery = datastore.createQuery(OrbKit.class).filter("_id", objectId);
        if(kitQuery.asList().size() == 1) {
            OrbKit updatedOrbKit = kitQuery.get();
            return Optional.of(updatedOrbKit);
        }
        return Optional.empty();
    }

    public Optional<OrbKit> getKitByName(String name) {

        Query<OrbKit> kitQuery = datastore.createQuery(OrbKit.class).field("name").equalIgnoreCase(name);
        if(kitQuery.asList().size() == 1) {
            OrbKit updatedOrbKit = kitQuery.get();
            return Optional.of(updatedOrbKit);
        }
        return Optional.empty();
    }

    public OrbKit newOrbKit(Integer cost, String name) {
        return new OrbKit(name, cost);
    }

    public void updateOrbKit(OrbKit OrbKit) {
        ModRealmsAPI.getInstance().getLogger().info("Updating "+ OrbKit.getName()+" to database and cache list");
        this.cachedOrbKits.put(OrbKit.getName(), OrbKit.getId());
        datastore.save(OrbKit);
    }
}
