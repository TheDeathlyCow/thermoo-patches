# Thermoo Patches

[![](https://raw.githubusercontent.com/TheDeathlyCow/scorchful/main/docs/try_frostiful.svg)](https://modrinth.com/mod/frostiful)

[![](https://raw.githubusercontent.com/TheDeathlyCow/scorchful/main/docs/try_scorchful.svg)](https://modrinth.com/mod/scorchful)

<a href="https://modrinth.com/mod/thermoo">
<img src="https://i.imgur.com/MjlOmH0.png" alt="Requires Thermoo badge" width="300"/>
</a>

Have trouble? Get help on Discord: https://discord.gg/aqASuWebRU

Thermoo Patches provides compatibility patches and integrations for [Thermoo](https://github.com/TheDeathlyCow/thermoo/)
based mods, including [Frostiful](https://github.com/TheDeathlyCow/frostiful/)
and [Scorchful](https://github.com/TheDeathlyCow/scorchful/), and other non-Thermoo mods where it would make sense for
them to have Thermoo integration.

# List of Patches

The following patches are currently either implemented, or planned.

| Mod name                                                                                           | Patch Description                                                                                                                         | Implemented versions   |
|----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|------------------------|
| [Armor Points++](https://modrinth.com/mod/armorpoints) & [libhud](https://modrinth.com/mod/libhud) | Temperature now properly displays on HUD, temperature percent also shown next to health text. Requires libhud to work!                    | ⚠️ 1.20.1 only         |
| [Colorful Hearts](https://modrinth.com/mod/colorful-hearts)                                        | Temperature now properly displays on HUD.                                                                                                 | ✅ 1.20.1, 1.20.4, 1.21 |
| [Overflowing Bars](https://modrinth.com/mod/overflowing-bars)                                      | Temperature now properly displays on HUD                                                                                                  | ⚠️ 1.20.1 only         |
| [Fabric Seasons](https://modrinth.com/mod/fabric-seasons)                                          | Makes Frostiful/Scorchful aware of current season (does not include tropical seasons).                                                    | ⚠️ 1.20.1 only         |
| [Serene Seasons](https://modrinth.com/mod/serene-seasons)                                          | Makes Frostiful/Scorchful aware of current season (including tropical seasons).                                                           | ✅ 1.20.4, 1.21         |
| [Immersive Weathering](https://modrinth.com/mod/immersive-weathering)                              | Eating Icicles and Ice Sickles cools the player.                                                                                          | ⚠️ 1.20.1 only         |
| [Origins](https://modrinth.com/mod/origins) (Thermoo Patches 3.2+)                                 | Provides powers for integrating Thermoo temperatures with Origins. However, integration with specific origins is left to modpack authors. | ✅ 1.21                 |

- ✅ indicates the patch is available for the latest version of Thermoo Patches
- ⚠️ indicates that the mod the patch is for has not (yet) been updated to the latest version of Minecraft

Note that other temperature mods, like EnvironmentZ and Tough as Nails are not currently planned. This is because these
mods are largely redundant with Frostiful and Scorchful installed, so unfortunately I don't consider adding them to be a
very worthwhile use of my time. Feel free to PR these (or make your own separate patch mod) if you want them.

### Removed Patches

These patches were previously part of Thermoo Patches, but were either substantially changed or removed.

| Mod name                                                           | Patch Description                                                                                                          | Implemented versions | Removal reason                                                                                     | 
|--------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|----------------------|----------------------------------------------------------------------------------------------------|
| [Ad Astra](https://modrinth.com/mod/ad-astra)                      | Adds more extreme temperatures on various planets, and disables the normal temperature effects.                            | 1.20.1, 1.20.4       | Mod was discontinued                                                                               | 
| [Origins](https://modrinth.com/mod/origins) (Thermoo Patches 3.1-) | Blazeborn is empowered by heat, but freezes when even a little cold.                                                       | 1.20.1, 1.20.4, 1.21 | Only origin powers are provided, integration with specific origins is now left to modpack authors. |
| [Mob Origins](https://modrinth.com/mod/moborigins)                 | Snow Golem is empowered by cold, but melts when warm. Snow Golem temperature system also removed (redundant with Thermoo). | 1.20.1, 1.20.4       | Only origin powers are provided, integration with specific origins is now left to modpack authors. |
| [Extra Origins](https://modrinth.com/mod/extra-origins)            | Piglin origin has extra heat resistance                                                                                    | 1.20.1, 1.20.4       | Only origin powers are provided, integration with specific origins is now left to modpack authors. |

# Contributing

If you want a patch for Thermoo to be added, please either create an issue or submit a pull request! The most important
thing for any patch is that they MUST be optional. If the mod they are patching for is not loaded, then nothing should
crash or break. The only mods that are required by Thermoo Patches are Fabric API, Cloth Config, and, of course,
Thermoo.

# Origins Powers

Thermoo Patches provides a few powers and datapack facilities for Modpack authors to use in Origins. However,
integrating these powers into
specific origins (like Blazeborn or Snow Golem) is left to you, as I have no idea what other mods you may want to
integrate with Origins.

It may be useful to have a read through the [Thermoo Wiki](https://github.com/TheDeathlyCow/thermoo/wiki) to better
understand what these powers do, as things such as temperature changes and Frost/Heat Resistance are better defined
there.

| Power ID                                 | Description                                                                                                                       | 
|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `thermoo-patches:cold_immune`            | Adds a very large amount of Frost Resistance to affected entites, making them effectively immune to all cold temperature changes. |
| `thermoo-patches:ignores_cold_effects`   | Makes the affected entity immune to Frostiful's *negative* temperature effects                                                    |
| `thermoo-patches:cold_vulnerability`     | Doubles the amount of freezing that affected entities receive from cold temperature changes.                                      |
| `thermoo-patches:extra_frost_resistance` | Gives affected entities 50% resistance to cold temperature changes                                                                |
| `thermoo-patches:heat_immune`            | Adds a very large amount of Heat Resistance to affected entites, making them effectively immune to all warm temperature changes.  |
| `thermoo-patches:ignores_heat_effects`   | Makes the affected entity immune to Scorchful's *negative* temperature effects                                                    |
| `thermoo-patches:heat_vulnerability`     | Doubles the amount of heating that affected entities receive from warm temperature changes.                                       |
| `thermoo-patches:extra_heat_resistance`  | Gives affected entities 50% resistance to warm temperature changes                                                                |
