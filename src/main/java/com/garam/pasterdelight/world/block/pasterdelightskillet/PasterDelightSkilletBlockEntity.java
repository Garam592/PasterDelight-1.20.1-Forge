package com.garam.pasterdelight.world.block.pasterdelightskillet;

import com.garam.pasterdelight.helper.BlockEntityTypeHelper;
import com.garam.pasterdelight.helper.GetBottomBlockCapability;
import com.garam.pasterdelight.mixin.SkilletBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.registry.ModSounds;

import java.util.Optional;

public class PasterDelightSkilletBlockEntity extends SkilletBlockEntity
{
    public ItemStack outputItemStack = ItemStack.EMPTY;
    public IItemHandler itemHandler = new PasterDelightSkilletItemHandler(this);
    public Optional<CampfireCookingRecipe> recipe = Optional.empty();
    public ItemStack resultStack;

    public PasterDelightSkilletBlockEntity(BlockEntityType blockEntityType, BlockPos blockPosition, BlockState blockState)
    {
        super(prepare(blockPosition, blockEntityType), blockState);
    }

    private static BlockPos prepare(BlockPos blockPosition, BlockEntityType<?> blockEntityType)
    {
        BlockEntityTypeHelper.setPendingType(blockEntityType);
        return blockPosition;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
    {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
        {
            return LazyOptional.of(() -> itemHandler).cast();
        }
            else
            {
                return LazyOptional.empty();
            }
    }

    public ItemStack insertCookingStack(ItemStack itemStack, boolean simulate)
    {
        if (!itemStack.isEmpty())
        {
            Optional<CampfireCookingRecipe> foundedRecipe = ((SkilletBlockEntityAccessor)this).getMatchingRecipeInvoker(new SimpleContainer(itemStack));
            if (!foundedRecipe.isEmpty())
            {
                if (!simulate)
                {
                    ItemStack remainder = getInventory().insertItem(0, itemStack.copy(), false);

                    if (remainder.getCount() < itemStack.getCount())
                    {
                        ((SkilletBlockEntityAccessor)this).setCookingTimeTotalAccessor(SkilletBlock.getSkilletCookingTime(foundedRecipe.get().getCookingTime(), ((SkilletBlockEntityAccessor)this).fireAspectLevelAccessor()));

                        if (level != null && isHeated(level, getBlockPos()))
                        {
                            BlockPos position = getBlockPos();
                            level.playSound(null, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5, ModSounds.BLOCK_SKILLET_ADD_FOOD.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                        }
                    }

                    return remainder;
                }
                    else
                    {
                        return getInventory().insertItem(0, itemStack.copy(), true);
                    }
            }
                else
                {
                    return itemStack;
                }
        }
            else
            {
                return ItemStack.EMPTY;
            }
    }

    public boolean hasCookingRecipe(ItemStack stack)
    {
        return !stack.isEmpty() && ((SkilletBlockEntityAccessor)this).getMatchingRecipeInvoker(new SimpleContainer(stack)).isPresent();
    }

    public ItemStack getOutputStack()
    {
        return outputItemStack;
    }

    public ItemStack extractOutput(int amount, boolean simulate)
    {
        if (outputItemStack.isEmpty() || amount <= 0)
        {
            return ItemStack.EMPTY;
        }

        int extractCount = Math.min(amount, outputItemStack.getCount());
        ItemStack extracted = outputItemStack.copy();
        extracted.setCount(extractCount);

        if (!simulate)
        {
            outputItemStack.shrink(extractCount);

            if (outputItemStack.isEmpty())
            {
                outputItemStack = ItemStack.EMPTY;
            }

            inventoryChanged();
        }

        return extracted;
    }

    public void pasterDelightCookAndOutputItems(ItemStack cookingStack, Level level)
    {
        if (level != null)
        {
            if (recipe.isEmpty())
            {
                ((SkilletBlockEntityAccessor)this).setCookingTimeAccessor(0);
                SimpleContainer wrapper = new SimpleContainer(cookingStack);
                recipe = ((SkilletBlockEntityAccessor)this).getMatchingRecipeInvoker(wrapper);
                if(recipe.isPresent())
                {
                    resultStack = recipe.get().assemble(wrapper, level.registryAccess());
                    if(!outputItemStack.isEmpty() && (!ItemStack.isSameItemSameTags(outputItemStack, resultStack) || outputItemStack.getCount() + resultStack.getCount() > outputItemStack.getMaxStackSize()))
                    {
                        recipe = Optional.empty();
                    }
                }
            }
                else
                {
                    ((SkilletBlockEntityAccessor)this).setCookingTimeAccessor(((SkilletBlockEntityAccessor)this).getCookingTimeAccessor() + 1);

                    if (((SkilletBlockEntityAccessor)this).getCookingTimeAccessor() >= ((SkilletBlockEntityAccessor)this).cookingTimeTotalAccessor())
                    {
                        ((SkilletBlockEntityAccessor)this).setCookingTimeAccessor(0);
                        recipe = Optional.empty();

                        if(outputItemStack.isEmpty())
                        {
                            outputItemStack = resultStack.copy();
                        }
                            else
                            {
                                outputItemStack.grow(resultStack.getCount());
                            }

                        getInventory().extractItem(0, 1, false);
                        inventoryChanged();
                    }
                }
        }
    }

    @Override
    public ItemStack removeItem()
    {
        ItemStack cooked = takeOutput();

        if (!cooked.isEmpty())
        {
            inventoryChanged();
            return cooked;
        }
            else
            {
                recipe = Optional.empty();
                return super.removeItem();
            }
    }

    public ItemStack takeOutput()
    {
        if (!outputItemStack.isEmpty())
        {
            ItemStack buffer = outputItemStack.copy();
            outputItemStack = ItemStack.EMPTY;
            return buffer;
        }
            else
            {
                return ItemStack.EMPTY;
            }
    }

    public void tick()
    {
        if (level != null && !level.isClientSide)
        {
            if (!outputItemStack.isEmpty())
            {
                IItemHandler targetInventory = GetBottomBlockCapability.getTargetInventory(level, getBlockPos());
                if (targetInventory != null)
                {
                    ItemStack simulatedRemainder = ItemHandlerHelper.insertItemStacked(targetInventory, outputItemStack.copy(), true);
                    if(simulatedRemainder.getCount() < outputItemStack.getCount())
                    {
                        ItemStack extracted = itemHandler.extractItem(1, outputItemStack.getCount() - simulatedRemainder.getCount(), false);
                        if (!extracted.isEmpty())
                        {
                            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, extracted, false);
                            if (!remainder.isEmpty())
                            {
                                Block.popResource(level, getBlockPos().above(), remainder);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        outputItemStack = ItemStack.of(compoundTag.getCompound("outputItemStack"));
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.put("outputItemStack", outputItemStack.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag compoundTag = super.getUpdateTag();
        compoundTag.put("outputItemStack", outputItemStack.serializeNBT());
        return compoundTag;
    }
}
