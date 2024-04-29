# Thermoo Patches

Thermoo Patches provides compatibility patches and integrations for [Thermoo](https://github.com/TheDeathlyCow/thermoo/) based mods, including [Frostiful](https://github.com/TheDeathlyCow/frostiful/) and [Scorchful](https://github.com/TheDeathlyCow/scorchful/), and other non-Thermoo mods where it would make sense for them to have Thermoo integration.

# List of Patches

Thermoo Patches is still a WIP. The following patches are currently planned:
- [x] Armor Points++ / libhud: Temperature now properly displays on HUD, temperature percent also shown next to health text.
- [x] Colorful Hearts: Temperature now properly displays on HUD.
- [x] Overflowing Bars: Temperature now properly displays on HUD.
- [x] Fabric Seasons: Makes Frostiful/Scorchful aware of current season.
- [ ] Serene Seasons: Makes Frostiful/Scorchful aware of current season (coming to 1.20.4+ only).
- [x] Immersive Weathering: Eating Icicles and Ice Sickles cools the player.
- [ ] Spell Engine
- [x] Ad Astra: Adds more extreme temperatures on various planets, and disables the normal temperature effects
- [x] Origins: Blazeborn is immune to heat and vulnerable to cold.
- [x] Mob Origins: Snow Golem is immune to cold and vulnerable to heat. Snow Golem temperature system also removed (redundant with Thermoo).
- [x] Extra Origins: Piglin origin has extra heat resistance

Note that other temperature mods, like EnvironmentZ and Tough as Nails are not currently planned. This is because these mods are largely redundant with Frostiful and Scorchful installed, so unfortunately I don't consider adding them to be a very worthwhile use of my time. Feel free to PR these (or make your own separate patch mod) if you want them.  

# Contributing 

If you want a patch for Thermoo to be added, please either create an issue or submit a pull request! The most important thing for any patch is that they MUST be optional. If the mod they are patching for is not loaded, then nothing should crash or break. The only mods that are required by Thermoo Patches are Fabric API, Cloth Config, and, of course, Thermoo. 