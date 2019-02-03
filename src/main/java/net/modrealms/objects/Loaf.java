package net.modrealms.objects;

import com.flowpowered.math.vector.Vector3i;
import lombok.Data;
import xyz.morphia.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity(value = "loaf-loaders", noClassnameStored = true)
public class Loaf {
    @Embedded
    private WorldLoc locationData;
    private int range;
    @Id
    @Property("uuid")
    private UUID uniqueId;
    private Date creationDate;
    private boolean isOffline;
    private List<Vector3i> chunks;
    @Reference
    private LoafPlayer owner;
    @Reference
    private Server server;

    public Loaf(){}

    public Loaf(WorldLoc loc, LoafPlayer player, Server server, int range){
        this.uniqueId = UUID.randomUUID();
        this.locationData = loc;
        this.range = range;
        this.owner = player;
        this.server = server;
        this.isOffline = false;
        this.creationDate = new Date();
        this.chunks = new ArrayList<>();
    }

    public Boolean contains(Vector3i vector) {
        return locationData.getX() - range <= vector.getX() && vector.getX() <= locationData.getX() + range && locationData.getZ() - range <= vector.getZ() && vector.getZ() <= locationData.getZ() + range;
    }

    public Boolean contains(int chunkX, int chunkZ) {
        return locationData.getX() - range <= chunkX && chunkX <= locationData.getX() + range && locationData.getZ() - range <= chunkZ && chunkZ <= locationData.getZ() + range;
    }
}