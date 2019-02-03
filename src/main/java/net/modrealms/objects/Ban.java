package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import net.modrealms.api.util.TimeParser;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity("punishments-bans")
public class Ban {
    @Getter
    @Setter
    @Id
    private ObjectId id;

    @Getter @Setter
    private String player;

    @Getter @Setter
    private String staff;

    @Getter @Setter
    private String date;

    @Getter @Setter
    private String expiration_date;

    @Getter @Setter
    private String ip;

    @Getter @Setter
    private String reason;

    @Getter @Setter
    private String server;

    @Getter @Setter
    private Boolean active;

    public Ban(){

    }

    public Ban(String reason, String staff, String player, @Nullable String duration, @Nullable String ip){
        SimpleDateFormat format = new SimpleDateFormat();
        this.id = new ObjectId();
        this.player = player;
        this.date = format.format(new Date());
        if(duration != null){
            Calendar calendar = Calendar.getInstance();
            long milli = TimeParser.toMilliSec(duration);
            format.setCalendar(calendar);
            calendar.add(Calendar.MILLISECOND, (int) milli);
            this.expiration_date = format.format(calendar.getTime());
        }
        if(ip != null){
            this.ip = ip;
        }
        this.reason = reason;
        this.staff = staff;
        this.active = true;
    }

    public boolean isTemporary(){
        return this.expiration_date != null;
    }
}
