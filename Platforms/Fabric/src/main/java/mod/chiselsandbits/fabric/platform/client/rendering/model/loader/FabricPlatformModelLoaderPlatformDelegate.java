package mod.chiselsandbits.fabric.platform.client.rendering.model.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import mod.chiselsandbits.platforms.core.client.models.loaders.IModelSpecification;
import mod.chiselsandbits.platforms.core.client.models.loaders.IModelSpecificationLoader;
import mod.chiselsandbits.platforms.core.util.constants.Constants;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class FabricPlatformModelLoaderPlatformDelegate<L extends IModelSpecificationLoader<S>, S extends IModelSpecification<S>> implements ModelResourceProvider
{

    private final Gson gson;

    public FabricPlatformModelLoaderPlatformDelegate(final ResourceLocation name, final L delegate)
    {
        this.gson = new GsonBuilder()
                      .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
                      .registerTypeHierarchyAdapter(IModelSpecification.class, (JsonDeserializer<IModelSpecification<?>>) (json, typeOfT, context) -> {
                          if (!json.isJsonObject())
                              throw new JsonParseException("Model specification must be an object");

                          if (!json.getAsJsonObject().has("loader"))
                              return null;

                          if (!json.getAsJsonObject().get("loader").getAsString().equals(name.toString()))
                              return null;

                          return delegate.read(context, json.getAsJsonObject());
                      })
                      .disableHtmlEscaping()
                      .create();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable UnbakedModel loadModelResource(
      final ResourceLocation resourceLocation, final ModelProviderContext modelProviderContext) throws ModelProviderException
    {
        if (!resourceLocation.getNamespace().equals(Constants.MOD_ID))
            return null;

        try
        {
            final ResourceLocation target = new ResourceLocation(resourceLocation.getNamespace(), "models/" + resourceLocation.getPath() + ".json");

            final Resource resource = Minecraft.getInstance().getResourceManager().getResource(target);
            final InputStream inputStream = resource.getInputStream();
            final InputStreamReader streamReader = new InputStreamReader(inputStream);

            final IModelSpecification<S> modelSpecification = gson.fromJson(streamReader, IModelSpecification.class);

            streamReader.close();
            inputStream.close();
            resource.close();

            if (modelSpecification == null)
                return null;

            return new FabricModelSpecificationUnbakedModelDelegate<>(modelSpecification);
        }
        catch (IOException e)
        {
            throw new ModelProviderException("Failed to find and read resource", e);
        }
    }
}
