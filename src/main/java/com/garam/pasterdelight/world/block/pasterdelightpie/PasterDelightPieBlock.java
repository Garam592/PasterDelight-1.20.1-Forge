package com.garam.pasterdelight.world.block.pasterdelightpie;

import com.garam.pasterdelight.helper.IsPlayerCanEat;
import com.mojang.datafixers.util.Pair;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class PasterDelightPieBlock extends PieBlock
{
    public PasterDelightPieBlock(Properties properties, Supplier<Item> pieSlice)
    {
        super(properties, pieSlice);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player)
    {
        ItemStack sliceStack = getPieSliceItem();
        if(IsPlayerCanEat.isPlayerCanEat(player, sliceStack.getItem()))
        {
            Item visualPieItem = sliceStack.getItem();
            int useDuration = visualPieItem.getUseDuration(sliceStack);

            visualPieItem.use(player.level(), player, InteractionHand.MAIN_HAND);
            useDuration = ForgeEventFactory.onItemUseStart(player, sliceStack, useDuration);
            if (useDuration <= 0)
            {
                player.stopUsingItem();
                return InteractionResult.FAIL;
            }

            for(int i = useDuration; i > 0; i--)
            {
                visualPieItem.onUseTick(player.level(), player, sliceStack, i);
            }

            ItemStack resultStack = sliceStack.getItem().finishUsingItem(sliceStack, player.level(), player);
            ItemStack finalStack = ForgeEventFactory.onItemUseFinish(player, sliceStack, useDuration, resultStack);
            if(!finalStack.isEmpty())
            {
                if (!player.getInventory().add(finalStack))
                {
                    player.drop(finalStack, false);
                }
            }
            player.stopUsingItem();

            int bites = state.getValue(BITES);
            if (bites < this.getMaxBites() - 1)
            {
                level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            }
                else
                {
                    level.removeBlock(pos, false);
                }

            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            if (level instanceof ServerLevel serverLevel)
            {
                serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.001);
            }

            return InteractionResult.SUCCESS;
        }
            else
            {
                return InteractionResult.PASS;
            }
    }
}
