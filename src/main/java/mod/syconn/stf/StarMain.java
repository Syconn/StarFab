package mod.syconn.stf;

import mod.syconn.stf.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StarMain implements ModInitializer {

	public static final String ID = "stf";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		FItems.registerItems();
		FBlocks.registerBlocks();
		FBlockEntities.registerEntities();
		FScreens.registerHandlers();
		FStructures.setupAndRegisterStructureFeatures();
		FStructures.registerConfiguredStructures();
		FStructures.BiomeModifier();
	}

	public static final ItemGroup TEST_GROUP = FabricItemGroupBuilder.create(
			new Identifier("stf", "starwars"))
			.icon(() -> new ItemStack(FItems.LIGHTSABER))
			.build();

	public static Identifier id(String id){
		return new Identifier(ID, id);
	}
}