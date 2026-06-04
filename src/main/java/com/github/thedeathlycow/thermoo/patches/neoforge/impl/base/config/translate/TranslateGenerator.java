package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.config.translate;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.util.StringRepresentable;

import java.lang.reflect.Field;

public final class TranslateGenerator {
    public static <T> void generateConfigOptionTranslations(
            ConfigClassHandler<T> handler,
            FabricLanguageProvider.TranslationBuilder builder
    ) {
        final String prefix = Translate.prefixKey(handler);

        for (Field field : handler.configClass().getDeclaredFields()) {
            SerialEntry entry = field.getAnnotation(SerialEntry.class);
            if (entry == null) {
                continue;
            }

            Translate.Name nameData = field.getAnnotation(Translate.Name.class);
            String nameKey = configOption(prefix, field.getName());

            if (nameData != null) {
                builder.add(nameKey, nameData.value());
            } else {
                throw new IllegalStateException("Option name missing for" + nameKey);
            }

            String comment = entry.comment();
            String commentKey = commentKey(prefix, field.getName());

            if (comment != null && !comment.isEmpty()) {
                builder.add(commentKey, comment);
            } else if (field.getAnnotation(Translate.NoComment.class) == null) {
                throw new IllegalStateException("Missing comment or @NoComment marker for " + commentKey);
            }
        }
    }

    public static <E extends Enum<E> & StringRepresentable> void generateConfigEnumTranslations(
            FabricLanguageProvider.TranslationBuilder builder,
            Class<E> enumClass,
            String... names
    ) {
        E[] entries = enumClass.getEnumConstants();
        if (entries.length != names.length) {
            throw new IllegalStateException(
                    "Names array length %d is different from enums array length %d"
                            .formatted(names.length, entries.length)
            );
        }

        for (E entry : enumClass.getEnumConstants()) {
            String key = "yacl3.config.enum.%s.%s".formatted(enumClass.getSimpleName(), entry.getSerializedName());
            builder.add(key, names[entry.ordinal()]);
        }
    }

    private static String configOption(String prefix, String name) {
        return prefix + "." + name;
    }

    private static String commentKey(String prefix, String name) {
        return configOption(prefix, name) + ".desc";
    }

    private TranslateGenerator() {

    }
}