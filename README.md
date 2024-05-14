# Thermoo Patches

[![](https://raw.githubusercontent.com/TheDeathlyCow/scorchful/main/docs/try_frostiful.svg)](https://modrinth.com/mod/frostiful)

[![](https://raw.githubusercontent.com/TheDeathlyCow/scorchful/main/docs/try_scorchful.svg)](https://modrinth.com/mod/scorchful)

<a href="https://modrinth.com/mod/thermoo">
<img src="https://i.imgur.com/MjlOmH0.png" alt="Requires Thermoo badge" width="300"/>
</a>

Thermoo Patches provides compatibility patches and integrations for [Thermoo](https://github.com/TheDeathlyCow/thermoo/)
based mods, including [Frostiful](https://github.com/TheDeathlyCow/frostiful/)
and [Scorchful](https://github.com/TheDeathlyCow/scorchful/), and other non-Thermoo mods where it would make sense for
them to have Thermoo integration.

# List of Patches

The following patches are currently either implemented, or planned.

| Mod name                                                                                           | Patch Description                                                                                                                                     | Implemented versions |
| -------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------- |
| [Armor Points++](https://modrinth.com/mod/armorpoints) & [libhud](https://modrinth.com/mod/libhud) | Temperature now properly displays on HUD, temperature percent also shown next to health text. ‼️ **IMPORTANT** ‼️ LIBHUD IS REQUIRED FOR ARMOR POINTS++ | ⚠️ 1.20.1 only        |
| [Colorful Hearts](https://modrinth.com/mod/colorful-hearts)                                        | Temperature now properly displays on HUD.                                                                                                             | ✅ 1.20.1, 1.20.4     |
| [Overflowing Bars](https://modrinth.com/mod/overflowing-bars)                                      | Temperature now properly displays on HUD                                                                                                              | ⚠️ 1.20.1 only        |
| [Fabric Seasons](https://modrinth.com/mod/fabric-seasons)                                          | Makes Frostiful/Scorchful aware of current season.                                                                                                    | ⚠️ 1.20.1 only        |
| [Serene Seasons](https://modrinth.com/mod/serene-seasons)                                          | Makes Frostiful/Scorchful aware of current season (coming to 1.20.4+ only).                                                                           | ❌ TODO 1.20.4        |
| [Immersive Weathering](https://modrinth.com/mod/immersive-weathering)                              | Eating Icicles and Ice Sickles cools the player.                                                                                                      | ⚠️ 1.20.1 only        |
| [Ad Astra](https://modrinth.com/mod/ad-astra)                                                      | Adds more extreme temperatures on various planets, and disables the normal temperature effects.                                                       | ✅ 1.20.1, 1.20.4     |
| [Origins](https://modrinth.com/mod/origins)                                                        | Blazeborn is immune to heat and vulnerable to cold.                                                                                                   | ⚠️ 1.20.1 only        |
| [Mob Origins](https://modrinth.com/mod/moborigins)                                                 | Snow Golem is immune to cold and vulnerable to heat. Snow Golem temperature system also removed (redundant with Thermoo).                             | ⚠️ 1.20.1 only        |
| [Extra Origins](https://modrinth.com/mod/extra-origins)                                            | Piglin origin has extra heat resistance                                                                                                               | ⚠️ 1.20.1 only        |

Note that other temperature mods, like EnvironmentZ and Tough as Nails are not currently planned. This is because these
mods are largely redundant with Frostiful and Scorchful installed, so unfortunately I don't consider adding them to be a
very worthwhile use of my time. Feel free to PR these (or make your own separate patch mod) if you want them.

# Contributing

If you want a patch for Thermoo to be added, please either create an issue or submit a pull request! The most important
thing for any patch is that they MUST be optional. If the mod they are patching for is not loaded, then nothing should
crash or break. The only mods that are required by Thermoo Patches are Fabric API, Cloth Config, and, of course,
Thermoo. 