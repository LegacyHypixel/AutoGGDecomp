package uk.debb.autogg.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.autogg.AutoGG;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Unique
    private long lastTime = 0L;
    @Shadow
    @Final
    private MinecraftClient client;
    @Unique
    private List<String> hypixelGGStrings = new ArrayList<>();
    @Unique
    private List<String> bedwarsPracticeGGStrings = new ArrayList<>();
    @Unique
    private List<String> pvpLandGGStrings = new ArrayList<>();
    @Unique
    private List<String> minemenGGStrings = new ArrayList<>();
    @Unique
    private List<String> hypixelGLHFStrings = new ArrayList<>();
    @Unique
    private List<String> bedwarsPracticeGLHFStrings = new ArrayList<>();
    @Unique
    private List<String> pvpLandGLHFStrings = new ArrayList<>();
    @Unique
    private List<String> minemenGLHFStrings = new ArrayList<>();
    @Unique
    private List<String> hypixelGFStrings = new ArrayList<>();
    @Unique
    private List<String> bedwarsPracticeGFStrings = new ArrayList<>();
    @Unique
    private List<String> pvpLandGFStrings = new ArrayList<>();
    @Unique
    private List<String> minemenGFStrings = new ArrayList<>();

    @Unique
    private void populateHypixelGGStrings() {
        this.hypixelGGStrings.add("1st Killer -");
        this.hypixelGGStrings.add("1st Place -");
        this.hypixelGGStrings.add("Winner:");
        this.hypixelGGStrings.add(" - Damage Dealt -");
        this.hypixelGGStrings.add("Winning Team -");
        this.hypixelGGStrings.add("1st -");
        this.hypixelGGStrings.add("Winners:");
        this.hypixelGGStrings.add("Winner:");
        this.hypixelGGStrings.add("Winning Team:");
        this.hypixelGGStrings.add(" won the game!");
        this.hypixelGGStrings.add("Top Seeker:");
        this.hypixelGGStrings.add("1st Place:");
        this.hypixelGGStrings.add("Last team standing!");
        this.hypixelGGStrings.add("Winner #1 (");
        this.hypixelGGStrings.add("Top Survivors");
        this.hypixelGGStrings.add("Winners -");
        this.hypixelGGStrings.add("Sumo Duel -");
        this.hypixelGGStrings.add("Most Wool Placed -");
        this.hypixelGGStrings.add("Your Overall Winstreak:");
    }

    @Unique
    private void populateBedwarsPracticeGGStrings() {
        this.bedwarsPracticeGGStrings.add("Winners -");
        this.bedwarsPracticeGGStrings.add("Game Won!");
        this.bedwarsPracticeGGStrings.add("Game Lost!");
        this.bedwarsPracticeGGStrings.add("The winning team is");
    }

    @Unique
    private void populatePvpLandGGStrings() {
        this.pvpLandGGStrings.add("The match has ended!");
        this.pvpLandGGStrings.add("Match Results");
        this.pvpLandGGStrings.add("Winner:");
        this.pvpLandGGStrings.add("Loser:");
    }

    @Unique
    private void populateMinemenGGStrings() {
        this.minemenGGStrings.add("Match Results");
    }

    @Unique
    private void populateHypixelGLHFStrings() {
        this.hypixelGLHFStrings.add("The game starts in 1 second!");
    }

    @Unique
    private void populateBedwarsPracticeGLHFStrings() {
        this.bedwarsPracticeGLHFStrings.add("Game starting in 1 seconds!");
        this.bedwarsPracticeGLHFStrings.add("Game has started!");
    }

    @Unique
    private void populatePvpLandGLHFStrings() {
        this.pvpLandGLHFStrings.add("The match is starting in 1 second.");
        this.pvpLandGLHFStrings.add("The match has started!");
    }

    @Unique
    private void populateMinemenGLHFStrings() {
        this.minemenGLHFStrings.add("1...");
    }

    @Unique
    private void populateHypixelGFStrings() {
        this.hypixelGFStrings.add("SkyWars Experience (Kill)");
        this.hypixelGFStrings.add("coins! (Final Kill)");
    }

    @Unique
    private void populateBedwarsPracticeGFStrings() {
        this.bedwarsPracticeGFStrings.add(this.client.getSession().getUsername() + " FINAL KILL!");
    }

    @Unique
    private void populatePvpLandGFStrings() {
        this.pvpLandGFStrings.add("slain by " + this.client.getSession().getUsername());
    }

    @Unique
    private void populateMinemenGFStrings() {
        this.minemenGFStrings.add("killed by " + this.client.getSession().getUsername() + "!");
    }

    @Unique
    private void processChat(Text messageRecieved, List<String> options, String messageToSend) {
        if (System.currentTimeMillis() - this.lastTime <= 3000L) {
            return;
        }
        for (String s : options) {
            if (!messageRecieved.asUnformattedString().contains(s)) continue;
            this.client.player.sendChatMessage(messageToSend);
            this.lastTime = System.currentTimeMillis();
            return;
        }
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At(value="HEAD"), cancellable = true)
    private void typeGG(Text message, int messageId, int timestamp, boolean bl, CallbackInfo ci) {
        if (this.client.getCurrentServerEntry().address.contains("hypixel.net")) {
            if (AutoGG.config.gfMessages) {
                if (this.hypixelGFStrings.size() == 0) {
                    this.populateHypixelGFStrings();
                }
                this.processChat(message, this.hypixelGFStrings, "gf");
            }
            if (AutoGG.config.ggMessages) {
                if (this.hypixelGGStrings.size() == 0) {
                    this.populateHypixelGGStrings();
                }
                this.processChat(message, this.hypixelGGStrings, "gg");
            }
            if (AutoGG.config.glhfMessages) {
                if (this.hypixelGLHFStrings.size() == 0) {
                    this.populateHypixelGLHFStrings();
                }
                this.processChat(message, this.hypixelGLHFStrings, "glhf");
            }
        } else if (this.client.getCurrentServerEntry().address.contains("bedwarspractice.club")) {
            if (AutoGG.config.gfMessages) {
                if (this.bedwarsPracticeGFStrings.size() == 0) {
                    this.populateBedwarsPracticeGFStrings();
                }
                this.processChat(message, this.bedwarsPracticeGFStrings, "gf");
            }
            if (AutoGG.config.ggMessages) {
                if (this.bedwarsPracticeGGStrings.size() == 0) {
                    this.populateBedwarsPracticeGGStrings();
                }
                this.processChat(message, this.bedwarsPracticeGGStrings, "gg");
            }
            if (AutoGG.config.glhfMessages) {
                if (this.bedwarsPracticeGLHFStrings.size() == 0) {
                    this.populateBedwarsPracticeGLHFStrings();
                }
                this.processChat(message, this.bedwarsPracticeGLHFStrings, "glhf");
            }
        } else if (this.client.getCurrentServerEntry().address.contains("pvp.land")) {
            if (AutoGG.config.gfMessages) {
                if (this.pvpLandGFStrings.size() == 0) {
                    this.populatePvpLandGFStrings();
                }
                this.processChat(message, this.pvpLandGFStrings, "gf");
            }
            if (AutoGG.config.ggMessages) {
                if (this.pvpLandGGStrings.size() == 0) {
                    this.populatePvpLandGGStrings();
                }
                this.processChat(message, this.pvpLandGGStrings, "gg");
            }
            if (AutoGG.config.glhfMessages) {
                if (this.pvpLandGLHFStrings.size() == 0) {
                    this.populatePvpLandGLHFStrings();
                }
                this.processChat(message, this.pvpLandGLHFStrings, "glhf");
            }
        } else if (this.client.getCurrentServerEntry().address.contains("minemen.club")) {
            if (AutoGG.config.gfMessages) {
                if (this.minemenGFStrings.size() == 0) {
                    this.populateMinemenGFStrings();
                }
                this.processChat(message, this.minemenGFStrings, "gf");
            }
            if (AutoGG.config.ggMessages) {
                if (this.minemenGGStrings.size() == 0) {
                    this.populateMinemenGGStrings();
                }
                this.processChat(message, this.minemenGGStrings, "gg");
            }
            if (AutoGG.config.glhfMessages) {
                if (this.minemenGLHFStrings.size() == 0) {
                    this.populateMinemenGLHFStrings();
                }
                this.processChat(message, this.minemenGLHFStrings, "glhf");
            }
        } else if (this.client.getCurrentServerEntry().address.contains("mcplayhd.net") && AutoGG.config.lMessages && message.toString().contains("Regardless, we wish you a lot of fun playing.")) {
            this.client.player.sendChatMessage("L");
        }
    }
}
