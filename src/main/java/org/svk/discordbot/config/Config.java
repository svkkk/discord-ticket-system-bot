package org.svk.discordbot.config;

 /*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: Config
# Date: 13.08.2022, 11:13
 */

import java.util.ArrayList;

public class Config {
    public static String embedColorAll;
    public static StatusBot statusBot;
    public static NeedRole needRole;
    public static Commands commands;
    public static Buttons buttons;
    public static Messages messages;
    public static Categories categories;

    public Config() {
        embedColorAll = "#5865f2";
        needRole = new NeedRole();
        statusBot = new StatusBot();
        commands = new Commands();
        buttons = new Buttons();
        messages = new Messages();
        categories = new Categories();
    }
    public static class NeedRole {
        public ArrayList<Long> all_perms = new ArrayList<Long>() {{
            add(856191668697038849L); // Role id
        }};
    }
    public static class StatusBot {
        public String[] messages = {"Bot created by svk#1066", "https://dc.dxsbots.pl"}; // Messages in status (Infinite number of messages)
    }
    public static class Commands {
        public String sender_command = "-yd689r57hxtujiru89pdy6jxt5";
    }
    public static class Buttons {
        public String ticket_start_button_id = "ticket-start";
        public String ticket_close_button_id = "ticket-close";
    }
    public static class Messages {
        public String ticket_start_button_message = "✏️Begin ticket";
        public String ticket_start_embed_title = "Write a ticket! ✏️";
        public String ticket_start_embed_description = "If you want to contact the server administration, click the button below and describe your problem! We will try to contact you as soon as possible.";
        public String ticket_created_message = "Your ticket has been created!";
        public String ticket_close_button_message = "Close your ticket \uD83D\uDD12";
        public String ticket_close_message = "The ticket will be deleted in 5 seconds";
    }
    public static class Categories {
        public String tickets_category = "1007947409759932416";
    }
}
