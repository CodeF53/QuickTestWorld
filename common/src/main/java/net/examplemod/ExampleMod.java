package net.examplemod;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ExampleMod {
    public static final String MOD_ID = "examplemod";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    // Registering a new creative tab
    public static final CreativeModeTab EXAMPLE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "example_tab"), () ->
            new ItemStack(ExampleMod.EXAMPLE_ITEM.get()));
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Properties().tab(ExampleMod.EXAMPLE_TAB)));
    
    public static void init() {
        ITEMS.register();
        
        System.out.println(ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());

        #if POST_CURRENT_MC_1_16_5 && MC_1_19
        System.out.println("Is post 1.16.5 and is 1.19");
        #elif POST_CURRENT_MC_1_16_5
        System.out.println("Is 1.16.5 or post 1.16.5");
        #elif POST_MC_1_16_5
        System.out.println("Is post 1.16.5");
        #elif PRE_MC_1_18_2
        System.out.println("Is pre 1.18.2");
        #elif PRE_CURRENT_MC_1_18_2
        System.out.println("Is 1.18.2 or pre 1.18.2");
        #endif
    }
}
