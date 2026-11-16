package com.garam.pasterdelight.world.block;

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

public class PasterDelightPieBlock extends PieBlock {
    public PasterDelightPieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    // 重写方块右键吃掉功能，使帕斯特乐事的派放在地上正常食用时也能回复san和融梦能量，而且使料理buff的效果正常生效
    // TODO:个人觉得这个写法有些蠢，应该会有更好的写法……？
    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {

        // 原版派只能饿了吃；料理(COOK)buff 需要“吃饱了继续吃”来产生溢出
        boolean hasCook = player.hasEffect(ModEffects.COOK.get());
        if (!player.canEat(false) && !hasCook) {
            return InteractionResult.PASS;
        }
        ItemStack sliceStack = getPieSliceItem();
        ItemStack sliceCopy = sliceStack.copy();
        FoodProperties food = sliceStack.getItem().getFoodProperties();

        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            ForgeEventFactory.onItemUseStart(sp, sliceStack, sliceStack.getUseDuration());
        }

        player.getFoodData().eat(sliceStack.getItem(), sliceStack);
        ForgeEventFactory.onItemUseFinish(player, sliceCopy, 0, ItemStack.EMPTY);

        if (sliceStack.getItem().isEdible() && food != null) {
            for (Pair<MobEffectInstance, Float> effect : food.getEffects()) {
                if (!level.isClientSide && effect.getFirst() != null
                        && level.random.nextFloat() < effect.getSecond()) {
                    player.addEffect(new MobEffectInstance(effect.getFirst()));
                }
            }
        }

        if (!level.isClientSide && player instanceof ServerPlayer sp
                && sliceStack.getItem() instanceof PasterDreamFoodItem pfood) {
            if (pfood.sanAdd != 0) SanHelper.addPlayerSanAndSync(sp, pfood.sanAdd);
            if (pfood.meltDreamEnergyAdd != 0) MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(sp, pfood.meltDreamEnergyAdd);
        }

        int bites = state.getValue(BITES);
        if (bites < getMaxBites() - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.001);
        }
        return InteractionResult.SUCCESS;
    }
}
