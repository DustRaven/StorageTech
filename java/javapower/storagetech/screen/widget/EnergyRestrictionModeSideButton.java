package javapower.storagetech.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.refinedmods.refinedstorage.screen.BaseScreen;
import com.refinedmods.refinedstorage.screen.widget.sidebutton.SideButton;
import com.refinedmods.refinedstorage.tile.data.TileDataManager;
import com.refinedmods.refinedstorage.tile.data.TileDataParameter;

import javapower.storagetech.core.StorageTech;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class EnergyRestrictionModeSideButton extends SideButton
{
    private final TileDataParameter<Boolean, ?> parameter;

    public EnergyRestrictionModeSideButton(BaseScreen<?> screen, TileDataParameter<Boolean, ?> parameter)
    {
        super(screen);

        this.parameter = parameter;
    }

    @Override
    public String getTooltip()
    {
        return I18n.format("sidebutton.storagetech.energy_restriction") + "\n" + TextFormatting.GRAY + I18n.format("sidebutton.storagetech.energy_restriction." + parameter.getValue());
    }

    @Override
    protected void renderButtonIcon(MatrixStack matrixStack, int x, int y)
    {
    	screen.bindTexture(StorageTech.MODID, "icons.png");
        screen.blit(matrixStack, x, y, parameter.getValue() ? 16 : 0, 0, 16, 16);
    }

    @Override
    public void onPress()
    {
        TileDataManager.setParameter(parameter, !parameter.getValue());
    }
}