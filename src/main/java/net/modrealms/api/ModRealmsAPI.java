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

    public ModRealmsAPI(Map<String, String> info,@Nullable ProxyServer bungee,@Nullable Game sponge,@Nullable Logger logger,boolean setupMongo,boolean setupJDA){
        this.setInfo(info);
        System.out.println("Input Info:");
        info.forEach((st, st1) -> System.out.println(st + " " + st1));
        System.out.println("Class Info:");
        this.info.forEach((st, st1) -> System.out.println(st + " " + st1));
        if(logger != null){
            this.setLogger(logger);
            System.out.println("ModRealms API> Successfully loaded SLF4J Logger");
        } else System.out.println("ModRealms API> Logger is not present!");

        if(sponge != null){
            this.setSponge(sponge);
            System.out.println("ModRealms API> Successfully loaded Sponge");
        } else System.out.println("ModRealms API> Sponge is not present!");

        if(bungee != null){
            this.setBungee(bungee);
            System.out.println("ModRealms API> Successfully loaded Bungee");
        } else System.out.println("ModRealms API> Bungee is not present!");

        if(setupMongo){
            this.setMongo(new Mongo());
            this.setDaoManager(new DAOManager());
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
}
