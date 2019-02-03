package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.RestrictedItem;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;

public class RestrictionDAO {
    private static final ModRealmsAPI api = ModRealmsAPI.getInstance();
    private static final Datastore datastore = api.getMongo().getDatastore();

    public Optional<RestrictedItem> getItemById(ObjectId objectId) {
        Query<RestrictedItem> itemQuery = datastore.createQuery(RestrictedItem.class).filter("_id", objectId);
        if(itemQuery.asList().size() == 1) {
            RestrictedItem updatedRestrictedItem = itemQuery.get();
            return Optional.of(updatedRestrictedItem);
        }
        return Optional.empty();
    }

    public Optional<RestrictedItem> getItem(String rank) {

        Query<RestrictedItem> itemQuery = datastore.createQuery(RestrictedItem.class).field("rank").equalIgnoreCase(rank);
        if(itemQuery.asList().size() == 1) {
            RestrictedItem updatedRestrictedItem = itemQuery.get();
            return Optional.of(updatedRestrictedItem);
        }
        return Optional.empty();
    }

    public RestrictedItem newItem(String itemid) {
        //we dont update the item here as that will be done in the login logic
        return new RestrictedItem(itemid);
    }

    public void updateItem(RestrictedItem item) {
        //MRLogger.DEBUG("Updating "+ item.getRestrictedItemname()+" to database and cache list");
        datastore.save(item);
    }
}
