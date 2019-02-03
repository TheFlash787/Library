package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "restricted-items", noClassnameStored = true)
public class RestrictedItem {
    @Id
    @Getter
    @Setter
    private ObjectId id;

    @Getter @Setter @Property("item_id")
    private String itemId;

    @Getter @Setter
    private String reason;

    @Setter
    private List<String> bypassingRanks;

    @Setter @Property("action_bypass")
    private List<RestrictingAction> bypassingActions;

    @Setter @Property("server_bypass")
    private List<String> bypassingServers;

    public RestrictedItem(){

    }

    public RestrictedItem(String itemId){
        this.id = new ObjectId();
        this.itemId = itemId;
        this.reason = "This item was restricted to improve the performance of the server and overall the community. Thank you for understanding.";
        this.bypassingActions = new ArrayList<>();
        this.bypassingActions.add(RestrictingAction.NONE);
        this.bypassingServers = new ArrayList<>();
        this.bypassingServers.add("NONE");
    }

    public List<String> getBypassingRanks(){
        if(this.bypassingRanks == null){
            this.bypassingRanks = new ArrayList<>();
        }
        return this.bypassingRanks;
    }

    public List<RestrictingAction> getBypassingActions(){
        if(this.bypassingActions == null){
            this.bypassingActions = new ArrayList<>();
        }
        return this.bypassingActions;
    }

    public List<String> getBypassingServers(){
        if(this.bypassingServers == null){
            this.bypassingServers = new ArrayList<>();
        }
        return this.bypassingServers;
    }
}
