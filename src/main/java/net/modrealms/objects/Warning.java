package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Entity;

import javax.annotation.Nullable;
import java.util.Date;

@Entity("punishments-warnings")
public class Warning {
    @Getter @Setter @Id
    private ObjectId id;

    @Getter @Setter
    private String player;

    @Getter @Setter
    private String staff;

    @Getter @Setter
    private Date date;

    @Getter @Setter
    private Date expiration_date;

    @Getter @Setter
    private String reason;

    @Getter @Setter
    private String server;

    public Warning(){

    }

    public Warning(String reason, String staff, String player, @Nullable String server){
        this.id = new ObjectId();
        this.player = player;
        if(server != null){
            this.server = server;
        }
        this.reason = reason;
        this.staff = staff;
        this.date = new Date();
    }
}
