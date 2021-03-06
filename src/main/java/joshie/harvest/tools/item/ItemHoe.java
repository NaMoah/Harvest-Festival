package joshie.harvest.tools.item;

import com.google.common.collect.Multimap;
import joshie.harvest.core.base.item.ItemToolChargeable;
import joshie.harvest.core.helpers.EntityHelper;
import joshie.harvest.tools.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.HashSet;

public class ItemHoe extends ItemToolChargeable {
    public ItemHoe() {
        super("hoe", new HashSet<>());
    }

    @Override
    public int getFront(ToolTier tier) {
        switch (tier) {
            case BASIC:
                return 0;
            case COPPER:
                return 1;
            case SILVER:
                return 2;
            case GOLD:
                return 3;
            case MYSTRIL:
                return 5;
            case CURSED:
            case BLESSED:
                return 11;
            case MYTHIC:
                return 17;
            default:
                return 0;
        }
    }

    @Override
    public int getSides(ToolTier tier) {
        switch (tier) {
            case BASIC:
            case COPPER:
            case SILVER:
            case GOLD:
            case MYSTRIL:
                return 0;
            case CURSED:
            case BLESSED:
                return 1;
            case MYTHIC:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        ToolTier tier = getTier(stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)3F + (tier.getToolLevel() - 6.0F), 0));
        }

        return multimap;
    }

    protected void setBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        doParticles(stack, player, world, pos);
        if (!world.isRemote) {
            world.setBlockState(pos, state, 11);
        }
    }

    public EnumActionResult getHoeResult(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing facing) {
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) {
            return EnumActionResult.FAIL;
        } else {
            int original = stack.getItemDamage();
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
            if (hook != 0) {
                if (hook > 0) {
                    stack.setItemDamage(original);
                    return EnumActionResult.SUCCESS;
                } else return EnumActionResult.FAIL;
            }

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean allowed = worldIn.isAirBlock(pos.up()) || worldIn.getBlockState(pos.up()).getBlock() instanceof IPlantable;
            if (facing != EnumFacing.DOWN && allowed) {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                    setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT) {
                    switch (iblockstate.getValue(BlockDirt.VARIANT)) {
                        case DIRT:
                            setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        super.onUsingTick(stack, player, count);
    }

    @Override
    protected void onFinishedCharging(World world, EntityLivingBase entity, @Nullable RayTraceResult result, ItemStack stack, ToolTier tier) {
        if (result != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            BlockPos pos = result.getBlockPos();
            EnumFacing front = EntityHelper.getFacingFromEntity(player);
            if (player.canPlayerEdit(pos.offset(front), front, stack) && canUse(stack)) {
                for (int x2 = getXMinus(tier, front, pos.getX()); x2 <= getXPlus(tier, front, pos.getX()); x2++) {
                    for (int z2 = getZMinus(tier, front, pos.getZ()); z2 <= getZPlus(tier, front, pos.getZ()); z2++) {
                        if (canUse(stack)) {
                            BlockPos newPos = new BlockPos(x2, pos.getY(), z2);
                            getHoeResult(stack, player, world, newPos, EnumFacing.UP);
                        }
                    }
                }
            }
        }
    }

    private void doParticles(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
        displayParticle(world, pos, EnumParticleTypes.BLOCK_CRACK, Blocks.DIRT.getDefaultState());
        playSound(world, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS);
        ToolHelper.performTask(player, stack, getExhaustionRate(stack));
        if (world.getBlockState(pos.up()).getBlock() instanceof IPlantable) {
            world.setBlockToAir(pos.up());
        }
    }
}