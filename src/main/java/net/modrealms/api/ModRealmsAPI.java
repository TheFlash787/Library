package net.modrealms.api;
import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.core.JDA;
import net.md_5.bungee.api.ProxyServer;
import net.modrealms.api.data.DAOManager;
import net.modrealms.api.data.Mongo;
import net.modrealms.api.jda.JDAConnection;
import org.slf4j.Logger;
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
    public Logger logger;
    public DAOManager daoManager;
    public ProxyServer bungee;
    public Map<String, String> info;

    public ModRealmsAPI(Map<String, String> info, @Nullable ProxyServer bungee, @Nullable Game sponge, boolean setupMongo, boolean setupJDA){
        instance = this;
        this.setInfo(info);

        if(sponge != null){
            this.sponge = sponge;
            System.out.println("ModRealms API> Successfully loaded Sponge");
        } else System.out.println("ModRealms API> Sponge is not present!");

        if(bungee != null){
            this.bungee = bungee;
            System.out.println("ModRealms API> Successfully loaded Bungee");
        } else System.out.println("ModRealms API> Bungee is not present!");

        if(setupMongo){
            this.mongo = new Mongo();
            this.daoManager = new DAOManager();
            System.out.println("ModRealms API> Successfully loaded Mongo");
        } else System.out.println("ModRealms API> Mongo will not be loaded!");

        if(setupJDA){
            JDAConnection.connect();
            this.setJDA(JDAConnection.API);
            System.out.println("ModRealms API>: Successfully loaded JDA");
        } else System.out.println("ModRealms API> JDA will not be loaded!");
    }

    public JDA getJDA(){
        try{
            return this.JDA;
        } catch(NullPointerException e){
            System.out.println("API: JDA is not present!");
            return null;
        }
    }

    public Game getSponge(){
        try{
            return this.sponge;
        } catch(NullPointerException e){
            System.out.println("API: Sponge is not present!");
            return null;
        }
    }

    public ProxyServer getBungee(){
        try{
            return this.bungee;
        } catch(NullPointerException e){
            System.out.println("API: Bungee is not present!");
            return null;
        }
    }

    public Mongo getMongo(){
        try{
            return this.mongo;
        } catch(NullPointerException e){
            System.out.println("API: Mongo/Morphia is not present!");
            return null;
        }
    }

    public DAOManager getDAOManager(){
        try{
            return this.daoManager;
        } catch(NullPointerException e){
            System.out.println("API: Mongo/Morphia is not present!");
            return null;
        }
    }
}
