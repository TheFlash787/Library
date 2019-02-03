package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "progress-milestones", noClassnameStored = true)
public class ProgressMilestone {

    @Getter @Setter @Id @Property("_id")
    private ObjectId id;

    @Getter @Setter @Property("needed_minutes")
    private long minutes;

    @Setter
    private List<String> commands;

    @Setter
    private List<String> rewards;

    @Getter @Setter
    private String rank;

    @Getter @Setter
    private int cost;

    public ProgressMilestone(){

    }

    public ProgressMilestone(long minutes, String rank){
        this.id = new ObjectId();
        this.rank = rank;
        this.minutes = minutes;
        this.cost = 0;
        this.commands = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    public List<String> getCommands(){
        if(this.commands == null){
            this.commands = new ArrayList<>();
        }
        return this.commands;
    }
    public List<String> getRewards(){
        if(this.rewards == null){
            this.rewards = new ArrayList<>();
        }
        return this.rewards;
    }
}
