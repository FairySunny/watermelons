package icu.sunny.mc.watermelons;

import icu.sunny.mc.watermelons.block.PicnicBasketBlock;
import icu.sunny.mc.watermelons.block.WatermimicBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatermelonsMod implements ModInitializer {
	public static final String MOD_ID = "watermelons";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block WATERMELON = new Block(
			FabricBlockSettings
					.of(Material.GOURD, MapColor.LIME)
					.strength(0.5f)
					.sounds(BlockSoundGroup.WOOD)
	);
	public static final Block RARE_WATERMELON = new Block(
			FabricBlockSettings
					.of(Material.GOURD, MapColor.CYAN)
					.strength(0.5f)
					.sounds(BlockSoundGroup.WOOD)
	);
	public static final Block WATERMIMIC_BLOCK = new WatermimicBlock(
			FabricBlockSettings
					.of(Material.GOURD, MapColor.LIME)
					.breakInstantly()
					.sounds(BlockSoundGroup.WOOD)
	);
	public static final Block PICNIC_BASKET_BLOCK = new PicnicBasketBlock(
			FabricBlockSettings
					.of(Material.WOOD)
					.strength(1.0f)
					.sounds(BlockSoundGroup.WOOD)
	);

	private void registerBlock(String name, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
	}

	@Override
	public void onInitialize() {
		LOGGER.info("WatermelonsMod.onInitialize begin");

		registerBlock("watermelon", WATERMELON);
		registerBlock("rare_watermelon", RARE_WATERMELON);
		registerBlock("watermimic", WATERMIMIC_BLOCK);
		registerBlock("picnic_basket", PICNIC_BASKET_BLOCK);

		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld().or(BiomeSelectors.foundInTheNether()).or(BiomeSelectors.foundInTheEnd()),
				GenerationStep.Feature.VEGETAL_DECORATION,
				RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MOD_ID, "watermelon"))
		);

		LOGGER.info("WatermelonsMod.onInitialize end");
	}
}
