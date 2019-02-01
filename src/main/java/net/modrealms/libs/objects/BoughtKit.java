package net.modrealms.libs.objects;

import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;

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
