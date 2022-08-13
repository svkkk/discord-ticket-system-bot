package org.svk.discordbot.tasks;

 /*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: TicketStartTask
# Date: 13.08.2022, 11:33
 */

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.svk.discordbot.config.Config;
import org.svk.discordbot.utils.DataHelper;
import org.svk.discordbot.utils.TimeHelper;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TicketTask extends ListenerAdapter {

    private final HashMap<User, Long> nextUsage = new HashMap<>();

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        if (e.getComponentId().equalsIgnoreCase(Config.buttons.ticket_start_button_id)) {
            if (!nextUsage.containsKey(e.getUser())) nextUsage.put(e.getUser(), 10L);
            if (nextUsage.get(e.getUser()) > System.currentTimeMillis()) {
                e.reply("`#` An error has occurred! Wait more:: **" + DataHelper.durationToString(nextUsage.get(e.getUser()) - System.currentTimeMillis(), true)+"**").setEphemeral(true).queue();
                return;
            }
            nextUsage.put(e.getUser(), System.currentTimeMillis() + TimeHelper.MINUTE.getTime());
            TextInput nick = TextInput.create("ticket-nick", "Nick", TextInputStyle.SHORT)
                    .setPlaceholder("Your nickname...")
                    .setMinLength(3)
                    .setMaxLength(24)
                    .setRequired(true)
                    .build();
            TextInput problem = TextInput.create("ticket-problem", "Problem", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Write why you would like to contact us.")
                    .setMinLength(16)
                    .setMaxLength(512)
                    .setRequired(true)
                    .build();
            Modal ticket = Modal.create("ticket", "Ticket " + e.getMember().getUser().getAsTag())
                    .addActionRows(ActionRow.of(nick), ActionRow.of(problem))
                    .build();
            e.replyModal(ticket).queue();
        }
        if (e.getComponentId().equalsIgnoreCase(Config.buttons.ticket_close_button_id)) {
            e.getChannel().delete().queueAfter(5L, TimeUnit.SECONDS);
            e.reply(Config.messages.ticket_close_message).queue();
        }
    }
    @Override
    public void onModalInteraction(@Nonnull ModalInteractionEvent e) {
        EmbedBuilder x = new EmbedBuilder();
        if (e.getModalId().equals("ticket")) {
            String nick = e.getValue("ticket-nick").getAsString();
            String body = e.getValue("ticket-problem").getAsString();
            String name = "ticket-" + e.getUser().getAsTag();
            e.reply(Config.messages.ticket_created_message).setEphemeral(true).queue();
            e.getMember().getUser().openPrivateChannel().submit()
                    .thenCompose(channel -> channel.sendMessage(Config.messages.ticket_created_message).setActionRow(Button.secondary("sended", "Message sended from " + e.getGuild().getName()).asDisabled()).submit())
                    .whenComplete((message, error) -> {
                    });
            TextChannel ticketChannel = e.getGuild().getCategoryById(Config.categories.tickets_category).createTextChannel(name).complete();
            ticketChannel.upsertPermissionOverride(e.getMember()).setAllowed(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND).queue();
            ticketChannel.getManager().setSlowmode(5).complete();
            x.setTitle("Ticket " + e.getMember().getUser().getAsTag());
            x.setColor(Color.decode(Config.embedColorAll));
            x.addField("User", "`#` Nickname: `" + nick + "`\n`#` Discord mention: " + e.getUser().getAsMention(), false);
            x.addField("Content of ticket", "`#` " + body, false);
            ticketChannel.sendMessageEmbeds(x.build()).setActionRow(Button.danger(Config.buttons.ticket_close_button_id, Config.messages.ticket_close_button_message)).queue();
            x.clear();
        }
    }
}