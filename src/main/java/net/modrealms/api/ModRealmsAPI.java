package net.modrealms.api;
import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.PteroUserAPI;
import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.core.JDA;
import net.md_5.bungee.api.ProxyServer;
import net.modrealms.api.data.DAOManager;
import net.modrealms.api.data.Mongo;
import net.modrealms.api.jda.JDAConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ModRealmsAPI {
    @Getter
    public static ModRealmsAPI instance;
    public JDA JDA;
    public Game sponge;
    public Mongo mongo;
    public DAOManager daoManager;
    public PteroAdminAPI pteroAdminAPI;
    public PteroUserAPI pteroUserAPI;
    public ProxyServer bungee;
    public Map<String, String> info;
    public Logger logger;

    public ModRealmsAPI(Class project, Map<String, String> info, @Nullable ProxyServer bungee, @Nullable Game sponge, boolean setupMongo, boolean setupJDA){
        instance = this;
        this.setInfo(info);
        logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

        if(sponge != null){
            this.sponge = sponge;
            logger.info("Successfully loaded Sponge");
        } else logger.info("Sponge is not present!");

        if(bungee != null){
            this.bungee = bungee;
            logger.info("Successfully loaded Bungee");
        } else logger.info("Bungee is not present!");

        if(setupMongo){
            this.mongo = new Mongo(project);
            this.daoManager = new DAOManager();
            logger.info("Successfully loaded Mongo");
        } else logger.info("Mongo will not be loaded!");

        if(setupJDA){
            JDAConnection.connect();
            this.setJDA(JDAConnection.API);
            logger.info("Successfully loaded JDA");
        } else logger.info("JDA will not be loaded!");

        if(this.info.containsKey("ptero-api-key")){
            // Setup Pterodactyl API
            this.setPteroAdminAPI(new PteroAdminAPI("https://manage.modrealms.net", this.info.get("ptero-admin-api-key")));
            this.setPteroUserAPI(new PteroUserAPI("https://manage.modrealms.net", this.info.get("ptero-user-api-key")));
        }
    }

    public JDA getJDA(){
        try{
            return this.JDA;
        } catch(NullPointerException e){
            logger.info("JDA is not present!");
            return null;
        }
    }

    public Game getSponge(){
        try{
            return this.sponge;
        } catch(NullPointerException e){
            logger.info("Sponge is not present!");
            return null;
        }
    }

    public ProxyServer getBungee(){
        try{
            return this.bungee;
        } catch(NullPointerException e){
            logger.info("Bungee is not present!");
            return null;
        }
    }

    public Mongo getMongo(){
        try{
            return this.mongo;
        } catch(NullPointerException e){
            logger.info("Mongo/Morphia is not present!");
            return null;
        }
    }

    public DAOManager getDaoManager(){
        try{
            return this.daoManager;
        } catch(NullPointerException e){
            logger.info("Mongo/Morphia is not present!");
            return null;
        }
    }
}
