package net.testworld.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.testworld.TestWorld;

public class TestWorldFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TestWorld.init();
    }
}
