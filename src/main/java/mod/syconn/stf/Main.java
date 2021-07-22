package mod.syconn.stf;

import mod.syconn.stf.init.FItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {

	public static final String ID = "stf";

	@Override
	public void onInitialize() {
		FItems.registerItems();
	}

	public static final ItemGroup TEST_GROUP = FabricItemGroupBuilder.create(
			new Identifier("stf", "starwars"))
			.icon(() -> new ItemStack(FItems.lightsaber))
			.appendItems(stacks -> stacks.add(new ItemStack(FItems.lightsaber)))
			.build();
}