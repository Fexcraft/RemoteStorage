package net.fexcraft.mod.remotestorage.block;

import net.fexcraft.lib.mc.api.registry.fBlock;
import net.fexcraft.mod.remotestorage.RemoteStorage;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@fBlock(modid = RemoteStorage.MODID, name = "transfer")
public class TransferBlock extends BlockContainer {

	public TransferBlock(){
		super(Material.IRON);
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldI, int meta){
		return new TransferTE();
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        if(world.isRemote) return false;
        if(player.isSneaking()) return true;
        //todo perm check
        //todo auth state check
        player.openGui(RemoteStorage.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

}
