package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import net.modrealms.api.ModRealmsAPI;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Embedded;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity(value = "tickets", noClassnameStored = true)
public class Ticket {
    @Id
    @Getter
    @Property("_id")
    private ObjectId id;
    @Property("creator_id")
    private ObjectId creatorid;
    @Setter
    @Property("channel_id")
    private String channelId;
    @Property("creator")
    private String creator;
    @Property("server")
    private String server;
    @Property("message") @Setter
    private String message;
    @Setter @Property("status")
    private String status;
    @Property("creation_date")
    private Date creationdate;
    @Setter @Embedded
    private WorldLoc location;
    @Getter @Setter @Property("replies")
    private List<String> replies;

    @Getter @Setter
    private HashMap<String, Boolean> options;

    public Ticket(){
        //For Morphia Constructor
    }


    public Ticket(BasePlayer player, Server server, @Nullable String message,WorldLoc location,Boolean launcher,Boolean bugs,Boolean recipe,Boolean lag,Boolean suggestion,Boolean other) {
        this.id = new ObjectId();
        this.creatorid = player.getId();
        this.creationdate = new Date();
        this.creator = player.getName();
        this.server = server.getServername();
        if(message != null){
            this.message = message;
        }
        this.status = "open";
        this.options = new HashMap<>();
        this.options.put("launcher", launcher);
        this.options.put("bugs", bugs);
        this.options.put("recipe", recipe);
        this.options.put("suggestion", suggestion);
        this.options.put("lag", lag);
        this.options.put("other", other);
        this.location = location;
        this.replies = new ArrayList<>();
        player.addTicket(this);
        ModRealmsAPI.getInstance().getDaoManager().getBasePlayerDAO().updatePlayer(player);
        // TicketCreate.createTicketChannel(this);
    }

    public int getReplyCount(){
        checkRepliesNull();
        if(this.replies.isEmpty()){
            return 0;
        }
        else{
            return this.replies.size();
        }
    }

    public BasePlayer getCreator(){
        return ModRealmsAPI.getInstance().getDaoManager().getBasePlayerDAO().getPlayerById(this.getCreatorId()).get();
    }

    public ObjectId getCreatorId(){
        return this.creatorid;
    }

    public String getMessage(){
        return this.message;
    }

    public WorldLoc getLocation(){
        return this.location;
    }

    public void addReply(String author, String message){
        checkRepliesNull();
        this.replies.add(author + ": " + message);
    }

    public String getLastReply(){
        checkRepliesNull();
        return this.getReplies().get(this.getReplies().size() - 1);
    }

    public String getServer(){
        return this.server;
    }

    public boolean isClosed(){
        return this.getStatus().toLowerCase().equals("closed");
    }

    public Date getCreationDate(){
        return this.creationdate;
    }

    public String getChannelId(){
        return this.channelId;
    }

    public String getStatus(){
        return this.status.substring(0, 1).toUpperCase() + this.status.substring(1).toLowerCase();
    }


    private void checkRepliesNull(){
        if(this.replies==null){
            this.replies = new ArrayList<>();
        }
    }

    public boolean equals(Ticket ticket) {
        return ticket.getId().equals(this.id);
    }
}
