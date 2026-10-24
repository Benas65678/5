package dev.veritydark.client;

import dev.veritydark.VerityDark;
import dev.veritydark.entity.AnomalyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

/**
 * Renders the Blockbench model.
 * Files: geo/entity/anomaly.geo.json, animations/entity/anomaly.animation.json,
 * textures/entity/anomaly.png and textures/entity/anomaly_glowmask.png (the glowing eyes and runes).
 */
public class AnomalyRenderer extends GeoEntityRenderer<AnomalyEntity> {
    public AnomalyRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(new ResourceLocation(VerityDark.MODID, "anomaly")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
        withScale(0.8F);
    }
}
