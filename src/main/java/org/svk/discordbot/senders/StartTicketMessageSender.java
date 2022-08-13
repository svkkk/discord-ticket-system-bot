package org.svk.discordbot.senders;

 /*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: StartTicketMessageSender
# Date: 13.08.2022, 11:12
 */

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.svk.discordbot.config.Config;

import java.awt.*;

public class StartTicketMessageSender extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] a = e.getMessage().getContentRaw().split(" ");
        EmbedBuilder x = new EmbedBuilder();
        if (a[0].equalsIgnoreCase(Config.commands.sender_command)) {
            e.getMessage().delete().queue();
            x.setTitle(Config.messages.ticket_start_embed_title);
            x.setColor(Color.decode(Config.embedColorAll));
            x.setDescription("*" + Config.messages.ticket_start_embed_description + "*");
            e.getChannel().sendMessageEmbeds(x.build()).setActionRow(Button.primary(Config.buttons.ticket_start_button_id, Config.messages.ticket_start_button_message)).queue();
        }
    }
}
