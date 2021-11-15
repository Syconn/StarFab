package mod.syconn.stf.structures;

import com.mojang.serialization.Codec;
import mod.syconn.stf.StarMain;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.GeodeFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.apache.logging.log4j.Level;

import java.util.Random;

public class CrystalCave extends StructureFeature<DefaultFeatureConfig> {
    public CrystalCave(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return CrystalCave.Start::new;
    }

    private static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
            new SpawnSettings.SpawnEntry(EntityType.ILLUSIONER, 100, 4, 9),
            new SpawnSettings.SpawnEntry(EntityType.VINDICATOR, 100, 4, 9)
    );

    @Override
    public Pool<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return STRUCTURE_MONSTERS;
    }

    private static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_CREATURES = Pool.of(
            new SpawnSettings.SpawnEntry(EntityType.SHEEP, 30, 10, 15),
            new SpawnSettings.SpawnEntry(EntityType.RABBIT, 100, 1, 2)
    );
    @Override
    public Pool<SpawnSettings.SpawnEntry> getCreatureSpawns() {
        return STRUCTURE_CREATURES;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, ChunkRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, DefaultFeatureConfig featureConfig, HeightLimitView heightLimitView) {
        BlockPos centerOfChunk = new BlockPos(chunkPos.x * 16, 0, chunkPos.z * 16);

        // Grab height of land. Will stop at first non-air block.
        int landHeight = chunkGenerator.getHeightInGround(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG, heightLimitView);

        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
        // the chunk generator will place for that dimension.
        VerticalBlockSample columnOfBlocks = chunkGenerator.getColumnSample(centerOfChunk.getX(), centerOfChunk.getZ(), heightLimitView);

        // Combine the column of blocks with land height and you get the top block itself which you can test.
        BlockState topBlock = columnOfBlocks.getState(centerOfChunk.up(landHeight));

        // Now we test to make sure our structure is not spawning on water or other fluids.
        // You can do height check instead too to make it spawn at high elevations.
        return topBlock.getFluidState().isEmpty(); //landHeight > 100;
    }

    public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureIn, ChunkPos chunkPos, int referenceIn, long seedIn) {
            super(structureIn, chunkPos, referenceIn, seedIn);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, DefaultFeatureConfig defaultFeatureConfig, HeightLimitView heightLimitView) {

            int x = chunkPos.x * 16;
            int z = chunkPos.z * 16;
            int r = new Random().nextInt(3);

            BlockPos.Mutable centerPos = new BlockPos.Mutable(x, 45 + r * 3, z);

            StructurePoolFeatureConfig structureSettingsAndStartPool = new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.STRUCTURE_POOL_KEY)
                    .get(new Identifier(StarMain.ID, "crystal_cave/start_pool")), 10);

            StructurePoolBasedGenerator.generate(
                    dynamicRegistryManager,
                    structureSettingsAndStartPool,
                    PoolStructurePiece::new,
                    chunkGenerator,
                    structureManager,
                    centerPos, // Position of the structure. Y value is ignored if last parameter is set to true.
                    this, // The class instance that holds the list that will be populated with the jigsaw pieces after this method.
                    this.random,
                    false, // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
                    // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
                    false, // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
                    // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                    heightLimitView);

            this.children.forEach(piece -> piece.translate(0, 2, 0));

            Vec3i structureCenter = this.children.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for(StructurePiece structurePiece : this.children){
                structurePiece.translate(xOffset, 0, zOffset);
            }

            this.setBoundingBoxFromChildren();
        }

    }
}
