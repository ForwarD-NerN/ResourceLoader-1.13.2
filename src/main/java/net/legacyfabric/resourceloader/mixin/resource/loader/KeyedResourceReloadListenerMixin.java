/*
 * Copyright (c) 2020 - 2022 Legacy Fabric
 * Copyright (c) 2016 - 2022 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.legacyfabric.resourceloader.mixin.resource.loader;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import net.legacyfabric.resourceloader.api.util.Identifier;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.loot.LootManager;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.tag.RegistryTagManager;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.texture.TextureManager;

import net.legacyfabric.resourceloader.api.resource.IdentifiableResourceReloadListener;
import net.legacyfabric.resourceloader.api.resource.ResourceReloadListenerKeys;
public class KeyedResourceReloadListenerMixin {
	@Mixin({
			/* public */
			SoundManager.class, FontManager.class, BakedModelManager.class, LanguageManager.class, TextureManager.class,
			/* private */
			WorldRenderer.class, BlockRenderManager.class, HeldItemRenderer.class
	})
	public abstract static class Client implements IdentifiableResourceReloadListener {
		private Collection<Identifier> fabric_idDeps;
		private Identifier fabric_id;

		@Override
		@SuppressWarnings({"ConstantConditions", "RedundantCast"})
		public Collection<Identifier> getFabricDependencies() {
			if (fabric_idDeps == null) {
				Object self = this;

				if (self instanceof BakedModelManager || self instanceof WorldRenderer) {
					fabric_idDeps = Collections.singletonList(ResourceReloadListenerKeys.TEXTURES);
				} else if (self instanceof HeldItemRenderer || self instanceof BlockRenderManager) {
					fabric_idDeps = Collections.singletonList(ResourceReloadListenerKeys.MODELS);
				} else {
					fabric_idDeps = Collections.emptyList();
				}
			}

			return fabric_idDeps;
		}

		@Override
		@SuppressWarnings({"ConstantConditions", "RedundantCast"})
		public Identifier getFabricId() {
			if (fabric_id == null) {
				Object self = this;

				if (self instanceof SoundManager) {
					fabric_id = ResourceReloadListenerKeys.SOUNDS;
				} else if (self instanceof FontManager) {
					fabric_id = ResourceReloadListenerKeys.FONTS;
				} else if (self instanceof BakedModelManager) {
					fabric_id = ResourceReloadListenerKeys.MODELS;
				} else if (self instanceof LanguageManager) {
					fabric_id = ResourceReloadListenerKeys.LANGUAGES;
				} else if (self instanceof TextureManager) {
					fabric_id = ResourceReloadListenerKeys.TEXTURES;
				} else {
					fabric_id = new Identifier("minecraft", "private/" + self.getClass().getSimpleName().toLowerCase(Locale.ROOT));
				}
			}

			return fabric_id;
		}
	}

	@Mixin({
			/* public */
			RecipeManager.class, ServerAdvancementLoader.class, CommandFunctionManager.class, LootManager.class, RegistryTagManager.class
			/* private */
	})
	public abstract static class Server implements IdentifiableResourceReloadListener {
		private Collection<Identifier> fabric_idDeps;
		private Identifier fabric_id;

		@Override
		@SuppressWarnings({"ConstantConditions", "RedundantCast"})
		public Collection<Identifier> getFabricDependencies() {
			if (fabric_idDeps == null) {
				Object self = this;

				if (self instanceof RegistryTagManager) {
					fabric_idDeps = Collections.emptyList();
				} else {
					fabric_idDeps = Collections.singletonList(ResourceReloadListenerKeys.TAGS);
				}
			}

			return fabric_idDeps;
		}

		@Override
		@SuppressWarnings({"ConstantConditions", "RedundantCast"})
		public Identifier getFabricId() {
			if (fabric_id == null) {
				Object self = this;

				if (self instanceof RecipeManager) {
					fabric_id = ResourceReloadListenerKeys.RECIPES;
				} else if (self instanceof ServerAdvancementLoader) {
					fabric_id = ResourceReloadListenerKeys.ADVANCEMENTS;
				} else if (self instanceof CommandFunctionManager) {
					fabric_id = ResourceReloadListenerKeys.FUNCTIONS;
				} else if (self instanceof LootManager) {
					fabric_id = ResourceReloadListenerKeys.LOOT_TABLES;
				} else if (self instanceof RegistryTagManager) {
					fabric_id = ResourceReloadListenerKeys.TAGS;
				} else {
					fabric_id = new Identifier("minecraft", "private/" + self.getClass().getSimpleName().toLowerCase(Locale.ROOT));
				}
			}

			return fabric_id;
		}
	}
}
