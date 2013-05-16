
package scottishbiomes.biome;

import java.util.Random;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import scottishbiomes.tree.Sequoia;

import com.google.common.base.Optional;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RedwoodForest extends BiomeGenBase
{
    private static Optional<? extends WorldGenerator> treeGen = Optional.absent();
    private static Optional<? extends WorldGenerator> shrubGen = Optional.absent();

    @SuppressWarnings("unchecked")
    public RedwoodForest(final int id)
    {
        super(id);

        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));

        theBiomeDecorator.treesPerChunk = 30;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor()
    {
        final double d0 = MathHelper.clamp_float(BiomeGenBase.plains.temperature, 0.0F, 1.0F);
        final double d1 = MathHelper.clamp_float(BiomeGenBase.plains.rainfall, 0.0F, 1.0F);
        return getModdedBiomeFoliageColor(ColorizerFoliage.getFoliageColor(d0, d1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor()
    {
        final double d0 = MathHelper.clamp_float(BiomeGenBase.plains.temperature, 0.0F, 1.0F);
        final double d1 = MathHelper.clamp_float(BiomeGenBase.plains.rainfall, 0.0F, 1.0F);
        return getModdedBiomeGrassColor(ColorizerGrass.getGrassColor(d0, d1));
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random)
    {
        if (!treeGen.isPresent())
        {
            populateTreeGen();
        }
        return par1Random.nextInt(10) == 0 ? worldGeneratorBigTree : par1Random.nextInt(2) == 0
                ? shrubGen.get() : par1Random.nextInt(3) == 0 ? treeGen.get() : new WorldGenTrees(
                        false, 4 + par1Random.nextInt(7), 0, 0, false);
    }

    private void populateTreeGen()
    {
        treeGen = Optional.of(new Sequoia());
        shrubGen = Optional.of(new WorldGenShrub(0, 0));
    }
}
