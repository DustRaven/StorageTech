package javapower.storagetech.util;

import javapower.storagetech.core.StorageTech;
import javapower.storagetech.item.ItemCustomEnergyStoragePart;
import javapower.storagetech.item.ItemCustomFluidStoragePart;
import javapower.storagetech.item.ItemCustomStoragePart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PartValue
{
	private Item item;
	private int value;
	private EPartType type;
	
	public PartValue(Item _item, int _value, EPartType _type)
	{
		item = _item;
		value = _value;
		type = _type;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public int getValue(ItemStack stack)
	{
		return value;
	}
	
	public EPartType getType()
	{
		return type;
	}
	
	public ItemStack createPart(int value)
	{
		if(type == EPartType.ITEM)
			return ItemCustomStoragePart.createItem(value);
		if(type == EPartType.FLUID)
			return ItemCustomFluidStoragePart.createItem(value);
		if(type == EPartType.ENERGY)
			return ItemCustomEnergyStoragePart.createItem(value);
		
		if(type == EPartType.CHEMICAL && StorageTech.MOD_MEKANISM_IS_LOADED)
			return javapower.storagetech.mekanism.api.MekanismUtils.createPart(value);
		
		return ItemCustomStoragePart.createItem(value);
	}
}
