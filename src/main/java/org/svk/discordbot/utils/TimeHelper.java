package org.svk.discordbot.utils;

 /*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: TimeHelper
# Date: 13.08.2022, 11:35
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public enum TimeHelper {

    TICK(50, 50),
    MILLISECOND(1, 1),
    SECOND(1000, 1000),
    MINUTE(60000, 60),
    HOUR(3600000, 60),
    DAY(86400000, 24),
    WEEK(604800000, 7);

    public static int MPT = 50;
    public static String sec = "sek";
    public static String min = "min";
    public static String hr = "godz";
    public static String day = "d";
    static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private final int time;
    private final int timeMulti;

    TimeHelper(int time, int timeMulti) {
        this.time = time;
        this.timeMulti = timeMulti;
    }


    private static Integer getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(7);
        return dayOfWeek - 1;
    }

    public static String getRecalculateTime(long millis) {
        if (millis == 0L) {
            return "0";
        }
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        if (days > 0L) {
            millis -= TimeUnit.DAYS.toMillis(days);
        }
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        if (hours > 0L) {
            millis -= TimeUnit.HOURS.toMillis(hours);
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        if (minutes > 0L) {
            millis -= TimeUnit.MINUTES.toMillis(minutes);
        }
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        if (seconds > 0L) {
            millis -= TimeUnit.SECONDS.toMillis(seconds);
        }
        StringBuilder sb = new StringBuilder();
        if (days > 0L) {
            sb.append(days);
            long i = days % 10L;
            if (i == 1L) {
                sb.append("dni");
            } else {
                sb.append("dni");
            }
        }
        if (hours > 0L) {
            sb.append(hours);
            sb.append("g");
        }
        if (minutes > 0L) {
            sb.append(minutes);
            sb.append("m");
        }
        if (seconds > 0L) {
            sb.append(seconds);
            sb.append("s");
        }
        return sb.toString();
    }

    public static String getTime2(long l) {
        if (l < 60L) {
            return l + TimeHelper.sec;
        }
        int minutes = (int) (l / 60L);
        int s = 60 * minutes;
        int secondsLeft = (int) (l - s);
        if (minutes < 60) {
            if (secondsLeft > 0) {
                return minutes + TimeHelper.min + " " + secondsLeft + TimeHelper.sec;
            }
            return minutes + TimeHelper.min;
        } else {
            if (minutes < 1440) {
                String time = "";
                int hours = minutes / 60;
                time = hours + TimeHelper.hr;
                int inMins = 60 * hours;
                int left = minutes - inMins;
                if (left >= 1) {
                    time = time + " " + left + TimeHelper.min;
                }
                if (secondsLeft > 0) {
                    time = time + " " + secondsLeft + TimeHelper.sec;
                }
                return time;
            }
            String time = "";
            int days = minutes / 1440;
            time = days + TimeHelper.day;
            int inMins = 1440 * days;
            int leftOver = minutes - inMins;
            if (leftOver >= 1) {
                if (leftOver < 60) {
                    time = time + " " + leftOver + TimeHelper.min;
                } else {
                    int hours2 = leftOver / 60;
                    time = time + " " + hours2 + TimeHelper.hr;
                    int hoursInMins = 60 * hours2;
                    int minsLeft = leftOver - hoursInMins;
                    if (leftOver >= 1) {
                        time = time + " " + minsLeft + TimeHelper.min;
                    }
                }
            }
            if (secondsLeft > 0) {
                time = time + " " + secondsLeft + TimeHelper.sec;
            }
            return time;
        }
    }

    public int getMulti() {
        return this.timeMulti;
    }

    public int getTime() {
        return this.time;
    }

    public int getTick() {
        return this.time / 50;
    }

    public int getTime(int multi) {
        return this.time * multi;
    }

    public int getTick(int multi) {
        return this.getTick() * multi;
    }
}