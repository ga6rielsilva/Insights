package dev.frankheijden.insights.api.utils;

import dev.frankheijden.insights.api.objects.wrappers.ScanObject;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * Resolves the display name (Component) of materials, entities and scan objects,
 * using Adventure translatable keys so the name appears in the player's language.
 */
public final class DisplayNameUtils {

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
        String prefix = material.isBlock() ? "block" : "item";
        return translatableComponent(prefix, material.getKey().getNamespace(), material.getKey().getKey(), EnumUtils.pretty(material));
    }

    /**
     * Resolves the translatable display name of an {@link EntityType}, falling back
     * to {@link EnumUtils#pretty(EntityType)} if the client doesn't recognize the key.
     */
    public static Component of(EntityType entityType) {
        return translatableComponent("entity", entityType.getKey().getNamespace(), entityType.getKey().getKey(), EnumUtils.pretty(entityType));
    }

    private static Component translatableComponent(String prefix, String namespace, String key, String fallback) {
        return Component.translatable(prefix + '.' + namespace + '.' + key, fallback);
    }
}