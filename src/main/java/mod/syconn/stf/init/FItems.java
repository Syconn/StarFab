package mod.syconn.stf.init;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.item.lightsaber.Lightsaber;
import mod.syconn.stf.item.lightsaber.customize.LColor;
import mod.syconn.stf.item.lightsaber.customize.LSettings;
import mod.syconn.stf.item.lightsaber.customize.LType;
import mod.syconn.stf.util.LightsaberHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FItems {

    public static Lightsaber LIGHTSABER = new Lightsaber(new LSettings(LType.VADER, LColor.BLUE, 9, -1.6f, true));

    public static void registerItems(){
        register("l_saber", LIGHTSABER);
    }

    private static void register(String name, Item item){
        Registry.register(Registry.ITEM, new Identifier(StarMain.ID, name), item);
    }

    public static void registerColors(){
        // DO AN IF TO CHECK TINTINDEX
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> LightsaberHelper.getSettings(stack).getColor().convert().getSignColor(), LIGHTSABER);
    }

    public static void registerOverrides(){
        FabricModelPredicateProviderRegistry.register(LIGHTSABER, new Identifier("handle"), (stack, world, entity, seed) -> LightsaberHelper.getSettings(stack).getType().getId() / 10.0F);
        FabricModelPredicateProviderRegistry.register(LIGHTSABER, new Identifier("mode"), (stack, world, entity, seed) -> {
            if (entity == null){
                return 0.0F;
            }
            return LightsaberHelper.getSettings(stack).isActivated() ? 1.0F : 0.0F;
        });
    }
}
