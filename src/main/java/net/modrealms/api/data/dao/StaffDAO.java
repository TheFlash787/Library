package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.StaffMember;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;

public class StaffDAO {
    private final ModRealmsAPI api = ModRealmsAPI.getInstance();
    private final Datastore datastore = api.getMongo().getDatastore();

    public Optional<StaffMember> getStaffMemberById(ObjectId objectId) {
        Query<StaffMember> StaffMemberQuery = datastore.createQuery(StaffMember.class).filter("_id", objectId);
        if(StaffMemberQuery.asList().size() == 1) {
            StaffMember updatedStaffMember = StaffMemberQuery.get();
            return Optional.of(updatedStaffMember);
        }
        return Optional.empty();
    }

    public Optional<StaffMember> getStaffMember(String StaffMemberName) {

        Query<StaffMember> StaffMemberQuery = datastore.createQuery(StaffMember.class).field("StaffMember_name").equalIgnoreCase(StaffMemberName);
        if(StaffMemberQuery.asList().size() == 1) {
            StaffMember updatedStaffMember = StaffMemberQuery.get();
            return Optional.of(updatedStaffMember);
        }
        return Optional.empty();
    }

    public StaffMember newStaffMember() {
        //we dont update the StaffMember here as that will be done in the login logic
        return new StaffMember();
    }

    public void updateStaffMember(StaffMember StaffMember) {
        datastore.save(StaffMember);
        System.out.println("Updated " + StaffMember.getName() + " to database and cache list");
    }
}
