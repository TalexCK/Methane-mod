package com.modrinth.methane;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "methane")
public class MethaneSettings implements ConfigData {
    //TODO: we should transition to translatable comments (@Comment(Text.translatable("YOUR.THING.HERE")))


    @Comment("auto-magically darkens the world at night!")
    public boolean dynamicShading = true;

    @Comment("Time between shading rebuilds with Dynamic Shading, has a *tiny* performance impact. 0 = realtime, which has a large chunk loading performance impact. Requires a restart to take effect.")
    public int rebuildSeconds = 20;

    @Comment("Methane's initial state. (You should set this to 'Yes' to stop some bugs from happening)")
    public boolean modstate = true;

    @Comment("Render the status messages on the HUD instead of chat?")
    public boolean hudrender = false;

    @Comment("Use the old Methane lighting 'engine', has a smaller performance boost, but can fix compatibility with some mods and a few bugs.")
    public boolean useOldLightingEngine = false;

    public boolean disableToasts = true;

    @Comment("Enable or disable fog effects.")
    @ConfigEntry.Gui.CollapsibleObject
    public FogSettings fogSettings = new FogSettings();

    public static class FogSettings{
        @Comment("Toggle whether to keep fog settings even with Methane disabled.")
        public  boolean persistFogSettings = false;
        public boolean disableAirFog = false; // the fog pass that obscures terrain
        public boolean disableWaterFog = false; // the fog layer that tints everything blue
        public  boolean disableLavaFog = false; // the thing that tints everything orange
        public  boolean disablePowderedSnowFog = false; // pretty self-explanatory

        public  boolean disableThickFog = false; // Nether Fog pass
        @Comment("The fog that covers terrain in")
        public  boolean disableSkyFog = false; // I think this is another fog pass
    }

    @Comment("Can be used to greatly improve performance, at the cost of some visual features.")
    @ConfigEntry.Gui.CollapsibleObject
    public DestructiveSettings destructiveSettings = new DestructiveSettings();

    //@Comment("The default world brightness value (15 default and effective max)")
    //public double brightness = 1000; // unused for now because of a ton of issues

    public static class DestructiveSettings{

        @Comment("Whether or not we calculate rainfall in biomes (breaks a lot of rain effects, but has performance benefits)")
        public  boolean destructiveweatheroptimizations = false;

        @Comment("Forcefully deletes weather.")
        public boolean DestroyWeather;

        @Comment("Does nothing if sodium is installed")
        public boolean RenderLayerSkips;

        @Comment("Removes ALL screen backgrounds for a performance boost.")
        public boolean DestroyScreens;

    }


}
