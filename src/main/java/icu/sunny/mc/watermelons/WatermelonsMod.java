package icu.sunny.mc.watermelons;

import icu.sunny.mc.watermelons.block.PicnicBasketBlock;
import icu.sunny.mc.watermelons.block.WatermimicBlock;
import icu.sunny.mc.watermelons.entity.mob.WatermimicEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class WatermelonsMod implements ModInitializer {
	public static final String MOD_ID = "watermelons";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item[] KEY_ITEMS = Stream
			.iterate(0, i -> i + 1)
			.limit(26)
			.map(
					i -> registerItem(
							"key_" + (char)('a' + i),
							new Item(new FabricItemSettings().rarity(Rarity.RARE))
					)
			)
			.toArray(Item[]::new);

	public static final Block WATERMELON_BLOCK = registerBlock(
			"watermelon",
			new Block(
					FabricBlockSettings
							.of(Material.GOURD, MapColor.LIME)
							.strength(0.5f)
							.sounds(BlockSoundGroup.WOOD)
			)
	);
	public static final Block RARE_WATERMELON_BLOCK = registerBlock(
			"rare_watermelon",
			new Block(
					FabricBlockSettings
							.of(Material.GOURD, MapColor.CYAN)
							.strength(0.5f)
							.sounds(BlockSoundGroup.WOOD)
			)
	);
	public static final Block WATERMIMIC_BLOCK = registerBlock(
			"watermimic",
			new WatermimicBlock(
					FabricBlockSettings
							.of(Material.GOURD, MapColor.LIME)
							.breakInstantly()
							.sounds(BlockSoundGroup.WOOD)
			)
	);
	public static final Block PICNIC_BASKET_BLOCK = registerBlock(
			"picnic_basket",
			new PicnicBasketBlock(
					FabricBlockSettings
							.of(Material.WOOD)
							.strength(1.0f)
							.sounds(BlockSoundGroup.WOOD)
			)
	);

	public static final EntityType<WatermimicEntity> WATERMIMIC_ENTITY = registerLivingEntity(
			"watermimic",
			FabricEntityTypeBuilder
					.create(SpawnGroup.MONSTER, WatermimicEntity::new)
					.dimensions(EntityDimensions.fixed(1.0f, 1.0f))
					.trackRangeChunks(8),
			HostileEntity
					.createHostileAttributes()
					.add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)
					.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
					.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
	);

	private static Item registerItem(String name, Item item) {
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
		return item;
	}

	private static Block registerBlock(String name, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
		return block;
	}

	private static <T extends LivingEntity> EntityType<T> registerLivingEntity(String name, FabricEntityTypeBuilder<T> typeBuilder, DefaultAttributeContainer.Builder attributeBuilder) {
		EntityType<T> type = Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID, name), typeBuilder.build());
		FabricDefaultAttributeRegistry.register(type, attributeBuilder);
		return type;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("WatermelonsMod.onInitialize begin");

		BiomeModifications.addFeature(
				BiomeSelectors.tag(TagKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "generates_watermelons"))),
				GenerationStep.Feature.VEGETAL_DECORATION,
				RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MOD_ID, "watermelon"))
		);

		LOGGER.info("WatermelonsMod.onInitialize end");
	}
}
