package javapower.storagetech.mekanism.block;

import java.util.List;

import javax.annotation.Nullable;

import com.refinedmods.refinedstorage.block.BlockDirection;
import com.refinedmods.refinedstorage.block.NetworkNodeBlock;
import com.refinedmods.refinedstorage.container.factory.PositionalTileContainerProvider;
import com.refinedmods.refinedstorage.item.blockitem.BaseBlockItem;
import com.refinedmods.refinedstorage.util.NetworkUtils;

import javapower.storagetech.block.STBlocks;
import javapower.storagetech.core.StorageTech;
import javapower.storagetech.mekanism.container.ContainerChemicalGrid;
import javapower.storagetech.mekanism.tileentity.TileEntityChemicalGrid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockChemicalGrid extends NetworkNodeBlock
{
	public static final BooleanProperty CONNECTED = STBlocks.CONNECTED;

	protected Item thisItem;
	public static String raw_name = "chemicalgrid";
	
	public BlockChemicalGrid()
	{
		super(STBlocks.DEFAULT_BLOCK_PROPERTIES);
		setRegistryName(StorageTech.MODID, raw_name);
		setDefaultState(getDefaultState().with(CONNECTED, Boolean.valueOf(false)));
	}
	
	public Item getItem()
	{
		if(thisItem == null)
		{
			thisItem = new BaseBlockItem(this, new Item.Properties().group(StorageTech.creativeTab))
			{
				@Override
			    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
				{
			        super.addInformation(stack, world, tooltip, flag);
			    }

			    @Override
			    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
			    {
			        ItemStack stack = player.getHeldItem(hand);
			        return new ActionResult<>(ActionResultType.PASS, stack);
			    }
			};
			
			thisItem.setRegistryName(StorageTech.MODID,raw_name);
		}
		return thisItem;
	}
	
	@Override
	public Item asItem()
	{
		return getItem();
	}

	@Override
	public Block getBlock()
	{
		return this;
	}
	
	@Override
    public BlockDirection getDirection()
	{
        return BlockDirection.HORIZONTAL;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TileEntityChemicalGrid();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!world.isRemote)
        {
           return NetworkUtils.attemptModify(world, pos, player, () -> NetworkHooks.openGui(
                   (ServerPlayerEntity) player,
                   new PositionalTileContainerProvider<TileEntityChemicalGrid>(
                       new TranslationTextComponent("gui.storagetech.chemical_grid"),
                       (tile, windowId, inventory, p) -> new ContainerChemicalGrid(tile, player, windowId),
                       pos
                   ),
                   pos
               ));
        }

        return ActionResultType.SUCCESS;
    }
    
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder)
    {
    	super.fillStateContainer(builder);
    	builder.add(CONNECTED);
    }
}
