# Thermoo Patches

[![](https://raw.githubusercontent.com/TheDeathlyCow/scorchful/main/docs/try_frostiful.svg)](https://modrinth.com/mod/frostiful)

[![](https://raw.githubusercontent.com/TheDeathlyCow/scorchful/main/docs/try_scorchful.svg)](https://modrinth.com/mod/scorchful)

<a href="https://modrinth.com/mod/thermoo">
<img src="https://i.imgur.com/MjlOmH0.png" alt="Requires Thermoo badge" width="300"/>
</a>

Have trouble? Get help on Discord: https://discord.thedeathlycow.com

Thermoo Patches provides compatibility patches and integrations for [Thermoo](https://github.com/TheDeathlyCow/thermoo/)
based mods, including [Frostiful](https://github.com/TheDeathlyCow/frostiful/)
and [Scorchful](https://github.com/TheDeathlyCow/scorchful/), and other non-Thermoo mods where it would make sense for
them to have Thermoo integration.

# List of Patches

The following patches are currently either implemented, or planned.

| Mod name                                                                                           | Patch Description                                                                                                                                                                                                                    | Implemented versions                          |
|----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|
| [Friends & Foes](https://modrinth.com/mod/friends-and-foes)                                        | The Iceologer's Ice Chunk attack and the Totem of Freezing freeze victims through Thermoo's system; Iceologer is cold immune and Wildfire is tagged as benefiting from heat; Iceologer has max temp of 0; Wildfire has min temp of 0 | 1.21.1, 1.21.6-11, 26.1.x                     |
| [Overflowing Bars](https://modrinth.com/mod/overflowing-bars)                                      | Temperature now properly displays on HUD                                                                                                                                                                                             | 1.20.1, 1.21.1, 1.21.6-11, 26.1.x             |
| [Serene Seasons](https://modrinth.com/mod/serene-seasons)                                          | Makes Frostiful/Scorchful aware of current season (including tropical seasons, with a summer dry season).                                                                                                                            | 1.20.1[^1], 1.20.4, 1.21.1, 1.21.6-11, 26.1.x |
| [Homeostatic Seasons](https://modrinth.com/mod/homeostatic-seasons)                                | Makes Frostiful/Scorchful aware of current season (with tropical season support for wet seasons in temperate biomes).                                                                                                                | 26.1.x                                        |
| [Nycto](https://modrinth.com/mod/nycto)                                                            | Makes Vampires immune to negative cold temperature statuses.                                                                                                                                                                         | 26.1.x                                        |
| [Origins](https://modrinth.com/mod/origins)                                                        | Provides powers for integrating Thermoo temperatures with Origins. Blazeborn Origin ignores the effects of heat, but is extremely vulnerable to cold.                                                                                | 1.21.1                                        |
| [Mob Origins](https://modrinth.com/mod/moborigins)                                                 | Snow Golem is empowered by cold, but melts when warm. Snow Golem temperature system also removed (redundant with Thermoo).                                                                                                           | 1.20.1, 1.20.4                                | 
| [Extra Origins](https://modrinth.com/mod/extra-origins)                                            | Piglin origin has extra heat resistance                                                                                                                                                                                              | 1.20.1, 1.20.4, 1.21.1                        |
| [Fabric Seasons](https://modrinth.com/mod/fabric-seasons)                                          | Makes Frostiful/Scorchful aware of current season (including tropical seasons, with a summer dry season).                                                                                                                            | 1.20.1[^1], 1.21.1                            |
| [Colorful Hearts](https://modrinth.com/mod/colorful-hearts)                                        | Temperature now properly displays on HUD.                                                                                                                                                                                            | 1.20.1, 1.20.4, 1.21.1                        |
| [Simple Seasons](https://modrinth.com/mod/simple-seasons)                                          | Makes Frostiful/Scorchful aware of current season (including tropical seasons, with a summer wet season).                                                                                                                            | 1.21.1                                        |
| [Stellaris](https://modrinth.com/mod/stellaris)                                                    | Set the temperature of each planet to be close to how they should be in real life, and gives Space Suits proper environmental resistances.                                                                                           | 1.21.1                                        |
| [Armor Points++](https://modrinth.com/mod/armorpoints) & [libhud](https://modrinth.com/mod/libhud) | Temperature now properly displays on HUD, temperature percent also shown next to health text. Requires libhud to work!                                                                                                               | 1.20.1 only                                   |
| [Immersive Weathering](https://modrinth.com/mod/immersive-weathering)                              | Eating Icicles and Ice Sickles cools the player.                                                                                                                                                                                     | 1.20.1 only                                   |
| [Ad Astra](https://modrinth.com/mod/ad-astra)                                                      | Adds more extreme temperatures on various planets, and disables the normal temperature effects.                                                                                                                                      | 1.20.1, 1.20.4                                |

---

Note that other temperature mods, like EnvironmentZ and Tough as Nails are not currently planned. This is because these
mods are largely redundant with Frostiful and Scorchful installed, so unfortunately I don't consider adding them to be a
very worthwhile use of my time. Feel free to PR these (or make your own separate patch mod) if you want them.

---

[^1]: Seasons integration for 1.20.1 does not support Tropical Seasons.

# Contributing

If you want a patch for Thermoo to be added, please either create an issue or submit a pull request! The most important
thing for any patch is that they MUST be optional. If the mod they are patching for is not loaded, then nothing should
crash or break. The only mods that are required by Thermoo Patches are Fabric API, YACL, and, of course, Thermoo.

# Origins Powers and Integration

Thermoo Patches provides a few powers and datapack facilities for mod pack authors to use in Origins. Some of these
powers are integrated onto some origins (see the specific patch descriptions above), but this is also meant to be used
as something of an API for working with Thermoo+Origins in your mod packs.

**Important:** The way that these powers are patched onto origins is different, depending on your game version. In
1.20.x, these are patched by simply overriding the datapack entry (just like you would do) with a `loading_priority`
of `1000`. However, as of 1.21.1, this is now done dynamically in Java code, and will *only apply if the origin JSON
file is being supplied by the Origins mod itself*. Origins added by any addon or datapack will not have these powers
applied unless you add them.

It may be useful to have a read through the [Thermoo Wiki](https://thermoo.thedeathlycow.com) to better understand what
these powers do, as things such as temperature changes and Frost/Heat Resistance are better defined there.

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

Some powers are provided under the name space `thermoo-patches-origins-patch`. These powers should be considered
internal to Thermoo Patches and are not a stable API. However, you may copy them into your own datapacks under the terms
of the GNU LGPL.

| Predicate ID                 | Description                                                                                                       |
|------------------------------|-------------------------------------------------------------------------------------------------------------------|
| `thermoo-patches:can_freeze` | Whether the target is affected by negative temperature effects from cold (use instead of `frostiful:can_freeze`). | 
| `thermoo-patches:can_heat`   | Whether the target is affected by negative temperature effects from heat (use instead of `scorchful:can_heat`).   | 

# Additional Credits

* [Stellaris Icon](https://github.com/st0x0ef/stellaris/blob/f7fd8393196106091d208d83cb93d26b9c29aa83/fabric/src/main/resources/logo.png),
  by st0x0ef used under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.en).
* [Simple Seasons Icon](https://github.com/steves-underwater-paradise/simple-seasons/blob/ccdb19626986d771f4ef980cf913f5d1972cb1d9/src/main/resources/assets/simple_seasons/icon.png),
  by Steveplays28, used under [LGPL-3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html).
* [Fabric Seasons Icon](https://github.com/lucaargolo/fabric-seasons/blob/e0b2dd285e5a7f0b2850871ea7a0eb5289d0937c/src/main/resources/assets/seasons/icon.png),
  by lucaargolo, used under [MPL 2.0](https://www.mozilla.org/en-US/MPL/2.0/).
* [Colorful Hearts Icon](https://github.com/Terrails/colorful-hearts/blob/5c0a4a4ba913b2c0d6efc1cb6a32f52b3c82881f/common/src/main/resources/icon.png),
  by Terrails, used under
  the [MIT license](https://github.com/Terrails/colorful-hearts/blob/5c0a4a4ba913b2c0d6efc1cb6a32f52b3c82881f/LICENSE).
* [Friends & Foes Icon](https://github.com/Faboslav/friends-and-foes/blob/a82fbdb25f377df3c9a4c3a962c5376ff96a5e5c/common/src/main/resources/icon.png),
  by faboslav, used under [CC-BY-NC-ND-4.0](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.en) (no changes
  made).
* [Origins Icon](https://github.com/apace100/origins-fabric/blob/80bf80530e6ecb1dbd317d7d5f44cec9d3b79b7e/src/main/resources/assets/origins/icon.png),
  by Apace, used under
  the [MIT license](https://github.com/Terrails/colorful-hearts/blob/5c0a4a4ba913b2c0d6efc1cb6a32f52b3c82881f/LICENSE).
* [Immersive Weathering Icon](https://github.com/AstralOrdana/Immersive-Weathering/blob/1bcc03b6754d6840f3b3ab328744af16e06bdb9a/fabric/src/main/resources/icon.png),
  by Ordana, used under [LGPL-3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html).
* [Armor Points++ Icon](https://github.com/Cheos137/ArmorpointsPlusplus/blob/cdf7dbed9bd078af687a893ce0264a24c552a997/core/src/main/resources/icon.png),
  by Cheos, used under
  the [MIT license](https://github.com/Terrails/colorful-hearts/blob/5c0a4a4ba913b2c0d6efc1cb6a32f52b3c82881f/LICENSE).
* [Homeostatic Seasons Icon](https://github.com/wendall911/HomeostaticSeasons/blob/ca7389a1fc55b9bf239e9ff32daf8517575a0305/Common/src/main/resources/homeostaticseasons_icon.png),
  by Wendall Cada (wendall911), used under
  the [MIT license](https://github.com/wendall911/HomeostaticSeasons/blob/ca7389a1fc55b9bf239e9ff32daf8517575a0305/LICENSE).
