package net.fexcraft.mod.remotestorage.block;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TransferTE extends TileEntity {

	public TransferTE(){}
	
	@Override
	public void invalidate(){
		super.invalidate();
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        //
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        //
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
    	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

	@Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
    	//
        return super.getCapability(capability, facing);
    }

}
