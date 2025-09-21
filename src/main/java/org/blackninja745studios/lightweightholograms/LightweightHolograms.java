package org.blackninja745studios.lightweightholograms;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public final class LightweightHolograms extends JavaPlugin implements Listener {

    public static LightweightHolograms plugin;
    public static List<Hologram> holograms = new ArrayList<>();

    public static String version = "1.3";
    public static boolean outdated;

    @Override
    public void onEnable() {

        plugin = this;

        getLogger().log(Level.INFO, "Attempting to load holograms from the config.");
        try {

            if (getConfig().isSet("hologram")) {

                List<Map<String, Object>> holoIn = (List<Map<String, Object>>) getConfig().getList("hologram");
                for (Map<String, Object> holo : holoIn) {

                    holograms.add(new Hologram(holo));

                }

            }

        } catch (Exception ignored) {

            getLogger().log(Level.SEVERE, "Unable to read data from the config!");

        }

        getLogger().log(Level.INFO, "Loaded " + holograms.size() + " from the config.");

        getServer().getCommandMap().register("holograms", new HologramCommand());

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().log(Level.INFO, "Successfully loaded!");

        if (!getConfig().isSet("check-versions"))
            getConfig().set("check-versions", true);
        saveConfig();

        if (getConfig().getBoolean("check-versions"))
            outdated = checkUpdates();

    }

    @Override
    public void onDisable() {

        List<Map<String, Object>> out = new ArrayList<>();
        holograms.forEach(i -> out.add(i.serialize()));
        getConfig().set("hologram", out);
        saveConfig();
        holograms.forEach(i -> {

            i.destructor();

        });
        getLogger().log(Level.INFO, "Successfully cleaned up!");

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (getConfig().getBoolean("check-versions")
                && (e.getPlayer().isOp() || e.getPlayer().hasPermission("holograms.command")) && outdated)
        {

            e.getPlayer()
                    .sendMessage(appendPluginPrefix(Component.text(
                            "Warning! This version of LightweightHolograms is outdated! (" + version + ")",
                            NamedTextColor.WHITE)));
            e.getPlayer().sendMessage(Component
                    .text("For latest performance and security updates, please download the latest version ",
                            NamedTextColor.WHITE)
                    .append(Component.text("here", NamedTextColor.WHITE)
                            .clickEvent(ClickEvent.openUrl("https://modrinth.com/plugin/lightweightholograms"))
                            .decorate(TextDecoration.UNDERLINED))
                    .append(Component.text("!", NamedTextColor.WHITE)));

        }

    }

    public static Component appendPluginPrefix(Component c) {

        return Component.text("[", NamedTextColor.DARK_GRAY).append(Component.text("Lightweight", NamedTextColor.GREEN))
                .append(Component.text("Holograms", NamedTextColor.BLUE))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY)).append(c);

    }

    public boolean checkUpdates() {

        getLogger().log(Level.INFO, "Checking version... (You can disable this in config.yml)");
        try {

            URL url = new URL("https://api.github.com/repos/MSKatKing/LightweightHolograms/releases/latest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            String response = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();

            JSONObject assets = (JSONObject) (new JSONParser().parse(response));

            if (!assets.isEmpty()) {

                String latestVersion = (String) assets.get("tag_name");

                String[] latest = latestVersion.split("\\.");
                String[] current = version.split("\\.");

                int length = Math.max(latest.length, current.length);

                for (int i = 0; i < length; i++) {

                    int part1 = i < latest.length ? Integer.parseInt(latest[i]) : 0;
                    int part2 = i < current.length ? Integer.parseInt(current[i]) : 0;

                    if (part2 > part1) {

                        getLogger().log(Level.WARNING,
                                "This version is ahead of the latest version (v" + latestVersion
                                        + "), which means it is a dev version (v" + version
                                        + "). Please proceed with caution!");
                        return false;

                    }

                    if (part1 > part2) {

                        getLogger().log(Level.INFO, "New version found! (" + latestVersion
                                + ") Don't forget to download it at https://modrinth.com/plugin/lightweightholograms!");
                        return true;

                    }

                }

            }

            getLogger().log(Level.INFO, "No new updates found!");

        } catch (Exception e) {

            getLogger().log(Level.WARNING,
                    "Unable to check for updates! Please consult https://modrinth.com/plugin/lightweightholograms to see if there is a later version available.");
            getLogger().log(Level.WARNING, e.getMessage());

        }

        return false;

    }

}
