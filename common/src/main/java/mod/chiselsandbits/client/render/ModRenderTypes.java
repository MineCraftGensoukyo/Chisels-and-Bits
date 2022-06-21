package mod.chiselsandbits.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import mod.chiselsandbits.platforms.core.util.constants.Constants;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;
import java.util.function.Supplier;

public enum ModRenderTypes
{
    MEASUREMENT_LINES(() -> InternalType.MEASUREMENT_LINES),
    WIREFRAME_LINES(() -> InternalType.WIREFRAME_LINES),
    WIREFRAME_LINES_ALWAYS(() -> InternalType.WIREFRAME_LINES_ALWAYS),
    WIREFRAME_BODY(() -> InternalType.WIREFRAME_BODY),
    GHOST_BLOCK_PREVIEW(() -> InternalType.GHOST_BLOCK_PREVIEW),
    GHOST_BLOCK_PREVIEW_GREATER(() -> InternalType.GHOST_BLOCK_PREVIEW_GREATER);

    private final Supplier<RenderType> typeSupplier;

    ModRenderTypes(final Supplier<RenderType> typeSupplier) {this.typeSupplier = typeSupplier;}

    public RenderType get() {
        return typeSupplier.get();
    }

    private static class InternalState extends RenderStateShard
    {
        private static final DepthTestStateShard DISABLED_DEPTH_TEST = new DepthTestDisabled();

        private static class DepthTestDisabled extends DepthTestStateShard
        {
            public DepthTestDisabled()
            {
                super("depth_test", 0);
            }

            @Override
            public void setupRenderState()
            {
                RenderSystem.disableDepthTest();
            }

            @Override
            public void clearRenderState()
            {
                RenderSystem.enableDepthTest();
            }

            @Override
            public String toString()
            {
                return name + "[" + Constants.MOD_ID + ":depth_test_disabled]";
            }
        }

        public InternalState(String name, Runnable setupState, Runnable clearState) {
            super(name, setupState, clearState);
            throw new IllegalStateException("This class must not be instantiated");
        }
    }

    private static class InternalType extends RenderType
    {
        private static final RenderType MEASUREMENT_LINES = RenderType.create(Constants.MOD_ID + ":measurement_lines",
          DefaultVertexFormat.POSITION_COLOR_NORMAL,
          VertexFormat.Mode.LINES,
          256,
          false,
          false,
          RenderType.CompositeState.builder()
            .setShaderState(RENDERTYPE_LINES_SHADER)
            .setLineState(new LineStateShard(OptionalDouble.of(2.5d)))
            .setLayeringState(VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(TRANSLUCENT_TARGET)
            .setWriteMaskState(COLOR_WRITE)
            .setCullState(NO_CULL)
            .setDepthTestState(InternalState.DISABLED_DEPTH_TEST)
            .createCompositeState(false));

        private static final RenderType WIREFRAME_LINES = buildWireframeType(false);
        private static final RenderType WIREFRAME_LINES_ALWAYS = buildWireframeType(true);

        private static RenderType buildWireframeType(final boolean always)
        {
          return RenderType.create(Constants.MOD_ID + ":wireframe_lines" + (always ? "_always" : ""),
          DefaultVertexFormat.POSITION_COLOR,
          VertexFormat.Mode.LINES,
          256,
          false,
          true,
          CompositeState.builder()
            .setShaderState(RENDERTYPE_LINES_SHADER)
            .setLineState(new LineStateShard(OptionalDouble.of(3d)))
            .setLayeringState(VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(TRANSLUCENT_TARGET)
            .setWriteMaskState(COLOR_WRITE)
            .setCullState(NO_CULL)
            .setDepthTestState(always ? InternalState.DISABLED_DEPTH_TEST : LEQUAL_DEPTH_TEST)
            .createCompositeState(false));
        }

        private static final RenderType WIREFRAME_BODY = RenderType.create(Constants.MOD_ID + ":wireframe_body",
          DefaultVertexFormat.BLOCK,
          VertexFormat.Mode.QUADS,
          2097152,
          false,
          true,
          CompositeState.builder()
            .setShaderState(RenderType.RENDERTYPE_SOLID_SHADER)
            .setLayeringState(VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(NO_TRANSPARENCY)
            .setOutputState(TRANSLUCENT_TARGET)
            .setWriteMaskState(COLOR_WRITE)
            .setCullState(NO_CULL)
            .setDepthTestState(NO_DEPTH_TEST)
            .createCompositeState(false));

        private static final TextureStateShard BLOCK_TEXTURE = new TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false);
        private static final DepthTestStateShard GREATER_DEPTH_TEST = new DepthTestStateShard(">", GL11.GL_GREATER);

        private static final RenderType GHOST_BLOCK_PREVIEW = buildGhostType(false);
        private static final RenderType GHOST_BLOCK_PREVIEW_GREATER = buildGhostType(true);

        private static RenderType buildGhostType(final boolean greater)
        {
            if (!greater)
                return Sheets.translucentCullBlockSheet();

            return RenderType.create(Constants.MOD_ID + ":ghost_block_preview_greater",
              DefaultVertexFormat.NEW_ENTITY,
              VertexFormat.Mode.QUADS,
              256,
              true,
              true,
              CompositeState.builder()
                .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER)
                .setTextureState(BLOCK_TEXTURE)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .setDepthTestState(GREATER_DEPTH_TEST) // Only difference from RenderType#ENTITY_TRANSLUCENT_CULL
                .createCompositeState(true));
        }

        private InternalType(String name, VertexFormat fmt, VertexFormat.Mode glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable)
        {
            super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
            throw new IllegalStateException("This class must not be instantiated");
        }
    }
}
