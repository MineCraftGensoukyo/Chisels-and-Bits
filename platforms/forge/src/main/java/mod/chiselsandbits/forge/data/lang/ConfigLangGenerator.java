package mod.chiselsandbits.forge.data.lang;

import com.google.gson.JsonObject;
import mod.chiselsandbits.forge.platform.configuration.ForgeConfigurationManager;
import mod.chiselsandbits.platforms.core.util.constants.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigLangGenerator implements DataProvider
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new ConfigLangGenerator(event.getGenerator()));
    }

    private final DataGenerator generator;

    private ConfigLangGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {


        final List<String> langKeys = new ArrayList<>(ForgeConfigurationManager.getInstance().getAvailableKeys());
        langKeys.sort(Comparator.comparing(s -> s.replace(".comment", "")));
        final JsonObject returnValue = new JsonObject();

        for (String langKey : langKeys)
        {
            returnValue.addProperty("mod.chiselsandbits.config." + langKey, "");
        }

        final Path configLangFolder = this.generator.getOutputFolder().resolve(Constants.DataGenerator.CONFIG_LANG_DIR);
        final Path langPath = configLangFolder.resolve("config.json");

        DataProvider.save(Constants.DataGenerator.GSON, cache, returnValue, langPath);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Chiseled config lang generator";
    }
}
