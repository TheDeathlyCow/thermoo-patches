package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.compat;

import dev.yumi.mc.core.api.YumiMods;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Plugin implements IMixinConfigPlugin {
    public static final Logger LOGGER = LoggerFactory.getLogger(Plugin.class);

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean apply = checkRequiredModsAnnotation(targetClassName, mixinClassName);

        if (YumiMods.get().isDevelopmentEnvironment()) {
            if (apply) {
                LOGGER.info("Mixin was applied: {}->{}", mixinClassName, targetClassName);
            } else {
                LOGGER.info("Mixin was NOT applied as @RequiredMods check failed: {}->{}", mixinClassName, targetClassName);
            }

        }

        return apply;
    }

    private static boolean checkRequiredModsAnnotation(String targetClassName, String mixinClassName) {
        try {
            var reader = new ClassReader(mixinClassName);
            var classNode = new ClassNode();
            reader.accept(classNode, 0);
            final String requiresModsName = RequiresMods.class.descriptorString();

            for (AnnotationNode annotationNode : classNode.invisibleAnnotations) {
                if (annotationNode.desc.equals(requiresModsName)) {
                    @SuppressWarnings("unchecked")
                    List<String> modIds = (List<String>) annotationNode.values.get(1);

                    if (!allModsLoaded(modIds)) {
                        return false;
                    }
                }
            }

        } catch (IOException e) {
            LOGGER.error("Error checking mixin required mods", e);
        }

        return true;
    }

    private static boolean allModsLoaded(List<String> modIds) {
        for (String modId : modIds) {
            if (!YumiMods.get().isModLoaded(modId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}