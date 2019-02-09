package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import xyz.morphia.annotations.*;

@Entity(value = "interview", noClassnameStored = true)
public class Interview {
    @Id @Getter @Setter @Property("_id")
    private ObjectId id;

    @Getter @Setter @Reference
    private StaffMember manager;

    @Getter @Setter @Reference
    private BasePlayer interviewee;

    @Getter @Setter @Embedded
    private DateTime startDate;

    @Getter @Setter
    private String duration;

    @Getter @Setter
    private long channel;

    @Getter @Setter
    private long message;

    @Getter @Setter
    private boolean training;

    public Interview(){

    }

    /**
     * Creates a new Interview object
     * @param player
     * @param manager
     */
    public Interview(BasePlayer player, StaffMember manager){
        this.id = new ObjectId();
        this.manager = manager;
        this.interviewee = player;
        this.startDate = new DateTime();
        this.training = false;
    }
}
