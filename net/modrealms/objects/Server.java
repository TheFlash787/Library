package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.spongepowered.api.Sponge;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity(value = "servers", noClassnameStored = true)
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

    public Server(){

    }

    public Server(String servername){
        this.id = new ObjectId();
        this.releasedate = new Date();
        this.servername = servername;
        this.manager = "";
        this.modpack = "";
        this.version = "";
        this.hostname = "";
        this.image = "";
        this.reactionId = "";
        this.bannedItems = "";
        this.description = "";
        this.isWhitelisted = Sponge.getServer().hasWhitelist();
        this.isOnline = true;
        this.tps = 20.0;
        this.onlinePlayers = new ArrayList<>();
        this.discordRoles = new HashMap<>();

        discordRoles.put("staff_roleId", "");
        discordRoles.put("main_roleId", "");

    }

    public static boolean isAutomatedRestart(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date time = new Date();
        String formatted = formatter.format(time);

        return formatted.equals("00:00") || formatted.equals("06:00") || formatted.equals("12:00") || formatted.equals("18:00");

    }

    public void addPlayerToServer(BasePlayer player){
        checkPlayersNull();
        this.onlinePlayers.add(player.getId());
    }

    public List<ObjectId> getOnlinePlayers(){
        checkPlayersNull();
        //MRUtils.getInstance().getDaoManager().getServerDAO().updateServer(this);
        return this.onlinePlayers;
    }

    public void removePlayerFromServer(BasePlayer player){
        checkPlayersNull();
        this.onlinePlayers.remove(player.getId());
    }

    public void clearAllPlayers(){
        checkPlayersNull();
        this.onlinePlayers.clear();
    }

    public void checkDiscordRolesNull(){
        if(this.discordRoles == null){
            this.discordRoles = new HashMap<>();
        }
    }

    public void checkPlayersNull(){
        if(this.onlinePlayers == null){
            this.onlinePlayers = new ArrayList<>();
        }
    }
}
