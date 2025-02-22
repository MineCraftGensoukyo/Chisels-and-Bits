package mod.chiselsandbits.forge.data.blockstate;

import mod.chiselsandbits.platforms.core.util.constants.Constants;
import mod.chiselsandbits.registrars.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModificationTableBlockStateGenerator extends BlockStateProvider implements DataProvider
{
    private static final ResourceLocation MODIFICATION_TABLE_BLOCK_MODEL = new ResourceLocation(Constants.MOD_ID, "block/modification_table");

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new ModificationTableBlockStateGenerator(event.getGenerator(), event.getExistingFileHelper()));
    }

    public ModificationTableBlockStateGenerator(final DataGenerator gen, final ExistingFileHelper exFileHelper)
    {
        super(gen, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        horizontalBlock(
          ModBlocks.MODIFICATION_TABLE.get(),
          models().getExistingFile(
            MODIFICATION_TABLE_BLOCK_MODEL
          ),
          0
        );
    }

    @Override
    public @NotNull String getName()
    {
        return "Modification station blockstate generator.";
    }
}
