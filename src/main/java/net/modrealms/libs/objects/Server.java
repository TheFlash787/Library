package net.modrealms.libs.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity("servers")
public class Server {
    @Id
    @Property("_id")
    @Getter
    private ObjectId id;

    @Getter @Setter
    @Property("server_name")
    private String servername;

    @Getter @Setter
    @Property("modpack")
    private String modpack;

    @Getter @Setter
    @Property("modpack_version")
    private String version;

    @Getter @Setter
    @Property("manager")
    private String manager;

    @Getter @Setter
    @Property("banned_items")
    private String bannedItems;

    @Getter @Setter
    @Property("hostname")
    private String hostname;

    @Getter @Setter
    @Property("image-url")
    private String image;

    @Getter @Setter
    @Property("reaction-id")
    private String reactionId;

    @Getter @Setter
    private String description;

    @Getter @Setter
    @Property("release_date")
    private Date releasedate;

    @Getter @Setter
    @Property("is-whitelisted")
    private boolean isWhitelisted;

    @Getter
    @Setter
    private Double tps;

    @Setter
    @Property("online_players")
    private List<ObjectId> onlinePlayers;

    @Getter
    @Setter
    @Property("discord_roles")
    private HashMap<String, String> discordRoles;

    @Getter
    @Setter
    @Property("is_online")
    private boolean isOnline;

    public List<ObjectId> getPlayers(){
        if(this.onlinePlayers == null){
            this.onlinePlayers = new ArrayList<>();
        }
        return this.onlinePlayers;
    }
}
