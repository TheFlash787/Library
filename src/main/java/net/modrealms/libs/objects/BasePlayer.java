package net.modrealms.libs.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.*;

@Entity("players")
public class BasePlayer {
    @Id
    @Property("_id")
    @Getter
    private ObjectId id;

    @Property("uuid")
    @Indexed
    @Getter
    private UUID uuid;

    @Getter @Setter
    @Property("username")
    private String name;

    @Setter @Property("is-staff")
    private boolean isStaff;

    @Setter @Property("ticket_ids")
    private List<ObjectId> ticketIds;

    @Setter @Embedded("kits_Bought")
    private List<BoughtKit> kitsBought;

    @Setter @Property("toggles")
    private HashMap<String, Boolean> toggles;

    @Property("orb_balance")
    private Integer orbBalance;

    @Setter @Property("application_ids")
    private List<ObjectId> applicationIds;

    @Setter @Property("current_milestone")
    private ObjectId milestone;

    @Setter @Property("completed-milestones")
    private List<ObjectId> completedMilestones;

    @Setter
    private HashMap<String, Integer> transportInventory;

    @Getter @Setter @Property("last_server")
    private String lastServer;

    @Getter @Setter @Property("verify_code")
    private String verifyCode;

    @Getter @Setter @Property("display_name")
    private String displayName;

    @Getter @Setter @Property("progress_time")
    private Long progress;

    @Getter @Setter @Property("discord_id")
    private String discordId;

    @Property("pending_messages")
    private List<String> pendingMessages;

//    @Getter @Setter
//    private ServerBossBar bossBar;

    @Getter @Setter @Property("first_vote_today")
    private Date firstVoteToday;

    @Getter @Setter @Property("last_vote")
    private Date lastVote;

    @Getter @Setter @Property("votes_today")
    private Integer votesToday;

    @Getter @Setter @Property("votes")
    private Integer votes;

    @Getter @Setter
    private Boolean makingTicket;

    @Getter @Setter @Property("last_join_date")
    private Date lastJoinDate;

    @Getter @Setter @Property("last_leave_date")
    private Date lastLeaveDate;

    @Getter @Setter @Property("afktime_seconds")
    private long minutesAFK;

    public BasePlayer(){
        //Morphia Constructor
    }

    public List<String> getPendingMessages(){
        checkPendingMessagesNull();
        return this.pendingMessages;
    }

    public List<ObjectId> getCompletedMilestones(){
        if(this.completedMilestones == null){
            this.completedMilestones = new ArrayList<>();
        }
        return this.completedMilestones;
    }


    public void addToKitBought(OrbKit kit) {
        checkKitsBoughtNull();
        this.kitsBought.add(new BoughtKit(kit));
    }

    public Integer getOrbBalance(){
        checkOrbBalanceNull();
        return this.orbBalance;
    }

    public HashMap<String, Boolean> getToggles(){
        return this.toggles;
    }

    public void toggleValue(String key){
        if(this.toggles.get(key)){
            this.toggles.replace(key, false);
        }
        else{
            this.toggles.replace(key, true);
        }
    }

    public boolean isVerified(){
        return ! this.discordId.isEmpty();
    }

    public boolean isMutingBridge(){
        return this.toggles.get("mute-bridge");
    }

    public boolean isAllowingPvp(){
        return this.toggles.get("enabled-pvp");
    }

    public boolean willReconnect(){
        checkTogglesNull();
        return this.toggles.get("reconnect");
    }

    public void clearPendingMessages(){
        checkPendingMessagesNull();
        this.pendingMessages.clear();
    }

    public void addPendingMessage(String message){
        checkPendingMessagesNull();
        this.pendingMessages.add(message);
    }

    public void setOrbBalance(Integer newBalance){
        checkOrbBalanceNull();
        this.orbBalance = newBalance;
    }

    public void takeOrbBalance(Integer amountToTake){
        checkOrbBalanceNull();
        this.orbBalance = this.orbBalance-amountToTake;
    }

    public void addOrbBalance(Integer amountToAdd){
        checkOrbBalanceNull();
        this.orbBalance = this.orbBalance + amountToAdd;
    }

    public void updateVoteDate(){
        this.setLastVote(new Date());
    }

    public void addVote(){
        if(votes == null){
            this.votes = 1;
        }
        else{
            this.votes = this.votes + 1;
        }
    }

    private void checkOrbBalanceNull(){
        if(this.orbBalance == null){
            this.orbBalance = 0;
        }
    }

    private void checkTicketsNull(){
        if(this.ticketIds == null){
            this.ticketIds = new ArrayList<>();
        }
    }

    private void checkApplicationsNull(){
        if(this.applicationIds == null){
            this.applicationIds = new ArrayList<>();
        }
    }

    private void checkPendingMessagesNull(){
        if(this.pendingMessages == null){
            this.pendingMessages = new ArrayList<>();
        }
    }

    private void checkTogglesNull(){
        this.getToggles().putIfAbsent("mute-bridge", false);
        this.getToggles().putIfAbsent("enabled-pvp", true);
        this.getToggles().putIfAbsent("reconnect", false);
    }

    private void checkKitsBoughtNull(){
        if(this.kitsBought == null){
            this.kitsBought = new ArrayList<>();
        }
    }

    public HashMap<String, Integer> getTransportInventory(){
        if(this.transportInventory == null){
            this.transportInventory = new HashMap<>();
        }
        return this.transportInventory;
    }
}
