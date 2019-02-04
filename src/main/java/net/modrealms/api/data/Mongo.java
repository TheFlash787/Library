package net.modrealms.api.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.Getter;
import net.modrealms.api.ModRealmsAPI;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;
import xyz.morphia.mapping.DefaultCreator;

import java.util.logging.Level;

public class Mongo {
    @Getter
    private Morphia morphia;
    @Getter
    private Datastore datastore;

    public Mongo() {
        connect();
    }

    private void connect() {
        // details
        String user = ModRealmsAPI.getInstance().getInfo().get("mongo_user");
        String pass = ModRealmsAPI.getInstance().getInfo().get("mongo_pass");
        String host = ModRealmsAPI.getInstance().getInfo().get("mongo_host");
        String db = ModRealmsAPI.getInstance().getInfo().get("mongo_db");
        MongoClientURI uri = new MongoClientURI("mongodb://" + user + ":" + pass + "@" + host + ":27017/" + db + "?authSource=admin");

        MongoClient mongoClient = new MongoClient(uri);
        Morphia morphia = new Morphia();

        morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
            @Override
            protected ClassLoader getClassLoaderForClass() {
                return ModRealmsAPI.class.getClassLoader();
            }
        });
        morphia.getMapper().getOptions().setMapSubPackages(true);

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("net.modrealms.objects");
//        morphia.mapPackage("net.modrealms.pistoncore.Objects.mods");
//        morphia.map(EnderStorageContainer.class);
        //morphia.mapPackage("net.modrealms."+pistonCore.getCoreConfiguration().getPluginType().toString().toLowerCase()+".Objects");

        this.morphia = morphia;

        // create the Datastore connecting to the default port on the local host
        this.datastore = morphia.createDatastore(mongoClient, "modrealms");
        this.datastore.ensureIndexes();
    }
}
