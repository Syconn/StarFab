package mod.syconn.stf.init;

import com.google.common.collect.Lists;
import mod.syconn.stf.Main;
import mod.syconn.stf.item.Lightsaber;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class FItems {

    public static Lightsaber lightsaber = new Lightsaber();

    public static void registerItems(){
        register("lightsaber", lightsaber);
    }

    private static void register(String name, Item item){
        Registry.register(Registry.ITEM, new Identifier(Main.ID, name), item);
    }
}
