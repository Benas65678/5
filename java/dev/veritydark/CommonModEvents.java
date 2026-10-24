package dev.veritydark;

import dev.veritydark.entity.AnomalyEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VerityDark.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonModEvents {
    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent event) {
        event.put(ModContent.ANOMALY.get(), AnomalyEntity.createAttributes().build());
    }

    private CommonModEvents() {}
}
