package org.xenos.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;

public class ColorUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String translateColors(String message) {
        if (message == null) {
            return "";
        }
        message = ColorUtils.translateHexColors(message);
        return ChatColor.translateAlternateColorCodes((char)'&', (String)message);
    }

    private static String translateHexColors(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        while (matcher.find()) {
            String hexColor = matcher.group();
            String translatedColor = ColorUtils.translateHexColor(hexColor);
            message = message.replace(hexColor, translatedColor);
        }
        return message;
    }

    private static String translateHexColor(String hexColor) {
        try {
            String hex = hexColor.substring(1);
            if (ColorUtils.isHexColorSupported()) {
                StringBuilder translatedColor = new StringBuilder("\u00a7x");
                for (char c : hex.toCharArray()) {
                    translatedColor.append("\u00a7").append(c);
                }
                return translatedColor.toString();
            }
            return ColorUtils.getNearestChatColor(hex);
        }
        catch (Exception e) {
            return ChatColor.RED.toString();
        }
    }

    private static boolean isHexColorSupported() {
        try {
            ChatColor.class.getMethod("of", String.class);
            return true;
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static String getNearestChatColor(String hex) {
        try {
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            int brightness = (r + g + b) / 3;
            if (r > g && r > b) {
                return brightness > 128 ? ChatColor.RED.toString() : ChatColor.DARK_RED.toString();
            }
            if (g > r && g > b) {
                return brightness > 128 ? ChatColor.GREEN.toString() : ChatColor.DARK_GREEN.toString();
            }
            if (b > r && b > g) {
                return brightness > 128 ? ChatColor.BLUE.toString() : ChatColor.DARK_BLUE.toString();
            }
            if (brightness > 200) {
                return ChatColor.WHITE.toString();
            }
            if (brightness > 128) {
                return ChatColor.GRAY.toString();
            }
            if (brightness > 64) {
                return ChatColor.DARK_GRAY.toString();
            }
            return ChatColor.BLACK.toString();
        }
        catch (Exception e) {
            return ChatColor.WHITE.toString();
        }
    }

    public static String stripColors(String message) {
        if (message == null) {
            return "";
        }
        message = HEX_PATTERN.matcher(message).replaceAll("");
        return ChatColor.stripColor((String)message);
    }
}
