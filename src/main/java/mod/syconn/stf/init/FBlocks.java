package mod.syconn.stf.init;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.blocks.CrystalBlock;
import mod.syconn.stf.blocks.LightsaberWorkstation;
import mod.syconn.stf.util.BlockProperties;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FBlocks {

    public static CrystalBlock CRYSTAL = new CrystalBlock();
    public static LightsaberWorkstation WORKSTATION = new LightsaberWorkstation();

    public static void registerBlocks(){
        register("crystal", CRYSTAL);
        register("workstation", WORKSTATION);
    }

    private static void register(String name, Block block){
        Registry.register(Registry.BLOCK, new Identifier(StarMain.ID, name), block);
        Registry.register(Registry.ITEM, new Identifier(StarMain.ID, name), new BlockItem(block, new FabricItemSettings().group(StarMain.TEST_GROUP)));
    }

    public static void registerColors(){
        // DO AN IF TO CHECK TINTINDEX
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> state.get(BlockProperties.COLOR).convert().getSignColor()), CRYSTAL);
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> state.get(BlockProperties.COLOR).convert().getSignColor()), WORKSTATION);
    }
}
