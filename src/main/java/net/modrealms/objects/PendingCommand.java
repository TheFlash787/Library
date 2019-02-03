package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.Date;
import java.util.List;

@Entity(value = "pending-commands", noClassnameStored = true)
public class PendingCommand {
    @Getter @Id
    @Property("_id")
    private ObjectId id;
    @Getter @Property("commands")
    private List<String> commands;
    @Getter @Setter
    private Date date;
    @Getter @Setter
    private List<String> servers;

    public PendingCommand(){

    }

    public PendingCommand(List<String> commands, List<String> servers){
        this.id = new ObjectId();
        this.commands = commands;
        this.servers = servers;
        this.date = new Date();
    }
}
