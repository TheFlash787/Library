package net.modrealms.objects;

import com.flowpowered.math.vector.Vector3d;
import lombok.Data;
import net.modrealms.api.ModRealmsAPI;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

@Data
public class WorldLoc {
    private int chunkX;
    private int chunkZ;
    private String world;
    private int x;
    private int y;
    private int z;

    public WorldLoc(){

    }

    public WorldLoc(Location<World> location){
        this.world = location.getExtent().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.chunkX = (int) Math.floor(x / 16.00);
        this.chunkZ = (int) Math.floor(z / 16.00);
    }

    public Vector3d get3d(){
        return new Vector3d(this.x, this.y, this.z);
    }

    public Location<World> getLocation(){
        return new Location<>(ModRealmsAPI.getInstance().getSponge().getServer().getWorld(this.world).get(),get3d());
    }
}
