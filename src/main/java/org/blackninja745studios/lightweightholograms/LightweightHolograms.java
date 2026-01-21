package org.blackninja745studios.lightweightholograms;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        saveConfig();

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

    public static Component appendPluginPrefix(Component c) {

        return Component.text("[", NamedTextColor.DARK_GRAY).append(Component.text("Lightweight", NamedTextColor.GREEN))
                .append(Component.text("Holograms", NamedTextColor.BLUE))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY)).append(c);

    }

}
