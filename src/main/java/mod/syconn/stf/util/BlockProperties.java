package mod.syconn.stf.util;

import mod.syconn.stf.items.lightsaber.customize.LColor;
import net.minecraft.state.property.EnumProperty;

public class BlockProperties {

    public static EnumProperty<LColor> COLOR = EnumProperty.of("color", LColor.class);

}
