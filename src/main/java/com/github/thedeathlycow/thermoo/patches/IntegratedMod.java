package com.github.thedeathlycow.thermoo.patches;

import net.fabricmc.loader.api.FabricLoader;

public enum IntegratedMod {

    LIBHUD("libhud", "https://modrinth.com/mod/libhud"),
    ARMOR_POINTS_PP("armorpointspp", "https://modrinth.com/mod/armorpoints"),
    COLORFUL_HEARTS("colorfulhearts", "https://modrinth.com/mod/colorful-hearts"),
    OVERFLOWING_BARS("overflowingbars", "https://modrinth.com/mod/overflowing-bars"),
    IMMERSIVE_WEATHERING("immersive_weathering", "https://modrinth.com/mod/immersive-weathering"),
    FABRIC_SEASONS("seasons", "https://modrinth.com/mod/fabric-seasons"),
    SERENE_SEASONS("sereneseasons", "https://modrinth.com/mod/serene-seasons"),
    ORIGINS("origins", "https://modrinth.com/mod/origins"),
    MOB_ORIGINS("moborigins", "https://modrinth.com/mod/moborigins"),
    EXTRA_ORIGINS("extraorigins", "https://modrinth.com/mod/extra-origins");
    private final String id;

    private final String modpage;

    IntegratedMod(String id, String modpage) {
        this.id = id;
        this.modpage = modpage;
    }

    public String getId() {
        return id;
    }

    public String getModpage() {
        return modpage;
    }

    public boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.id, this.modpage);
    }
}
