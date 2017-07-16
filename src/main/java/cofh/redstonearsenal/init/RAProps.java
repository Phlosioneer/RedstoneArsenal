package cofh.redstonearsenal.init;

import cofh.api.item.IMultiModeItem;
import cofh.core.gui.CreativeTabCore;
import cofh.core.key.KeyBindingItemMultiMode;
import cofh.core.util.helpers.StringHelper;
import cofh.redstonearsenal.RedstoneArsenal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class RAProps {

	private RAProps() {

	}

	public static void preInit() {

		configCommon();
		configClient();
	}

	/* HELPERS */
	private static void configCommon() {

		String category;
		String comment;

		category = "General";
		comment = "If TRUE, explosions generated by Redstone Arsenal will destroy blocks.";
		explosionsDestroyBlocks = RedstoneArsenal.CONFIG.getConfiguration().getBoolean("ExplosionsDestroyBlocks", category, explosionsDestroyBlocks, comment);
	}

	private static void configClient() {

		/* CREATIVE TABS */
		RedstoneArsenal.tabCommon = new CreativeTabCore("redstonearsenal") {

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getIconItemStack() {

				ItemStack iconStack = new ItemStack(RAEquipment.ToolSet.FLUX.itemSword);
				iconStack.setTagCompound(new NBTTagCompound());
				iconStack.getTagCompound().setBoolean("CreativeTab", true);
				iconStack.getTagCompound().setInteger("Energy", 32000);
				iconStack.getTagCompound().setInteger("Mode", 1);

				return iconStack;
			}

		};
	}

	public static void addEmpoweredTip(IMultiModeItem item, ItemStack stack, List<String> tooltip) {

		if (item.getMode(stack) == 1) {
			tooltip.add(StringHelper.localizeFormat("info.redstonearsenal.tool.chargeOff", StringHelper.getKeyName(KeyBindingItemMultiMode.INSTANCE.getKey())));
		} else {
			tooltip.add(StringHelper.localizeFormat("info.redstonearsenal.tool.chargeOn", StringHelper.getKeyName(KeyBindingItemMultiMode.INSTANCE.getKey())));
		}
	}

	/* GENERAL */
	public static boolean explosionsDestroyBlocks = false;

	/* INTERFACE */
	public static boolean showArmorCharge = true;
	public static boolean showToolCharge = true;

}
