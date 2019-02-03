package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import xyz.morphia.annotations.Reference;

import java.util.Date;

public class BrowniePoint {
    @Getter
    @Setter
    private Date date;
    @Getter @Setter
    private String reason;
    @Reference("given_by") @Getter @Setter
    private StaffMember givenBy;

    public BrowniePoint(){
        this.date = new Date();
    }
}
