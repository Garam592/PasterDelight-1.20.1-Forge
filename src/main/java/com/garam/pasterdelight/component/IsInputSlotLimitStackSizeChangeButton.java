package com.garam.pasterdelight.component;

import com.garam.pasterdelight.init.ModNetWork;
import com.garam.pasterdelight.network.cookingpotpacket.SyncIsInputSlotLimitStackSizePacket;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.PasterDelightCookingPotBlockEntity;
import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowCenteredString;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class IsInputSlotLimitStackSizeChangeButton extends Button
{
    public BlockEntity blockEntity;
    public boolean isInputSlotLimitStackSize = false;

    public IsInputSlotLimitStackSizeChangeButton(int x, int y, BlockEntity blockEntity)
    {
        super(x, y, 15, 15, Component.empty(), IsInputSlotLimitStackSizeChangeButton::onButtonPress, DEFAULT_NARRATION);

        this.blockEntity = blockEntity;

        if(blockEntity != null)
        {
            isInputSlotLimitStackSize = ((PasterDelightCookingPotBlockEntity)blockEntity).getIsInputSlotLimitStackSize();
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        guiGraphics.fill(getX(), getY(), getX() + 15, getY() + 15, (isHovered() ? 0xFFFFFFFF : 0xFF000000));
        guiGraphics.fill(getX() + 1, getY() + 1, getX() + 14, getY() + 14, 0xFF7F7F7F);
        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, getX() + 8, getY() + 8, (isInputSlotLimitStackSize ? "1" : "64"), 0xFFFFFFFF);

        if (isHovered())
        {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, (isInputSlotLimitStackSize ? Component.translatable("tooltip.pasterdelight.限制外部输入堆叠上限为1") : Component.translatable("tooltip.pasterdelight.不限制外部输入堆叠上限")), mouseX, mouseY);
        }
    }

    public static void onButtonPress(Button button)
    {
        if(button instanceof IsInputSlotLimitStackSizeChangeButton thisButton)
        {
            thisButton.isInputSlotLimitStackSize = !thisButton.isInputSlotLimitStackSize;
            ModNetWork.CHANNEL.sendToServer(new SyncIsInputSlotLimitStackSizePacket(thisButton.blockEntity.getBlockPos(), thisButton.isInputSlotLimitStackSize));
        }
    }
}
