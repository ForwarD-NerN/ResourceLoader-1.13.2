package net.legacyfabric.resourceloader.mixin.resource.loader;

import net.legacyfabric.resourceloader.impl.resource.loader.ModResourcePackCreator;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackContainerManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow
    private ResourcePackContainerManager<ResourcePackContainer> dataPackContainerManager;

    @Inject(method = "loadWorldDataPacks", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackContainerManager;addCreator(Lnet/minecraft/resource/ResourcePackCreator;)V", ordinal = 1))
    public void appendFabricDataPacks(File file, LevelProperties properties, CallbackInfo info) {
        dataPackContainerManager.addCreator(new ModResourcePackCreator(ResourceType.SERVER_DATA));
    }
}
