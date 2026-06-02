package com.github.thedeathlycow.thermoo.patches.neoforge;

import dev.yumi.mc.core.api.YumiMods;
import net.minecraft.resources.ResourceLocation;

public enum IntegratedMod {
    SERENE_SEASONS("sereneseasons", "https://modrinth.com/mod/serene-seasons");

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
        return YumiMods.get().isModLoaded(id);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.id, this.modpage);
    }

    public ResourceLocation createID(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.id, path);
    }
}