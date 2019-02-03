package net.modrealms.objects;

import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.modrealms.conversify.Conversify;
import net.modrealms.conversify.discord.JDAHandler;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;

@Entity("conversify-channels") @Data
public class Channel {
    @Id
    @Property("_id")
    private String id;
    @Property("prefix")
    private String prefix;
    @Property("required_permission")
    private String requiredPerm;
    @Property("alias")
    private String alias;
    @Property("interval_messages")
    private List<String> messages;
    @Property("override")
    private Boolean overrideToggle;
    @Property("user_send")
    private Boolean user_can_send;
    @Property("user_view")
    private Boolean user_can_view;
    @Property("staff_send")
    private Boolean staff_can_send;
    @Property("staff_view")
    private Boolean staff_can_view;
    private String description;

    public Channel(){
        //Morphia Constructor
    }

    public Channel(String id){
        this.id = id;
        this.prefix = id;
        this.alias = "";
        this.description = "";
        this.staff_can_send = true;
        this.staff_can_view = true;
        this.user_can_send = true;
        this.user_can_view = true;
        this.overrideToggle = false;
        this.messages = new ArrayList<>();
    }



    public void sendMessage(CPlayer sender,String content){
        if(this.getId().equals("DISCORD")){
            for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayers()){
                CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
                String message = ChatColor.translateAlternateColorCodes('&',this.prefix + sender.getPrefix() + sender.getDisplayName() + sender.getSuffix() + " &f- " + content);
                if(overrideToggle||(receiver.getVisibleChannels().contains(this.getId())&&sender.getVisibleChannels().contains(this.getId()))){
                    onlinePlayers.sendMessage(message);
                }
                Conversify.getInstance().getProxy().getLogger().info(message);
            }
        }
        else{
            LuckPermsApi luckPermsApi = Conversify.getInstance().getLuckPermsApi();
            User user = luckPermsApi.getUser(sender.getUuid());

            ContextManager cm = luckPermsApi.getContextManager();

            //ImmutableContextSet contextSet = cm.lookupApplicableContext(user).orElse(cm.getStaticContext());
            Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
            MetaData metaData = user.getCachedData().getMetaData(contexts);
            String prefix = metaData.getPrefix();
            String suffix = metaData.getSuffix();

            if(suffix==null){
                suffix = "";
            }

            for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayers()){
                CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
                if((overrideToggle)||(receiver.getVisibleChannels().contains(this.getId())&&sender.getVisibleChannels().contains(this.getId()))){
                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&',this.prefix+prefix+sender.getDisplayName()+suffix+" &f- "+content));
                    // onlinePlayers.sendMessage(new ComponentBuilder("").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("hi").create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/")).append(ChatColor.translateAlternateColorCodes('&',this.prefix+prefix+sender.getDisplayName()+suffix+" &f- "+content)).create());
                }
            }
        }
    }

    public void sendPlainMessage(String text){
        for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayers()){
            CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
            if(overrideToggle||(receiver.getVisibleChannels().contains(this.getId()))){
                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&',text));
            }
        }
    }
    public void sendPlainMessage(BaseComponent[] text){
        for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayers()){
            CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
            if(overrideToggle||(receiver.getVisibleChannels().contains(this.getId()))){
                onlinePlayers.sendMessage(text);
            }
        }
    }

    public void sendVoteMessage(String player){
        for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayers()){
            CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
            if(overrideToggle||(receiver.getVisibleChannels().contains(this.getId()))){
                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&',this.prefix+"&e"+player+" &ahas just voted for &dModRealms&a and received orbs!"));
            }
        }
    }

    public void sendPartyMessage(CPlayer sender,String content){

        LuckPermsApi luckPermsApi = Conversify.getInstance().getLuckPermsApi();
        User user = luckPermsApi.getUser(sender.getUuid());
        MetaData metaData = Conversify.getInstance().getLuckPermsMetadata(user);
        String prefix = metaData.getPrefix();
        String suffix = metaData.getSuffix();

        if(suffix==null){
            suffix = "";
        }

        boolean success = false;
        for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayers()){
            CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
            if(receiver.getVisibleChannels().contains(this.getId())&&sender.getVisibleChannels().contains(this.getId())){
                if((receiver.getPartyId()!=null)){
                    if(receiver.getPartyId().equals(sender.getPartyId())){
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&',this.prefix+prefix+sender.getDisplayName()+suffix+" &f- "+content));
                        success = true;
                    }
                }
            }
        }
        if(success){
            for(ProxiedPlayer all: Conversify.getInstance().getProxy().getPlayers()){
                CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(user.getUuid()).get();
                CPlayer cPlayer = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(all.getUniqueId()).get();
                    if(cPlayer.is_staff() && !cPlayer.getUuid().equals(receiver.getUuid()) && !cPlayer.getUuid().equals(sender.getUuid())){
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6PartySpy &f - "+sender.getDisplayName()+" &f- "+content));
                    }
                }
            }
        }

    public void sendLocalMessage(CPlayer sender,String content){
        LuckPermsApi luckPermsApi = Conversify.getInstance().getLuckPermsApi();
        User user = luckPermsApi.getUser(sender.getUuid());

        ContextManager cm = luckPermsApi.getContextManager();

        //ImmutableContextSet contextSet = cm.lookupApplicableContext(user).orElse(cm.getStaticContext());
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData metaData = user.getCachedData().getMetaData(contexts);
        String prefix = metaData.getPrefix();
        String suffix = metaData.getSuffix();

        if(suffix==null){
            suffix = "";
        }

        for(ProxiedPlayer onlinePlayers: Conversify.getInstance().getProxy().getPlayer(sender.getUuid()).getServer().getInfo().getPlayers()){
            CPlayer receiver = Conversify.getInstance().getDaoManager().getCPlayerDAO().getPlayer(onlinePlayers.getUniqueId()).get();
            if(overrideToggle||(receiver.getVisibleChannels().contains(this.getId())&&sender.getVisibleChannels().contains(this.getId()))){
                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&',this.prefix+prefix+sender.getDisplayName()+suffix+" &f- "+content));
            }
        }
    }
}
