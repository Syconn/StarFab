package mod.syconn.stf.init;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.structures.CrystalCave;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class FStructures {

    public static StructureFeature<DefaultFeatureConfig> CRYSTAL_CAVE = new CrystalCave(DefaultFeatureConfig.CODEC);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_CRYSTAL_CAVE = CRYSTAL_CAVE.configure(DefaultFeatureConfig.DEFAULT);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(StarMain.ID, "crystal_cave"), CRYSTAL_CAVE).step(GenerationStep.Feature.LOCAL_MODIFICATIONS).defaultConfig(new StructureConfig(10, 5, 399117345)).superflatFeature(CRYSTAL_CAVE.configure(FeatureConfig.DEFAULT)).register();
    }

    public static void registerConfiguredStructures(){
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(StarMain.ID, "configured_crystal_cave"), CONFIGURED_CRYSTAL_CAVE);
    }

    public static void BiomeModifier(){
        BiomeModifications.create(new Identifier(StarMain.ID, "crystal_cave_additions"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.all(),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(CONFIGURED_CRYSTAL_CAVE);
                        });
    }
}
