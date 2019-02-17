package net.modrealms.objects;

import lombok.Data;

import java.util.Date;

@Data
public class Booster {
    private int hours;
    private Date date;

    public Booster(int hours){
        this.hours = hours;
        this.date = new Date();
    }
}
