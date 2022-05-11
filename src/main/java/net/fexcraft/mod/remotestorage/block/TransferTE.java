package net.fexcraft.mod.remotestorage.block;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TransferTE extends TileEntity {

	public String auth, token, company;

	public TransferTE(){}
	
	@Override
	public void invalidate(){
		super.invalidate();
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        if(compound.hasKey("auth")) auth = compound.getString("auth");
        if(compound.hasKey("token")) token = compound.getString("token");
        if(compound.hasKey("company")) company = compound.getString("company");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        if(auth != null) compound.setString("auth", auth);
        if(token != null) compound.setString("token", token);
        if(company != null) compound.setString("company", company);
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
