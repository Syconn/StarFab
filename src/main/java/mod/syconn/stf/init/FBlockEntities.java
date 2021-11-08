package mod.syconn.stf.init;

import com.google.common.collect.Lists;
import mod.syconn.stf.StarMain;
import mod.syconn.stf.blockentity.WorkstationEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FBlockEntities {

    public static BlockEntityType<WorkstationEntity> WORKSTATION;

    public static void registerEntities(){
        WORKSTATION = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(StarMain.ID, "workstation"), FabricBlockEntityTypeBuilder.create(WorkstationEntity::new, FBlocks.WORKSTATION).build(null));
    }
}
