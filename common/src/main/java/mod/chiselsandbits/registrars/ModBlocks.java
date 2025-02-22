package mod.chiselsandbits.registrars;

import com.google.common.collect.Maps;
import mod.chiselsandbits.block.*;
import mod.chiselsandbits.platforms.core.util.constants.Constants;
import mod.chiselsandbits.materials.MaterialManager;
import mod.chiselsandbits.platforms.core.registries.deferred.IRegistrar;
import mod.chiselsandbits.platforms.core.registries.deferred.IRegistryObject;
import mod.chiselsandbits.utils.ReflectionHelperBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Function;

public final class ModBlocks
{

    public static final  Map<Material, IRegistryObject<ChiseledBlock>> MATERIAL_TO_BLOCK_CONVERSIONS = Maps.newHashMap();
    private static final Logger                                        LOGGER                        = LogManager.getLogger();
    private static final IRegistrar<Block>                             BLOCK_REGISTRAR               = IRegistrar.create(Registry.BLOCK_REGISTRY, Constants.MOD_ID);
    public static final  IRegistryObject<BitStorageBlock>              BIT_STORAGE                   =
      BLOCK_REGISTRAR.register("bit_storage", () -> new BitStorageBlock(BlockBehaviour.Properties.of(Material.METAL)
        .strength(1.5F, 6.0F)
        .requiresCorrectToolForDrops()
        .dynamicShape()
        .noOcclusion()
        .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
        .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false)));

    public static final IRegistryObject<ModificationTableBlock> MODIFICATION_TABLE =
      BLOCK_REGISTRAR.register("modification_table", () -> new ModificationTableBlock(BlockBehaviour.Properties.of(Material.METAL)
        .strength(1.5F, 6.0F)
        .requiresCorrectToolForDrops()
        .dynamicShape()
        .noOcclusion()
        .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
        .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false)));

    public static final IRegistryObject<ChiseledPrinterBlock> CHISELED_PRINTER = BLOCK_REGISTRAR.register(
      "chiseled_printer",
      () -> new ChiseledPrinterBlock(BlockBehaviour.Properties.of(Material.METAL)
        .strength(1.5F, 6.0F)
        .requiresCorrectToolForDrops()
        .dynamicShape()
        .noOcclusion()
        .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
        .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false))
    );

    public static final IRegistryObject<PatternScannerBlock> PATTERN_SCANNER = BLOCK_REGISTRAR.register(
      "pattern_scanner",
      () -> new PatternScannerBlock(BlockBehaviour.Properties.of(Material.STONE)
        .strength(1.5F, 6.0F)
        .requiresCorrectToolForDrops()
        .dynamicShape()
        .noOcclusion()
        .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
        .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
        .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false))
    );

    public static IRegistryObject<ReflectionHelperBlock> REFLECTION_HELPER_BLOCK = BLOCK_REGISTRAR.register(
            "reflection_helper_block",
            ReflectionHelperBlock::new
    );

    private ModBlocks()
    {
        throw new IllegalStateException("Tried to initialize: ModBlocks but this is a Utility class.");
    }

    public static void onModConstruction(Function<BlockBehaviour.Properties, ChiseledBlock> chiseledBlockFactory)
    {
        MaterialManager.getInstance()
          .getKnownMaterials()
          .forEach((name, material) -> MATERIAL_TO_BLOCK_CONVERSIONS.put(
            material,
            BLOCK_REGISTRAR.register(
              "chiseled" + name,
              () -> chiseledBlockFactory.apply(BlockBehaviour.Properties
                .of(material)
                .strength(1.5f, 6f)
                .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
                .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
                .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
                .noOcclusion())
            )
          ));
        LOGGER.info("Loaded block configuration.");
    }
}
