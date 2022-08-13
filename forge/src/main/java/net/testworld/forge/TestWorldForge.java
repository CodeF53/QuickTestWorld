package net.testworld.forge;

import dev.architectury.platform.forge.EventBuses;
import net.testworld.TestWorld;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestWorld.MOD_ID)
public class TestWorldForge {
    public TestWorldForge() {
        EventBuses.registerModEventBus(TestWorld.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        TestWorld.init();
    }
}
