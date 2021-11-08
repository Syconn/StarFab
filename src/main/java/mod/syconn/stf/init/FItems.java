package mod.syconn.stf.init;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.items.Hilts;
import mod.syconn.stf.items.KyberCrystal;
import mod.syconn.stf.items.lightsaber.Lightsaber;
import mod.syconn.stf.items.lightsaber.customize.LColor;
import mod.syconn.stf.items.lightsaber.customize.LSettings;
import mod.syconn.stf.items.lightsaber.customize.LType;
import mod.syconn.stf.items.tools.FPickaxe;
import mod.syconn.stf.util.helpers.LightsaberHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FItems {

    public static final Lightsaber LIGHTSABER = new Lightsaber(new LSettings(LType.VADER, 9, -1.6f, true, null));
    public static final KyberCrystal KYBER = new KyberCrystal();
    public static final ToolItem HAMMER = new FPickaxe(ToolMaterials.STONE, 1, -2.8F, (new Item.Settings()).group(ItemGroup.TOOLS));
    public static final Hilts HILTS = new Hilts();

    public static void registerItems(){
        register("l_saber", LIGHTSABER);
        register("kyber_crystal", KYBER);
        register("hammer", HAMMER);
        register("l_handles", HILTS);
    }

    private static void register(String name, Item item){
        Registry.register(Registry.ITEM, new Identifier(StarMain.ID, name), item);
    }

    public static void registerColors(){
        // DO AN IF TO CHECK TINTINDEX
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> LColor.getColor(stack.getOrCreateNbt().getInt("color")).convert().getSignColor(), FBlocks.CRYSTAL);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> LColor.getColor(stack.getOrCreateNbt().getInt("color")).convert().getSignColor(), FBlocks.WORKSTATION);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> LColor.getColor(stack.getOrCreateNbt().getInt("color")).convert().getSignColor(), KYBER);
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
        FabricModelPredicateProviderRegistry.register(HILTS, new Identifier("handle"), (stack, world, entity, seed) -> stack.getOrCreateNbt().getInt("handle") / 10.0F);
    }
}
