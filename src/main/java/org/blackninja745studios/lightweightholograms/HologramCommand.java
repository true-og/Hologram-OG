package org.blackninja745studios.lightweightholograms;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HologramCommand extends Command {

    List<String> operations = List.of("create", "edittext", "movehere", "delete", "list", "addline");

    public HologramCommand() {

        super("holograms", "The command for creating and managing holograms.", "/hologram <operation> <id> [text]",
                List.of("hg", "holo"));

    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player) {

            Player p = (Player) commandSender;
            if (!p.hasPermission("holograms.command") && !p.isOp()) {

                p.sendMessage(LightweightHolograms.appendPluginPrefix(
                        Component.text("You don't have permission to use this command!", NamedTextColor.RED)));
                return true;

            }

            if (args.length >= 2) {

                switch (args[0]) {

                    case "create": {

                        String text = "";
                        for (int i = 1; i < args.length; i++) {

                            if (!args[i].equals("--nominimessage"))
                                text += args[i] + " ";

                        }

                        text = text.substring(0, text.length() - 1);
                        if (Arrays.stream(args).toList().contains("--nominimessage")) {

                            LightweightHolograms.holograms.add(new Hologram(p.getLocation(),
                                    LegacyComponentSerializer.legacyAmpersand().deserialize(text)));
                            p.sendMessage(LightweightHolograms.appendPluginPrefix(Component.text(
                                    "Warning! The old chat formatting in outdated! Please consider learning mini message!",
                                    NamedTextColor.YELLOW)));

                        } else {

                            LightweightHolograms.holograms
                                    .add(new Hologram(p.getLocation(), MiniMessage.miniMessage().deserialize(text)));

                        }

                        p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                Component.text("Successfully created a hologram!", NamedTextColor.GREEN)));
                        return true;

                    }
                    case "edittext": {

                        if (args.length >= 3) {

                            String text = "";
                            for (int i = 2; i < args.length; i++) {

                                if (!args[i].equals("--nominimessage"))
                                    text += args[i] + " ";

                            }

                            text = text.substring(0, text.length() - 1);
                            try {

                                if (Arrays.stream(args).toList().contains("--nominimessage")) {

                                    LightweightHolograms.holograms.get(Integer.parseInt(args[1]))
                                            .setName(LegacyComponentSerializer.legacyAmpersand().deserialize(text));
                                    p.sendMessage(LightweightHolograms.appendPluginPrefix(Component.text(
                                            "Warning! The old chat formatting in outdated! Please consider learning mini message!",
                                            NamedTextColor.YELLOW)));

                                } else {

                                    LightweightHolograms.holograms.get(Integer.parseInt(args[1]))
                                            .setName(MiniMessage.miniMessage().deserialize(text));

                                }

                                p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                        Component.text("Successfully updated the hologram " + args[1] + "'s text!",
                                                NamedTextColor.GREEN)));

                            } catch (NumberFormatException ignored) {

                                p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                        Component.text("\"" + args[1] + "\" is not a number!", NamedTextColor.RED)));

                            } catch (IndexOutOfBoundsException ignored) {

                                p.sendMessage(LightweightHolograms.appendPluginPrefix(Component
                                        .text("\"" + args[1] + "\" is not a valid hologram!", NamedTextColor.RED)));

                            }

                        } else {

                            p.sendMessage(LightweightHolograms
                                    .appendPluginPrefix(Component.text("Not enough arguments!", NamedTextColor.RED)));

                        }

                        return true;

                    }
                    case "movehere": {

                        try {

                            LightweightHolograms.holograms.get(Integer.parseInt(args[1])).moveHere(p.getLocation());
                            p.sendMessage(LightweightHolograms
                                    .appendPluginPrefix(Component.text("Successfully moved the hologram " + args[1]
                                            + "'s to " + p.getLocation().toString() + "!", NamedTextColor.GREEN)));

                        } catch (NumberFormatException ignored) {

                            p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                    Component.text("\"" + args[1] + "\" is not a number!", NamedTextColor.RED)));

                        } catch (IndexOutOfBoundsException ignored) {

                            p.sendMessage(LightweightHolograms.appendPluginPrefix(Component
                                    .text("\"" + args[1] + "\" is not a valid hologram!", NamedTextColor.RED)));

                        }

                        return true;

                    }
                    case "delete": {

                        try {

                            LightweightHolograms.holograms.get(Integer.parseInt(args[1])).destructor();
                            LightweightHolograms.holograms.remove(Integer.parseInt(args[1]));
                            p.sendMessage(LightweightHolograms.appendPluginPrefix(Component
                                    .text("Successfully deleted the hologram " + args[1] + "!", NamedTextColor.GREEN)));

                        } catch (NumberFormatException ignored) {

                            p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                    Component.text("\"" + args[1] + "\" is not a number!", NamedTextColor.RED)));

                        } catch (IndexOutOfBoundsException ignored) {

                            p.sendMessage(LightweightHolograms.appendPluginPrefix(Component
                                    .text("\"" + args[1] + "\" is not a valid hologram!", NamedTextColor.RED)));

                        }

                        return true;

                    }
                    case "addline": {

                        if (args.length >= 3) {

                            String text = "";
                            for (int i = 2; i < args.length; i++) {

                                if (!args[i].equals("--nominimessage"))
                                    text += args[i] + " ";

                            }

                            text = text.substring(0, text.length() - 1);
                            try {

                                if (Arrays.stream(args).toList().contains("--nominimessage")) {

                                    LightweightHolograms.holograms.get(Integer.parseInt(args[1]))
                                            .addLine(LegacyComponentSerializer.legacyAmpersand().deserialize(text));
                                    p.sendMessage(LightweightHolograms.appendPluginPrefix(Component.text(
                                            "Warning! The old chat formatting in outdated! Please consider learning mini message!",
                                            NamedTextColor.YELLOW)));

                                } else {

                                    LightweightHolograms.holograms.get(Integer.parseInt(args[1]))
                                            .addLine(MiniMessage.miniMessage().deserialize(text));

                                }

                                p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                        Component.text("Successfully added a new line to hologram " + args[1] + "!",
                                                NamedTextColor.GREEN)));

                            } catch (NumberFormatException ignored) {

                                p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                        Component.text("\"" + args[1] + "\" is not a number!", NamedTextColor.RED)));

                            } catch (IndexOutOfBoundsException ignored) {

                                p.sendMessage(LightweightHolograms.appendPluginPrefix(Component
                                        .text("\"" + args[1] + "\" is not a valid hologram!", NamedTextColor.RED)));

                            }

                        } else {

                            p.sendMessage(LightweightHolograms
                                    .appendPluginPrefix(Component.text("Not enough arguments!", NamedTextColor.RED)));

                        }

                        return true;

                    }
                    default: {

                        p.sendMessage(LightweightHolograms.appendPluginPrefix(
                                Component.text("\"" + args[0] + "\" is not a valid argument!", NamedTextColor.RED)));

                    }

                }

            } else if (args.length == 1 && args[0].equals("list")) {

                for (int i = 0; i < LightweightHolograms.holograms.size(); i++) {

                    Hologram h = LightweightHolograms.holograms.get(i);
                    p.sendMessage(LightweightHolograms
                            .appendPluginPrefix(Component.text("Hologram #" + i + ": ", NamedTextColor.GREEN)
                                    .append(Component.text(
                                            "Location: (" + h.getLocation().getX() + ", " + h.getLocation().getY()
                                                    + ", " + h.getLocation().getZ() + "), Text: " + h.getName(),
                                            NamedTextColor.WHITE))));

                }

            } else {

                p.sendMessage(LightweightHolograms
                        .appendPluginPrefix(Component.text("Not enough arguments!", NamedTextColor.RED)));

            }

        } else {

            commandSender.sendMessage("Only players can execute this command! (Console support coming in the future!)");

        }

        return true;

    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias,
            @NotNull String[] args)
    {

        if (!sender.hasPermission("holograms.command") && !sender.isOp())
            return List.of();
        switch (args.length) {

            case 1:
                return operations.stream().filter(i -> i.startsWith(args[0])).toList();
            case 2: {

                switch (args[0]) {

                    case "edittext":
                    case "movehere":
                    case "addline":
                    case "delete": {

                        List<String> out = new ArrayList<>();
                        for (int i = 0; i < LightweightHolograms.holograms.size(); i++)
                            out.add(String.valueOf(i));
                        return out;

                    }
                    case "create":
                        return List.of("<place text here>");
                    default:
                        return List.of();

                }

            }
            case 3: {

                switch (args[0]) {

                    case "addline":
                    case "edittext":
                        return List.of("<place text here>");
                    default:
                        return List.of();

                }

            }
            default:
                return List.of();

        }

    }

}
