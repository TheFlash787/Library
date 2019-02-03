package net.modrealms.objects;

import lombok.Data;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity("staff-members") @Data
public class StaffMember {
    @Id
    private ObjectId id;
    @Property("name")
    private String name;
    @Property("ingame_name")
    private String ingameName;
    @Embedded
    private Role role;
    @Property("email_address")
    private String email;
    @Property("last_promotion_date")
    private List<String> promotions;
    @Property("hiring_date")
    private String hiringDate;
    @Embedded
    private Activity activity;
    @Property("received_training")
    private Boolean receivedTraining;
    @Property("primary_server")
    private String primaryServer;
    @Embedded("staff_warnings")
    private List<StaffWarning> warnings;
    private UUID UUID;
    @Reference
    private BasePlayer baseplayer;
    @Property("image_url")
    private String image;
    @Property("interface_id")
    private Long interfaceId;
    @Embedded("brownie_points")
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
        this.warnings = new ArrayList<>();
        this.browniePoints = new ArrayList<>();
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

    public void addPromotion(String formattedDate){
        this.getPromotions().add(formattedDate);
    }
    public void addWarning(StaffWarning warning){this.getWarnings().add(warning);}

}
