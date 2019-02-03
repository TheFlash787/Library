package net.modrealms.api.jda;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.modrealms.api.ModRealmsAPI;

import javax.security.auth.login.LoginException;

public class JDAConnection {
    public static JDA API;
    public static void connect(){
        try {
            API = new JDABuilder(AccountType.BOT).setToken(ModRealmsAPI.getInstance().getInfo().get("token")).build().awaitReady();
        } catch(LoginException| IllegalArgumentException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
