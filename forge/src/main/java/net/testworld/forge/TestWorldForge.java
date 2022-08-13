package net.testworld.forge;

import net.testworld.TestWorld;
import net.minecraftforge.fml.common.Mod;

@Mod(TestWorld.MOD_ID)
public class TestWorldForge {
    public TestWorldForge() {
        TestWorld.init();
    }
}
