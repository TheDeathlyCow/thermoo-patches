# Thermoo Patches

Thermoo Patches provides compatibility patches and integrations for [Thermoo](https://github.com/TheDeathlyCow/thermoo/) based mods, including [Frostiful](https://github.com/TheDeathlyCow/frostiful/) and [Scorchful](https://github.com/TheDeathlyCow/scorchful/), and other non-Thermoo mods where it would make sense for them to have Thermoo integration.

# List of Patches

Thermoo Patches is still a WIP. The following patches are currently planned:
- [x] Armor Points++ / libhud
- [ ] Serene Seasons 
- [x] Immersive Weathering
- [ ] Spell Engine 
- [ ] Ad Astra
- [ ] Origins (and some of its major add-ons)

The following patches are currently included in Thermoo itself, but will be moved to this mod in 1.20.2+:
- [ ] Colorful Hearts
- [ ] Overflowing Bars
- [x] Fabric Seasons

Note that other temperature mods, like EnvironmentZ and Tough as Nails are not currently planned. This is because these mods are largely redundant with Frostiful and Scorchful installed, so unfortunately I don't consider adding them to be a very worthwhile use of my time. Feel free to PR these (or make your own separate patch mod) if you want them.  

# Contributing 

If you want a patch for Thermoo to be added, please either create an issue or submit a pull request! The most important thing for any patch is that they MUST be optional. If the mod they are patching for is not loaded, then nothing should crash or break. The only mods that are required by Thermoo Patches are Fabric API, Cloth Config, and, of course, Thermoo. 