package net.modrealms.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(value = "staff-members", noClassnameStored = true)
public class StaffMember {
    @Id @Getter @Setter
    private ObjectId id;
    @Property("name") @Getter @Setter
    private String name;
    @Property("ingame_name") @Getter @Setter
    private String ingameName;
    @Embedded @Getter @Setter
    private Role role;
    @Property("email_address") @Getter @Setter
    private String email;
    @Property("last_promotion_date")@Setter
    private List<String> promotions;
    @Property("hiring_date") @Getter @Setter
    private String hiringDate;
    @Embedded @Getter @Setter
    private Activity activity;
    @Property("received_training") @Getter @Setter
    private Boolean receivedTraining;
    @Property("primary_server") @Getter @Setter
    private String primaryServer;
    @Embedded("staff_warnings")@Setter
    private List<StaffWarning> warnings;
    @Reference @Getter @Setter
    private BasePlayer baseplayer;
    @Property("image_url") @Getter @Setter
    private String image;
    @Property("interface_id") @Getter @Setter
    private Long interfaceId;
    @Embedded("brownie_points") @Setter
    private List<BrowniePoint> browniePoints;

    public StaffMember(){

    }

    public StaffMember(String name, String email, String server, BasePlayer basePlayer){
        this.id = new ObjectId();
        this.baseplayer = basePlayer;
        this.ingameName = basePlayer.getName();
        this.name = name;
        this.email = email;
        this.role = Role.HELPER;
        this.hiringDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.activity = Activity.ACTIVE;
        this.receivedTraining = false;
        this.primaryServer = server;
//        this.warnings = new ArrayList<>();
//        this.browniePoints = new ArrayList<>();
        this.image = "https://modrealms.net/img/small.png";
    }

    public List<String> getPromotions(){
        if(this.promotions == null){
            this.promotions = new ArrayList<>();
        }
        return this.promotions;
    }

    public List<StaffWarning> getWarnings(){
        if(this.warnings == null){
            this.warnings = new ArrayList<>();
        }
        return this.warnings;
    }

    public List<BrowniePoint> getBrowniePoints(){
        if(this.browniePoints == null){
            this.browniePoints = new ArrayList<>();
        }
        return this.browniePoints;
    }

}