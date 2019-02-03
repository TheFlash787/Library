package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.ProgressMilestone;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;

public class ProgressDAO {
    private static final ModRealmsAPI api = ModRealmsAPI.getInstance();
    private static final Datastore datastore = api.getMongo().getDatastore();

    public Optional<ProgressMilestone> getMilestoneById(ObjectId objectId) {
        Query<ProgressMilestone> milestoneQuery = datastore.createQuery(ProgressMilestone.class).filter("_id", objectId);
        if(milestoneQuery.asList().size() == 1) {
            ProgressMilestone updatedProgressMilestone = milestoneQuery.get();
            return Optional.of(updatedProgressMilestone);
        }
        return Optional.empty();
    }

    public Optional<ProgressMilestone> getMilestone(String rank) {

        Query<ProgressMilestone> milestoneQuery = datastore.createQuery(ProgressMilestone.class).field("rank").equalIgnoreCase(rank);
        if(milestoneQuery.asList().size() == 1) {
            ProgressMilestone updatedProgressMilestone = milestoneQuery.get();
            return Optional.of(updatedProgressMilestone);
        }
        return Optional.empty();
    }

    public ProgressMilestone newMilestone(int hours, String rank) {
        //we dont update the milestone here as that will be done in the login logic
        return new ProgressMilestone(hours, rank);
    }

    public void updateMilestone(ProgressMilestone milestone) {
        //MRLogger.DEBUG("Updating "+ milestone.getProgressMilestonename()+" to database and cache list");
        datastore.save(milestone);
    }
}
