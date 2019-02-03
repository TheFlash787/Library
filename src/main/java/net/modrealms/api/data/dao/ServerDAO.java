package net.modrealms.api.data.dao;

import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.Server;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.Optional;

public class ServerDAO {
    private static final ModRealmsAPI api = net.modrealms.api.ModRealmsAPI.getInstance();
    private static final Datastore datastore = api.getMongo().getDatastore();

    public Optional<Server> getServerById(ObjectId objectId) {
        Query<Server> serverQuery = datastore.createQuery(Server.class).filter("_id", objectId);
        if(serverQuery.asList().size() == 1) {
            Server updatedServer = serverQuery.get();
            return Optional.of(updatedServer);
        }
        return Optional.empty();
    }

    public Optional<Server> getServer(String serverName) {

        Query<Server> serverQuery = datastore.createQuery(Server.class).field("server_name").equalIgnoreCase(serverName);
        if(serverQuery.asList().size() == 1) {
            Server updatedServer = serverQuery.get();
            return Optional.of(updatedServer);
        }
        return Optional.empty();
    }

    public Server newServer() {
        //we dont update the server here as that will be done in the login logic
        return new Server();
    }

    public void updateServer(Server server) {
        //MRLogger.DEBUG("Updating "+ server.getServername()+" to database and cache list");
        datastore.save(server);
    }
}
