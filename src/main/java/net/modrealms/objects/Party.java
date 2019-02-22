package net.modrealms.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.api.data.dao.CPlayerDAO;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity("conversify-parties") @Data
public class Party {
    @Id
    @Property("_id")
    private ObjectId id;

    @Property("name")
    private String name;

    private List<UUID> players;

    private UUID leader;

    public Party(){
        //Morhpia Constructor
    }

    public Party(CPlayer leader){
        this.id = new ObjectId();
        this.name = leader.getName() + "'s New Party";
        this.players = new ArrayList<>();
        this.leader = leader.getUuid();
        this.players.add(leader.getUuid());
        leader.setPartyId(this.id);
        ModRealmsAPI.getInstance().getDaoManager().getCPlayerDAO().updatePlayer(leader);
    }
}
