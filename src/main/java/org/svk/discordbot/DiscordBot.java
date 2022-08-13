package org.svk.discordbot;

 /*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: DiscordBot
# Date: 13.08.2022, 11:07
 */

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.svk.discordbot.config.Config;
import org.svk.discordbot.senders.StartTicketMessageSender;
import org.svk.discordbot.tasks.TicketTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Getter
@Setter
public class DiscordBot {

    public Config config;
    private static JDA jda;

    private final ScheduledExecutorService executorService;

    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(4);

    public DiscordBot() throws InterruptedException {
        executorService = Executors.newScheduledThreadPool(10);
        jda = createUser();
        if (jda == null) return;
        config = new Config();
    }

    JDA createUser() {
        JDABuilder jda = JDABuilder.createDefault("");
        jda.addEventListeners(
                // Main
                new Main(),
                // Senders
                new StartTicketMessageSender(),
                // Tasks
                new TicketTask());

        jda.setStatus(OnlineStatus.ONLINE);
        jda.setMemberCachePolicy(MemberCachePolicy.ONLINE);
        jda.setChunkingFilter(ChunkingFilter.ALL);
        jda.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES);
        jda.enableCache(CacheFlag.ACTIVITY);
        jda.setRateLimitPool(SCHEDULED_THREAD_POOL_EXECUTOR);
        try {
            return jda.build().awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
            return this.jda;
        }
    }

    public static JDA getJda() {
        return jda;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

}
