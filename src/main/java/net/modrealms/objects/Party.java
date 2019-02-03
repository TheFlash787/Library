package net.modrealms.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.modrealms.conversify.Conversify;
import net.modrealms.conversify.mongo.CPlayerDAO;
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

    private static CPlayerDAO cPlayerDAO = Conversify.getInstance().getDaoManager().getCPlayerDAO();

    public Party(){
        //Morhpia Constructor
    }

    public Party(UUID leader){
        this.id = new ObjectId();
        this.name = cPlayerDAO.getPlayer(leader).get().getName()+"'s Party";
        this.players = new ArrayList<>();
        this.leader = leader;
        this.players.add(leader);
        CPlayer cLeader = cPlayerDAO.getPlayer(leader).get();
        cLeader.setPartyId(this.id);
        cPlayerDAO.updatePlayer(cLeader);
    }

}
