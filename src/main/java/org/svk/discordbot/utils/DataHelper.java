package org.svk.discordbot.utils;

 /*
# Created by: svk
# Contact: svk#1066 | https://dc.dxsbots.pl
# Class: DataHelper
# Date: 13.08.2022, 11:34
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHelper {


    private static final DateFormat dateFormat;
    private static final Map<Integer, String> values;

    static {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        (values = new LinkedHashMap<Integer, String>(6)).put(2592000, "msc");
        values.put(31104000, "y");
        values.put(86400, "d");
        values.put(3600, "h");
        values.put(60, "min");
        values.put(1, "s");
    }

    public static int convertTime(Long l) {
        int time = (int) ((l - System.currentTimeMillis()) / 1000L);
        return time;
    }

    public static String durationToString(long time, boolean now) {
        if (!now) {
            time -= System.currentTimeMillis();
        }
        if (time < 1L) {
            return "<1s";
        }
        long months = TimeUnit.MILLISECONDS.toDays(time) / 30L;
        long days = TimeUnit.MILLISECONDS.toDays(time) % 30L;
        long hours = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(time));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
        StringBuilder stringBuilder = new StringBuilder();
        if (months > 0L) {
            stringBuilder.append(months).append("msc")
                    .append(" ");
        }
        if (days > 0L) {
            stringBuilder.append(days).append("d")
                    .append(" ");
        }
        if (hours > 0L) {
            stringBuilder.append(hours).append("h")
                    .append(" ");
        }
        if (minutes > 0L) {
            stringBuilder.append(minutes).append("m")
                    .append(" ");
        }
        if (seconds > 0L) {
            stringBuilder.append(seconds).append("s");
        }
        return stringBuilder.length() > 0 ? stringBuilder.toString().trim() : time + "ms";
    }

    public static String getDate(long time) {
        return dateFormat.format(new Date(time));
    }

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            if (time.equalsIgnoreCase("perm")) {
                return -1L;
            }
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for (int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                    if (m.group(1) != null && !m.group(1).isEmpty()) {
                        years = Integer.parseInt(m.group(1));
                    }
                    if (m.group(2) != null && !m.group(2).isEmpty()) {
                        months = Integer.parseInt(m.group(2));
                    }
                    if (m.group(3) != null && !m.group(3).isEmpty()) {
                        weeks = Integer.parseInt(m.group(3));
                    }
                    if (m.group(4) != null && !m.group(4).isEmpty()) {
                        days = Integer.parseInt(m.group(4));
                    }
                    if (m.group(5) != null && !m.group(5).isEmpty()) {
                        hours = Integer.parseInt(m.group(5));
                    }
                    if (m.group(6) != null && !m.group(6).isEmpty()) {
                        minutes = Integer.parseInt(m.group(6));
                    }
                    if (m.group(7) == null) {
                        break;
                    }
                    if (m.group(7).isEmpty()) {
                        break;
                    }
                    seconds = Integer.parseInt(m.group(7));
                    break;
                }
            }
            if (!found) {
                return -1L;
            }
            Calendar c = new GregorianCalendar();
            if (years > 0) {
                c.add(1, years * (future ? 1 : -1));
            }
            if (months > 0) {
                c.add(2, months * (future ? 1 : -1));
            }
            if (weeks > 0) {
                c.add(3, weeks * (future ? 1 : -1));
            }
            if (days > 0) {
                c.add(5, days * (future ? 1 : -1));
            }
            if (hours > 0) {
                c.add(11, hours * (future ? 1 : -1));
            }
            if (minutes > 0) {
                c.add(12, minutes * (future ? 1 : -1));
            }
            if (seconds > 0) {
                c.add(13, seconds * (future ? 1 : -1));
            }
            Calendar max = new GregorianCalendar();
            max.add(1, 10);
            if (c.after(max)) {
                return max.getTimeInMillis();
            }
            return c.getTimeInMillis();
        } catch (Exception e) {
            return -1L;
        }
    }
}
