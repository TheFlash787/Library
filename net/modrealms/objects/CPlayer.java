package net.modrealms.objects;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.modrealms.api.ModRealmsAPI;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity("conversify-players") @Data
public class CPlayer {

    @Property("_id") @Id
    private UUID uuid;
    @Property("name")
    private String name;
    @Property("display_name")
    private String displayName;
    @Property("is_staff")
    private boolean is_staff;
    private String channel;
    private ObjectId partyId;
    @Property("visible_channels")
    private List<String> visibleChannels;
    private List<ObjectId> warnings;
    private List<ObjectId> kicks;
    private List<ObjectId> mutes;
    private List<ObjectId> bans;
    private String prefix;
    private String suffix;
    @Property("recent_pm")
    private UUID recent;
    @Property("party_invites")
    private List<ObjectId> invites;

    public CPlayer(){
        //Morphia Constructor
    }

    public CPlayer(ProxiedPlayer player){
        this.name = player.getName();
        this.displayName = player.getName();
        this.channel = "GLOBAL";
        this.uuid = player.getUniqueId();
        this.is_staff = false;
        this.visibleChannels = new ArrayList<>();
        this.visibleChannels.add("GLOBAL");
        this.visibleChannels.add("MODPACK");
        this.visibleChannels.add("VOTING");
        this.visibleChannels.add("PARTY");
        this.visibleChannels.add("DISCORD");
        this.visibleChannels.add("NEWS");
        this.visibleChannels.add("INFO");
    }

    public List<String> getVisibleChannels(){
        checkChannelsNull();
        return this.visibleChannels;
    }

    public List<ObjectId> getInvites(){
        checkInvitesNull();
        return this.invites;
    }

    public List<ObjectId> getWarnings(){
        checkWarns();
        return this.warnings;
    }

    public List<ObjectId> getKicks(){
        checkKicks();
        return this.kicks;
    }

    public List<ObjectId> getBans(){
        checkBansNull();
        return this.bans;
    }

    public List<ObjectId> getMutes(){
        checkMutes();
        return this.mutes;
    }

    private void checkChannelsNull(){
        if(this.visibleChannels==null){
            this.visibleChannels = new ArrayList<>();
        }
    }

    private void checkWarns(){
        if(this.warnings==null){
            this.warnings = new ArrayList<>();
        }
    }

    private void checkKicks(){
        if(this.kicks==null){
            this.kicks = new ArrayList<>();
        }
    }

    private void checkMutes(){
        if(this.mutes==null){
            this.mutes = new ArrayList<>();
        }
    }

    private void checkBansNull(){
        if(this.bans==null){
            this.bans = new ArrayList<>();
        }
    }

    private void checkInvitesNull(){
        if(this.invites==null){
            this.invites = new ArrayList<>();
        }
    }

    public void isBanned(){
        for(ObjectId banIds: this.bans){

        }
    }

    public void addInvite(ObjectId partyId){
        checkInvitesNull();
        this.invites.add(partyId);
    }

    public boolean hasParty(){
        return !ModRealmsAPI.getInstance().getMongo().getDatastore().createQuery(Party.class).filter("players",this.getUuid()).asList().isEmpty();
    }

    public void removeParty(ObjectId id){
        this.partyId = id;
    }

    public String getPrefix(){
        if(this.prefix == null) {
            this.prefix = "";
        }
        return this.prefix;
    }

    public String getSuffix(){
        if(this.suffix == null) {
            this.suffix = "";
        }
        return this.suffix;
    }

}
