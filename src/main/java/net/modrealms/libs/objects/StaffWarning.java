package net.modrealms.libs.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

public class StaffWarning {
    @Getter @Setter
    private Date date;
    @Getter @Setter
    private String reason;
    @Reference("given_by") @Getter @Setter
    private StaffMember givenBy;

    public StaffWarning(){
        this.date = new Date();
    }
}
