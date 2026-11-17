package com.garam.pasterdelight.world.block.pasterdelightskillet;

import com.garam.pasterdelight.mixin.SkilletRendererAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import vectorwing.farmersdelight.client.renderer.SkilletRenderer;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Random;

public class PasterDelightSkilletBlockEntityRenderer extends SkilletRenderer
{
    public PasterDelightSkilletBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(SkilletBlockEntity skillet, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        super.render(skillet, partialTicks, poseStack, buffer, packedLight, packedOverlay);

        if(skillet instanceof PasterDelightSkilletBlockEntity pasterDelightSkillet)
        {
            Random originalRandom = ((SkilletRendererAccessor)this).randomAccessor();
            Direction direction = skillet.getBlockState().getValue(StoveBlock.FACING);
            int seed = pasterDelightSkillet.outputItemStack.isEmpty() ? 187 : Item.getId(pasterDelightSkillet.outputItemStack.getItem()) + pasterDelightSkillet.outputItemStack.getDamageValue();
            int posLong = (int)skillet.getBlockPos().asLong();
            originalRandom.setSeed(seed);
            if (!pasterDelightSkillet.outputItemStack.isEmpty())
            {
                int itemRenderCount = getModelCount(pasterDelightSkillet.outputItemStack);

                for(int i = 0; i < itemRenderCount; ++i)
                {
                    poseStack.pushPose();
                    float xOffset = (originalRandom.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    float zOffset = (originalRandom.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    poseStack.translate((double)0.5F + (double)xOffset, 0.1 + 0.03 * (double)(i + 1) + 0.25, (double)0.5F + (double)zOffset);
                    float degrees = -direction.toYRot();
                    poseStack.mulPose(Axis.YP.rotationDegrees(degrees));
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    if (skillet.getLevel() != null)
                    {
                        Minecraft.getInstance().getItemRenderer().renderStatic(pasterDelightSkillet.outputItemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, skillet.getLevel(), posLong);
                    }

                    poseStack.popPose();
                }
            }
        }
    }
}
