package net.modrealms.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity(value = "kits", noClassnameStored = true) @Data
public class OrbKit {
    @Id
    @Property("_id")
    private ObjectId id;

    private Integer price;

    private String name;

    private String note;

    private List<String> commands;

    private HashMap<String, String> kitItems;

    private String placeholder;

    private List<String> blacklist;

    private List<String> requiredGroups;

    public OrbKit(){
        // Morphia Constructor
    }

    public OrbKit(String name, Integer price){

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
