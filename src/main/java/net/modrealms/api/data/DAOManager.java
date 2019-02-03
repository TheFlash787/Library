package net.modrealms.api.data;

import lombok.Data;
import net.modrealms.api.data.dao.*;
import net.modrealms.objects.LoafPlayer;
import xyz.morphia.dao.DAO;

@Data
public class DAOManager {
    private BasePlayerDAO basePlayerDAO;
    private LoafDAO loafDAO;
    private LoafPlayerDAO loafPlayerDAO;
    private OrbKitDAO orbKitDAO;
    private ProgressDAO progressDAO;
    private RestrictionDAO restrictionDAO;
    private ServerDAO serverDAO;
    private TicketDAO ticketDAO;

    public DAOManager(){
        this.setBasePlayerDAO(new BasePlayerDAO());
        this.setLoafDAO(new LoafDAO());
        this.setLoafPlayerDAO(new LoafPlayerDAO());
        this.setOrbKitDAO(new OrbKitDAO());
        this.setProgressDAO(new ProgressDAO());
        this.setRestrictionDAO(new RestrictionDAO());
        this.setServerDAO(new ServerDAO());
        this.setTicketDAO(new TicketDAO());
    }
}
