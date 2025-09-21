package org.blackninja745studios.lightweightholograms;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Hologram {

    ArmorStand summonedEntities;
    int lines = 1;

    public Hologram(Location l, Component n) {

        summonedEntities = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
        summonedEntities.customName(n);
        summonedEntities.setCustomNameVisible(true);
        summonedEntities.setArms(false);
        summonedEntities.setAI(false);
        summonedEntities.setBasePlate(false);
        summonedEntities.setSmall(true);
        summonedEntities.setSilent(true);
        summonedEntities.setHealth(0.1);
        summonedEntities.setInvisible(true);
        summonedEntities.setInvulnerable(true);
        summonedEntities.setCanTick(false);
        summonedEntities.setDisabledSlots(EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HAND,
                EquipmentSlot.LEGS, EquipmentSlot.HEAD, EquipmentSlot.OFF_HAND);
        summonedEntities.setCanMove(false);
        summonedEntities.setGlowing(false);
        summonedEntities.setMarker(true);
        summonedEntities.getPersistentDataContainer().set(new NamespacedKey(LightweightHolograms.plugin, "persistent"),
                PersistentDataType.BYTE, (byte) 1);

    }

    public Hologram(Map<String, Object> datain) {

        this(Location.deserialize((Map<String, Object>) datain.get("location")),
                MiniMessage.miniMessage().deserialize(((String) datain.get("component"))));
        lines = datain.containsKey("lines") ? (int) datain.get("lines") : 0;

    }

    public void addLine(Component c) {

        LightweightHolograms.holograms.add(new Hologram(getNextLineLocation(), c));
        lines++;

    }

    public void destructor() {

        summonedEntities.remove();
        summonedEntities = null;

    }

    public void setName(Component n) {

        this.summonedEntities.customName(n);

    }

    public void moveHere(Location loc) {

        this.summonedEntities.teleport(loc);

    }

    public Location getLocation() {

        return this.summonedEntities.getLocation();

    }

    public String getName() {

        return MiniMessage.miniMessage().serialize(this.summonedEntities.customName());

    }

    private Location getNextLineLocation() {

        return new Location(summonedEntities.getWorld(), summonedEntities.getLocation().getX(),
                summonedEntities.getLocation().getY() - (0.3 * lines), summonedEntities.getLocation().getZ());

    }

    public @NotNull Map<String, Object> serialize() {

        return Map.of("lines", this.lines, "component",
                MiniMessage.miniMessage().serialize(this.summonedEntities.customName()), "location",
                this.summonedEntities.getLocation().serialize());

    }

}
