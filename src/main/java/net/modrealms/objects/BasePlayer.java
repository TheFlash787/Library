package net.modrealms.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.entities.Member;
import net.modrealms.api.ModRealmsAPI;
import org.apache.commons.text.RandomStringGenerator;
import org.bson.types.ObjectId;
import org.spongepowered.api.entity.living.player.Player;
import net.modrealms.objects.Ticket;
import xyz.morphia.annotations.*;

import java.util.*;

@SuppressWarnings("Lombok") @Data
@Entity(value = "players", noClassnameStored = true)
public class BasePlayer {
    @Id
    @Property("_id")
    private ObjectId id;
    @Property("uuid")
    @Indexed
    private UUID uuid;
    @Property("username")
    private String name;
    @Property("is-staff")
    private boolean isStaff;
    @Property("ticket_ids")
    private List<ObjectId> ticketIds;
    @Embedded("kits_Bought")
    private List<BoughtKit> kitsBought;
    @Property("toggles")
    private HashMap<String, Boolean> toggles;
    @Property("orb_balance")
    private Integer orbBalance;
    @Property("application_ids")
    private List<ObjectId> applicationIds;
    @Property("current_milestone")
    private ObjectId milestone;

    @Property("completed-milestones")
    private List<ObjectId> completedMilestones;
    private HashMap<String, Integer> transportInventory;
    @Property("donator_role")
    private DonatorRole donatorRole;
    @Property("last_server")
    private String lastServer;
    @Property("verify_code")
    private String verifyCode;
    @Property("display_name")
    private String displayName;
    @Property("progress_time")
    private Long progress;
    @Property("discord_id")
    private String discordId;
    @Property("pending_messages")
    private List<String> pendingMessages;
    @Property("first_vote_today")
    private Date firstVoteToday;
    @Property("last_vote")
    private Date lastVote;
    @Property("votes_today")
    private Integer votesToday;
    @Property("votes")
    private Integer votes;
    private Boolean makingTicket;
    @Property("last_leave_date")
    private Date lastJoinDate;
    @Property("last_join_date")
    private Date lastLeaveDate;
    @Property("afktime_seconds")
    private long minutesAFK;

    public BasePlayer(){
        //Morphia Constructor
    }

    public BasePlayer(Player player,Server server){
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

        this.id = new ObjectId();
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.displayName = player.getName();
        this.ticketIds = new ArrayList<>();
        this.orbBalance = 0;
        this.toggles = new HashMap<>();
        this.progress = 0L;
        this.kitsBought = new ArrayList<>();
        this.lastServer = server.getServername();
        this.votes = 0;
        this.verifyCode = generator.generate(6);
        this.discordId = "";
        this.pendingMessages = new ArrayList<>();
        this.toggles.put("reconnect", true);
    }

//    public ServerBossBar newBossBar(){
//        ServerBossBar.Builder builder = ServerBossBar.builder();
//        builder.color(BossBarColors.PURPLE);
//        builder.name(Text.of(TextColors.LIGHT_PURPLE, "Welcome, " + this.getName() + "!"));
//        builder.overlay(BossBarOverlays.PROGRESS);
//        builder.percent(1f);
//        return builder.build();
//    }

    public List<Ticket> getTickets(){
        checkTicketsNull();
        List<Ticket> ticketList = new ArrayList<>();
        for(ObjectId ids : ticketIds){
            Optional<Ticket> ticketOptional = ModRealmsAPI.getInstance().getDaoManager().getTicketDAO().getTicketById(ids);
            ticketOptional.ifPresent(ticketList::add);
        }
        return ticketList;
    }

    public List<String> getPendingMessages(){
        checkPendingMessagesNull();
        return this.pendingMessages;
    }

    public ProgressMilestone getNextMilestone(){
        List<ProgressMilestone> milestones = ModRealmsAPI.getInstance().getMongo().getDatastore().createQuery(ProgressMilestone.class).asList();

        for(ProgressMilestone milestone: milestones){
            if(this.progress >= milestone.getMinutes()){
                if(!this.getCompletedMilestones().contains(milestone.getId())){
                    return milestone;
                }
            }
            else if(milestone.getMinutes() > this.progress){
                if(!this.getCompletedMilestones().contains(milestone.getId())){
                    return milestone;
                }
            }
        }
        return null;
    }

    public List<ObjectId> getCompletedMilestones(){
        if(this.completedMilestones == null){
            this.completedMilestones = new ArrayList<>();
        }
        return this.completedMilestones;
    }

    public void removeTicket(Ticket ticket){
        checkTicketsNull();
        this.ticketIds.remove(ticket.getId());
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

    public net.dv8tion.jda.core.entities.User getDiscordUser(){
        return ModRealmsAPI.getInstance().getJDA().getUserById(this.discordId);
    }

    public Member getDiscordMember(){
        return ModRealmsAPI.getInstance().getJDA().getGuildById("210739122577473536").getMemberById(this.discordId);

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

    public void addTicket(Ticket ticket){
        checkTicketsNull();
        this.ticketIds.add(ticket.getId());
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

    public Optional<StaffMember> getStaff(){
        return Optional.of(ModRealmsAPI.getInstance().getMongo().getDatastore().createQuery(StaffMember.class).filter("baseplayer", this).get());
    }

    public boolean isStaff(){
        return !ModRealmsAPI.getInstance().getMongo().getDatastore().createQuery(StaffMember.class).filter("baseplayer",this).asList().isEmpty();

    }

    public HashMap<String, Integer> getTransportInventory(){
        if(this.transportInventory == null){
            this.transportInventory = new HashMap<>();
        }
        return this.transportInventory;
    }
}
