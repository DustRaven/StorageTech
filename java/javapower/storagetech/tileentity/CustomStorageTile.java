package javapower.storagetech.tileentity;

import javax.annotation.Nonnull;

import com.refinedmods.refinedstorage.api.storage.AccessType;
import com.refinedmods.refinedstorage.tile.NetworkNodeTile;
import com.refinedmods.refinedstorage.tile.config.IAccessType;
import com.refinedmods.refinedstorage.tile.config.IComparable;
import com.refinedmods.refinedstorage.tile.config.IPrioritizable;
import com.refinedmods.refinedstorage.tile.config.IWhitelistBlacklist;
import com.refinedmods.refinedstorage.tile.data.RSSerializers;
import com.refinedmods.refinedstorage.tile.data.TileDataParameter;

import javapower.storagetech.core.StorageTech;
import javapower.storagetech.node.NetworkNodeCustomStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class CustomStorageTile extends NetworkNodeTile<NetworkNodeCustomStorage>
{
	@ObjectHolder(StorageTech.MODID+":customstorageblock")
	public static final TileEntityType<CustomStorageTile> CURRENT_TILE = null;
	
    public static final TileDataParameter<Integer, CustomStorageTile> PRIORITY = IPrioritizable.createParameter();
    public static final TileDataParameter<Integer, CustomStorageTile> COMPARE = IComparable.createParameter();
    public static final TileDataParameter<Integer, CustomStorageTile> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final TileDataParameter<AccessType, CustomStorageTile> ACCESS_TYPE = IAccessType.createParameter();
    public static final TileDataParameter<Long, CustomStorageTile> STORED = new TileDataParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);

    public int storageCapacity = 1;
    
    public CustomStorageTile()
    {
        super(CURRENT_TILE);
        
        dataManager.addWatchedParameter(PRIORITY);
        dataManager.addWatchedParameter(COMPARE);
        dataManager.addWatchedParameter(WHITELIST_BLACKLIST);
        dataManager.addWatchedParameter(STORED);
        dataManager.addWatchedParameter(ACCESS_TYPE);
    }

    @Override
    @Nonnull
    public NetworkNodeCustomStorage createNode(World world, BlockPos pos)
    {
        return new NetworkNodeCustomStorage(world, pos, this);
    }
    
    /*@Override
    public void read(CompoundNBT compound)
    {
    	super.read(compound);
    	
    	if(compound.contains("capacity"))
    		storageCapacity = compound.getInt("capacity");
    }*/
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
    	super.write(compound);
    	compound.putInt("capacity", storageCapacity);
    	return compound;
    }
    
    @Override
    public void readUpdate(CompoundNBT tag)
    {
    	super.readUpdate(tag);
    	if(tag.contains("capacity"))
    		storageCapacity = tag.getInt("capacity");
    }
    
    @Override
    public CompoundNBT writeUpdate(CompoundNBT tag)
    {
    	super.writeUpdate(tag);
    	tag.putInt("capacity", storageCapacity);
    	return tag;
    }
}