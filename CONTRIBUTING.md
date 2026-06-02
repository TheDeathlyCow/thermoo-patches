# Thermoo Patches development guidelines

This is just a basic set of guidelines for contributing to Thermoo Patches. Thank you for contributing to Thermoo Patches! If you are making a contribution for the first time, feel free to add your name to the list of contributors in the `fabric.mod.json` file for the mod and for any modules you contribute to before making a pull request. 

## Translations

Thermoo Patches is a multimodule project, essentially a "mod of mods". Each integration patch is a separate mod contained within the larger Thermoo Patches mod, and not all of them have things that need to be translated. However, there are a couple, and their default translation files can be found in the following locations:

* [Thermoo Patches Base](./thermoo-patches-base/src/main/resources/assets/thermoo-patches-base/lang/en_us.json) - The config file translations.
* [Origins Patch](./thermoo-patches-origins-patch/src/main/resources/assets/thermoo-patches-origins-patch/lang/en_us.json) - Names of the Origins Powers.

## New Patches

If you want to create a new patch for Thermoo Patches, here is how to do it: 

Set up tasks:
- First create a folder named something like `thermoo-patches-{patched mod ID}-patch`
- Include a basic `build.gradle` and import the mod from Maven using either the mod's official maven or Modrinth Maven as a `modCompileOnly` dependency.  
- If needed, also depend on the `thermoo-patches-base` module in the new module.
- Create a custom mappings file in `{module}/mappings/yarn-custom.tiny`
    - For most mods, you can simply copy from [the base custom mappings file](./mappings/yarn-custom.tiny) and leave it empty. This is only needed if importing a mod that uses Official Mappings and has some method name that conflicts with yarn, such as [Armor Points++](./thermoo-patches-armorpointspp-patch/mappings/yarn-custom.tiny).
- Include the module in [`settings.gradle`](./settings.gradle)
- Add and include the module to the base dependencies in [`build.gradle`](./build.gradle)

Documentation tasks:
- Create the `fabric.mod.json` file, set the proper mod ID for the module, and make it suggest the patched not, DO NOT MAKE IT REQUIRED!
- Include the [mod icon](./src/main/resources/assets/thermoo-patches/icon.png) in the module's assets folder (prefer to use the patched mod's icon if said mod's license allows it).
- Add the mod to the [`IntegratedMod` enum](./thermoo-patches-base/src/main/java/com/github/thedeathlycow/thermoo/patches/IntegratedMod.java) in the base module. 
- Add the mod as an optional dependency in the publishing section of [`build.gradle`](./build.gradle).
- Add patch to the [README list of patches](README.mdist-of-patches).

> [!NOTE]
> Thermoo Patches requires that all patched mods be OPTIONAL installations. This means that any resources should only load when the patched mod is present (often this means using `fabric:load_conditions`), and checking and Java-level calls with `isModLoaded()`.  

### Tips

- It may be easy to use another patch module as reference. The [Friends & Foes module](./thermoo-patches-friendsandfoes-patch) is a good example for most simple patches of content mods.
- If you need to mixin to other mods, use a mixin Plugin. The Friends & Foes module has one that can be copy/pasted into the new module verbatim.
