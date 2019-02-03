package net.modrealms.objects;

import lombok.Data;
import xyz.morphia.annotations.Embedded;

import java.util.Date;

@Data
public class BoughtKit {
    private Date date;
    @Embedded
    private OrbKit kit;

    public BoughtKit(){

    }

    public BoughtKit(OrbKit kit){
        this.date = new Date();
        this.kit = kit;
    }
}
