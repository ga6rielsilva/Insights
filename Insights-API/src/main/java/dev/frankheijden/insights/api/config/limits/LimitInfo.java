package dev.frankheijden.insights.api.config.limits;

import net.kyori.adventure.text.Component;

public class LimitInfo {

    private final Component displayName;
    private final String name;
    private final int limit;

    /**
     * Constructs a new LimitInfo object containing the name of the limit and the actual limit.
     */
    public LimitInfo(String name, int limit) {
        this(Component.text(name), name, limit);
    }

    public LimitInfo(Component displayName, String name, int limit) {
        this.displayName = displayName;
        this.name = name;
        this.limit = limit;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public int getLimit() {
        return limit;
    }
}
