package net.legacyfabric.resourceloader.impl.resource.loader;

import net.legacyfabric.resourceloader.api.resource.ModResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackCreator;
import net.minecraft.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModResourcePackCreator implements ResourcePackCreator {
    private final ResourceType type;

    public ModResourcePackCreator(ResourceType type) {
        this.type = type;
    }

    @Override
    public <T extends ResourcePackContainer> void registerContainer(Map<String, T> map, ResourcePackContainer.Factory<T> factory) {
        // TODO: "vanilla" does not emit a message; neither should a modded datapack
        List<ResourcePack> packs = new ArrayList<>();
        ModResourcePackUtil.appendModResourcePacks(packs, type);

        for (ResourcePack pack : packs) {
            if (!(pack instanceof ModResourcePack)) {
                throw new RuntimeException("Not a ModResourcePack!");
            }

            T var3 = ResourcePackContainer.of("fabric/" + ((ModResourcePack) pack).getFabricModMetadata().getId(),
                    false, () -> pack, factory, ResourcePackContainer.InsertionPosition.TOP);

            if (var3 != null) {
                map.put(var3.getName(), var3);
            }
        }
    }
}
