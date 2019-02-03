package net.modrealms.api;
import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.core.JDA;
import net.modrealms.api.data.DAOManager;
import net.modrealms.api.data.Mongo;
import net.modrealms.api.jda.JDAConnection;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import sun.rmi.runtime.Log;

import javax.annotation.Nullable;
import java.util.HashMap;

@Data
public class ModRealmsAPI {
    @Getter
    public static ModRealmsAPI instance;
    public JDA JDA;
    public Game sponge;
    public Mongo mongo;
    public Logger logger;
    public DAOManager daoManager;
    public HashMap<String, String> info;

    public ModRealmsAPI(HashMap<String, String> info, @Nullable Game sponge, @Nullable Logger logger,boolean setupMongo,boolean setupJDA){
        this.setInfo(info);
        if(logger != null){
            this.setLogger(logger);
            System.out.println("API: Successfully loaded SLF4J Logger");
        } else System.out.println("API: Logger is not present!");

        if(sponge != null){
            this.setSponge(sponge);
            System.out.println("API: Successfully loaded Sponge");
        } else System.out.println("API: Sponge is not present!");

        if(setupMongo){
            this.setMongo(new Mongo());
            this.setDaoManager(new DAOManager());
            System.out.println("API: Successfully loaded Mongo");
        } else System.out.println("API: Mongo will not be loaded!");

        if(setupJDA){
            JDAConnection.connect();
            this.setJDA(JDAConnection.API);
            System.out.println("API: Successfully loaded JDA");
        } else System.out.println("API: JDA will not be loaded!");
    }

    public JDA getJDA(){
        try{
            return this.JDA;
        } catch(NullPointerException e){
            System.out.println("JDA is not present!");
            return null;
        }
    }

    public Game getSponge(){
        try{
            return this.sponge;
        } catch(NullPointerException e){
            System.out.println("Sponge is not present!");
            return null;
        }
    }

    public Mongo getMongo(){
        try{
            return this.mongo;
        } catch(NullPointerException e){
            System.out.println("Mongo/Morphia is not present!");
            return null;
        }
    }
}
