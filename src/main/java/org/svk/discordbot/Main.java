package org.svk.discordbot;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: Main
# Date: 13.08.2022, 11:07
 */

public class Main implements EventListener {

    public static void main(final String[] a) throws InterruptedException {
        new DiscordBot();
    }

    public void onEvent(GenericEvent e) {
        if (e instanceof ReadyEvent)
            System.out.println("\n\n\n\n\nBot has been turned on!");
    }
}