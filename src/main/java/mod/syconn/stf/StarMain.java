package mod.syconn.stf;

import mod.syconn.stf.init.FItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class StarMain implements ModInitializer {

	public static final String ID = "stf";

	@Override
	public void onInitialize() {
		FItems.registerItems();
	}
	public static final ItemGroup TEST_GROUP = FabricItemGroupBuilder.create(
			new Identifier("stf", "starwars"))
			.icon(() -> new ItemStack(FItems.LIGHTSABER))
			.build();

	public static Identifier id(String id){
		return new Identifier(ID, id);
	}
}