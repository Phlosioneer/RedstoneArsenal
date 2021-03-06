package cofh.redstonearsenal.item.tool;

import cofh.core.init.CoreEnchantments;
import cofh.core.item.IEnchantableItem;
import cofh.redstonearsenal.init.RAEquipment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAxeFlux extends ItemToolFlux implements IEnchantableItem {

	public ItemAxeFlux(ToolMaterial toolMaterial) {

		super(-2.8F, toolMaterial);
		addToolClass("axe");
		damage = 7;
		damageCharged = 6;
		energyPerUseCharged = energyPerUseCharged * RAEquipment.multiblockScale / 100;

		effectiveBlocks.addAll(ItemAxe.EFFECTIVE_ON);

		effectiveMaterials.add(Material.WOOD);
		effectiveMaterials.add(Material.PLANTS);
		effectiveMaterials.add(Material.VINE);
		effectiveMaterials.add(Material.CACTUS);
		effectiveMaterials.add(Material.GOURD);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {

		if (EnumEnchantmentType.BREAKABLE.equals(enchantment.type)) {
			return enchantment.equals(Enchantments.UNBREAKING);
		}
		return enchantment.type.canEnchantItem(this) || enchantment.canApply(new ItemStack(Items.IRON_AXE));
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {

		return true;
	}

	/* IEnchantableItem */
	@Override
	public boolean canEnchant(ItemStack stack, Enchantment enchantment) {

		return super.canEnchant(stack, enchantment) || enchantment == CoreEnchantments.leech || enchantment == CoreEnchantments.vorpal;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {

		World world = player.world;
		IBlockState state = world.getBlockState(pos);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		Block block = state.getBlock();

		float refStrength = state.getPlayerRelativeBlockHardness(player, world, pos);
		if (refStrength != 0.0F) {
			if (isEmpowered(stack) && (block.isWood(world, pos) || canHarvestBlock(state, stack))) {
				for (int i = x - 1; i <= x + 1; i++) {
					for (int k = z - 1; k <= z + 1; k++) {
						for (int j = y - 2; j <= y + 2; j++) {
							BlockPos pos2 = new BlockPos(i, j, k);
							block = world.getBlockState(pos2).getBlock();
							if (block.isWood(world, pos2) || canHarvestBlock(state, stack)) {
								harvestBlock(world, pos2, player);
							}
						}
					}
				}
			}
			if (!player.capabilities.isCreativeMode) {
				useEnergy(stack, false);
			}
		}
		return false;
	}

}
