package dev.veritydark.client;

import dev.veritydark.ModSounds;
import dev.veritydark.VerityDark;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Sounds only you hear as your mind frays:
 *  - below 40%: phantom knocks, doors and footsteps nearby,
 *  - below 35%: whispers from behind you,
 *  - below 25%: a heartbeat that speeds up and gets louder.
 */
@Mod.EventBusSubscriber(modid = VerityDark.MODID, value = Dist.CLIENT)
public final class ClientForgeEvents {
    private static final SoundEvent[] PHANTOM_SOUNDS = {
            SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, // knocking
            SoundEvents.WOODEN_DOOR_OPEN,
            SoundEvents.WOODEN_DOOR_CLOSE
    };
    private static int heartTimer = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null || mc.isPaused() || player.isSpectator()) return;

        float pct = ClientSanity.pct();

        // heartbeat
        if (pct < 0.25F) {
            if (--heartTimer <= 0) {
                float urgency = 1.0F - pct / 0.25F;                       // 0..1
                float volume = 0.35F + 0.65F * urgency;
                mc.getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.HEARTBEAT.get(), 1.0F + 0.15F * urgency, volume));
                heartTimer = (int) (46 - 22 * urgency);
            }
        } else {
            heartTimer = 0;
        }

        // phantom world sounds and whispers
        if (pct >= 0.4F) return;
        float chancePerTick = (0.4F - pct) / 0.4F * 0.002F; // ~1 per 25s at zero sanity
        if (mc.level.random.nextFloat() > chancePerTick) return;

        double angle = mc.level.random.nextDouble() * Math.PI * 2.0;
        double dist = 5.0 + mc.level.random.nextDouble() * 5.0;
        boolean whisper = pct < 0.35F && mc.level.random.nextBoolean();
        SoundEvent sound = whisper ? ModSounds.WHISPER.get()
                : PHANTOM_SOUNDS[mc.level.random.nextInt(PHANTOM_SOUNDS.length)];
        mc.level.playLocalSound(
                player.getX() + Math.cos(angle) * dist, player.getY(), player.getZ() + Math.sin(angle) * dist,
                sound, SoundSource.AMBIENT, whisper ? 0.9F : 0.6F, 0.7F + mc.level.random.nextFloat() * 0.3F, false);
    }

    private ClientForgeEvents() {}
}
