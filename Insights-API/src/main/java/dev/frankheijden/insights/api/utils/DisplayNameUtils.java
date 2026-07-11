package dev.frankheijden.insights.api.utils;

import dev.frankheijden.insights.api.objects.wrappers.ScanObject;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import java.util.EnumMap;
import java.util.Map;

/**
 * Resolves the display name (Component) of materials, entities and scan objects,
 * using Adventure translatable keys so the name appears in the player's language.
 */
public final class DisplayNameUtils {

    private static final Map<Material, Component> MATERIAL_CACHE = new EnumMap<>(Material.class);
    private static final Map<EntityType, Component> ENTITY_CACHE = new EnumMap<>(EntityType.class);

    static {
        for (Material material : Material.values()) {
            MATERIAL_CACHE.put(material, buildMaterial(material));
        }
        for (EntityType entityType : EntityType.values()) {
            ENTITY_CACHE.put(entityType, buildEntity(entityType));
        }
    }

    private DisplayNameUtils() {}

    /**
     * Resolves the display name of a generic ScanObject, delegating to
     * {@link #of(Material)} or {@link #of(EntityType)} whenever possible.
     */
    public static Component of(ScanObject<?> scanObject) {
        Object object = scanObject.getObject();
        if (object instanceof Material material) {
            return of(material);
        } else if (object instanceof EntityType entityType) {
            return of(entityType);
        }
        return Component.text(String.valueOf(object));
    }

    /**
     * Resolves the translatable display name of a {@link Material}, falling back
     * to {@link EnumUtils#pretty(Material)} if the client doesn't recognize the key.
     */
    public static Component of(Material material) {
        return MATERIAL_CACHE.get(material);
    }

    /**
     * Resolves the translatable display name of an {@link EntityType}, falling back
     * to {@link EnumUtils#pretty(EntityType)} if the client doesn't recognize the key.
     */
    public static Component of(EntityType entityType) {
        return ENTITY_CACHE.get(entityType);
    }

    /**
     * Builds the translatable Component for a Material. Some Material values (legacy
     * materials) have no NamespacedKey and throw on getKey() - those fall back to plain text.
     */
    private static Component buildMaterial(Material material) {
        try {
            String prefix = material.isBlock() ? "block" : "item";
            return translatableComponent(prefix, material.getKey().getNamespace(), material.getKey().getKey(), EnumUtils.pretty(material));
        } catch (IllegalArgumentException ex) {
            return Component.text(EnumUtils.pretty(material));
        }
    }

    /**
     * Builds the translatable Component for an EntityType. EntityType.UNKNOWN (and possibly
     * others) has no NamespacedKey and throws on getKey() - those fall back to plain text.
     */
    private static Component buildEntity(EntityType entityType) {
        try {
            return translatableComponent("entity", entityType.getKey().getNamespace(), entityType.getKey().getKey(), EnumUtils.pretty(entityType));
        } catch (IllegalArgumentException ex) {
            return Component.text(EnumUtils.pretty(entityType));
        }
    }

    private static Component translatableComponent(String prefix, String namespace, String key, String fallback) {
        return Component.translatable(prefix + '.' + namespace + '.' + key, fallback);
    }
}