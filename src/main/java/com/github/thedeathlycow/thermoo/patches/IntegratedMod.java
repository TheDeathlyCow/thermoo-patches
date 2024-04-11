package com.github.thedeathlycow.thermoo.patches;

import net.fabricmc.loader.api.FabricLoader;

public enum IntegratedMod {

    LIBHUD("libhud", "https://modrinth.com/mod/libhud"),
    ARMOR_POINTS_PP("armorpointspp", "https://modrinth.com/mod/armorpoints"),
    IMMERSIVE_WEATHERING("immersive_weathering", "https://modrinth.com/mod/immersive-weathering"),
    FABRIC_SEASONS("seasons", "https://modrinth.com/mod/fabric-seasons");

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
