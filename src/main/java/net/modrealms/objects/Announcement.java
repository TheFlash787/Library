package net.modrealms.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;

@Entity("conversify-announcements")
public class Announcement {

    @Getter
    @Setter
    @Id
    private ObjectId id;

    @Getter
    @Setter
    @Property("alert-message")
    private String message;

    @Getter
    @Setter
    @Property("url")
    private String url;

    @Setter
    private List<String> blacklist;

    public Announcement(){

    }

    public Announcement(String message){
        this.id = new ObjectId();
        this.message = message;
        this.url = "";
        this.blacklist = new ArrayList<>();
    }

    public List<String> getBlacklist(){
        if(this.blacklist == null){
            this.blacklist = new ArrayList<>();
        }
        return this.blacklist;
    }

}
