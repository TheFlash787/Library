package net.modrealms.api.data.dao;

import lombok.Getter;
import lombok.NonNull;
import net.modrealms.api.ModRealmsAPI;
import net.modrealms.objects.BasePlayer;
import net.modrealms.objects.Server;
import net.modrealms.objects.Ticket;
import net.modrealms.objects.WorldLoc;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TicketDAO {
    @Inject
    Logger logger;

    @Getter
    private HashMap<UUID, ObjectId> cachedTickets = new HashMap<>();

    private static final ModRealmsAPI api = net.modrealms.api.ModRealmsAPI.getInstance();
    private static final Datastore datastore = api.getMongo().getDatastore();

    public Ticket createTicket(BasePlayer player,Server server,@Nullable String message,WorldLoc location,Boolean launcher,Boolean bugs,Boolean recipe,Boolean lag,Boolean suggestion,Boolean other) {
        //when using this method, the new instance must be added to the BasePlayer
        Ticket ticket = new Ticket(player, server, message, location, launcher, bugs, recipe, lag, suggestion, other);
        datastore.save(ticket);
        //Tickets.onTicketCreate(ticket);
        return ticket;
    }

    public Optional<Ticket> getTicketById(ObjectId ticketId) {
        Query<Ticket> ticketQuery = datastore.createQuery(Ticket.class).filter("id", ticketId);
        if(ticketQuery.asList().size() == 1){
            return Optional.of(ticketQuery.get());
        }
        return Optional.empty();
    }

    public int getOpenTicketCount(){
        int size = 0;
        size = datastore.createQuery(Ticket.class).filter("status", "open").asList().size();
        return size;
    }
    public int getClosedTicketCount(){
        int size = 0;
        size = datastore.createQuery(Ticket.class).filter("status", "closed").asList().size();
        return size;
    }
    public int getTotalTicketCount(){
        int size = 0;
        size = datastore.createQuery(Ticket.class).asList().size();
        return size;
    }

    public void closeTicket(Ticket ticket){
        ticket.setStatus("closed");
        updateTicket(ticket);
        //Tickets.onTicketClose(ticket);
    }

    public void updateTicket(Ticket ticket) {
        datastore.save(ticket);
    }

    public void deleteTicket(Ticket ticket, @NonNull CommandSource player){
        if(datastore.createQuery(Ticket.class).filter("id", ticket.getId()).asList().size() == 1){
            //Delete from datastore
            datastore.delete(ticket);

            //delete from Creator
            BasePlayer creator = ModRealmsAPI.getInstance().getDaoManager().getBasePlayerDAO().getPlayerById(ticket.getCreatorId()).get();
            creator.removeTicket(ticket);
            ModRealmsAPI.getInstance().getDaoManager().getBasePlayerDAO().updatePlayer(creator);

            //Send Messages
            //Tickets.onTicketDelete(ticket, (Player) player);
            if(player != null){
                player.sendMessage(Text.of(TextColors.GREEN, "You have successfully deleted this ticket from the database."));
            }
        }
        else{
            logger.error("User tried to delete a ticket but it did not exist.");

            if(player != null){
                player.sendMessage(Text.of(TextColors.RED, "Sorry, but this ticket has already been deleted from the database."));
            }
        }
    }
}
