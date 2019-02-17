package net.modrealms.objects;

import lombok.Data;
import xyz.morphia.annotations.Property;

import java.util.Date;

@Data
public class Booster {
    private int hours;
    @Property("start-date")
    private Date startDate;
    @Property("duration-days")
    private int duration;

    public Booster(){
        // Morphia Constructor
    }

    public Booster(int hours, int days){
        this.hours = hours;
        this.duration = days;
    }
}
