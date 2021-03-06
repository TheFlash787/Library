package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity(value = "kits-17", noClassnameStored = true)
public class OrbKit17 {
    @Id
    @Getter
    @Property("_id")
    private ObjectId id;

    @Getter @Setter
    private Integer price;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String note;

    @Setter
    private List<String> commands;

    @Setter
    private HashMap<String, String> kitItems;

    @Setter
    private List<String> blacklist;

    @Setter
    private List<String> requiredGroups;

    public OrbKit17(){
        // Morphia Constructor
    }

    public OrbKit17(String name, Integer price){

        this.id = new ObjectId();
        this.price = price;
        this.name = name;
        this.note = "";
        this.commands = new ArrayList<>();
        this.kitItems = new HashMap<>();
        this.blacklist = new ArrayList<>();
        this.requiredGroups = new ArrayList<>();

        this.commands.add("give mrplayer x y");
        this.kitItems.put("itemname", "1");
        this.requiredGroups.add("default");

    }

    public List<String> getCommands(){
        checkCommandsNotNull();
        return this.commands;
    }

    public List<String> getRequiredGroups(){
        checkGroupsNotNull();
        return this.requiredGroups;
    }

    public HashMap<String, String> getKitItems(){
        checkKitItemsNotNull();
        return this.kitItems;
    }

    public List<String> getBlacklist(){
        if(this.blacklist == null){
            this.blacklist = new ArrayList<>();
        }
        return this.blacklist;
    }

    public void addRequiredGroup(String group){
        checkGroupsNotNull();
        this.requiredGroups.add(group);
    }

    private void checkCommandsNotNull(){
        if(this.commands == null){
            this.commands = new ArrayList<>();
        }
    }
    private void checkGroupsNotNull(){
        if(this.requiredGroups == null){
            this.requiredGroups = new ArrayList<>();
        }
    }

    private void checkKitItemsNotNull(){
        if(this.kitItems == null){
            this.kitItems = new HashMap<>();
        }
    }
}