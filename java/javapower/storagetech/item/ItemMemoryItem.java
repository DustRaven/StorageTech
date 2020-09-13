package javapower.storagetech.item;

import java.util.List;

import com.refinedmods.refinedstorage.render.Styles;

import javapower.storagetech.core.StorageTech;
import javapower.storagetech.util.Tools;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemMemoryItem extends Item
{
	public ItemMemoryItem()
	{
		super(STItems.DEFAULT_PROPERTIES);
		setRegistryName(StorageTech.MODID,"memory");
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(stack != null)
		{
			CompoundNBT nbt = stack.getTag();
			if(nbt != null && nbt.contains("memory"))
			{
				tooltip.add(new TranslationTextComponent(I18n.format("tooltip.storagetech.valuevib", Tools.longFormatToString(nbt.getLong("memory")))).func_230530_a_(Styles.GRAY));
				tooltip.add(new TranslationTextComponent(I18n.format("tooltip.storagetech.putvib")).func_230530_a_(Styles.GRAY));
			}
			else
			{
				tooltip.add(new TranslationTextComponent(I18n.format("tooltip.storagetech.novib")).func_230530_a_(Styles.GRAY));
			}
		}
	}
	
	public static ItemStack createItem(long quant)
	{
		ItemStack item = new ItemStack(STItems.item_memory, 1);
		CompoundNBT nbt = new CompoundNBT();
		nbt.putLong("memory", quant);
		item.setTag(nbt);
		
		return item;
	}
}
