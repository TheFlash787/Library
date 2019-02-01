package net.modrealms.libs.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;

@Entity(value = "pending-commands", noClassnameStored = true)
public class PendingCommand {
    @Getter @Id @Property("_id")
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
