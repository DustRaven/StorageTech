package javapower.storagetech.mekanism.screen;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.refinedmods.refinedstorage.RS;
import com.refinedmods.refinedstorage.api.util.IFilter;
import com.refinedmods.refinedstorage.render.RenderSettings;

import javapower.storagetech.core.StorageTech;
import javapower.storagetech.mekanism.container.ContainerChemicalFilter;
import javapower.storagetech.mekanism.item.ItemChemicalFilter;
import javapower.storagetech.mekanism.packet.PacketChemicalFilterUpdateMessage;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenChemicalFilter extends BaseScreen2<ContainerChemicalFilter>
{
	//private final ItemStack stack;

    //private int compare;
    private int mode;
    //private boolean modFilter;
    private final String name;

    //private CheckboxWidget modFilterCheckBox;
    private Button modeButton;
    private TextFieldWidget nameField;
    
	public ScreenChemicalFilter(ContainerChemicalFilter container, PlayerInventory inventory, ITextComponent title)
	{
		super(container, 176, 231, inventory, title);
		
		//this.stack = container.getStack();

        //this.compare = ItemChemicalFilter.getCompare(container.getStack());
        this.mode = ItemChemicalFilter.getMode(container.getStack());
        //this.modFilter = ItemChemicalFilter.isModFilter(container.getStack());
        this.name = ItemChemicalFilter.getName(container.getStack());	
	}
	
	@Override
    public void onPostInit(int x, int y)
	{
        /*addCheckBox(x + 7, y + 77, new TranslationTextComponent("gui.refinedstorage.filter.compare_nbt"), (compare & IComparer.COMPARE_NBT) == IComparer.COMPARE_NBT, btn -> {
            compare ^= IComparer.COMPARE_NBT;

            sendUpdate();
        });*/

        /*modFilterCheckBox = addCheckBox(0, y + 71 + 25, new TranslationTextComponent("gui.refinedstorage.filter.mod_filter"), modFilter, btn ->
        {
            modFilter = !modFilter;

            sendUpdate();
        });*/

        modeButton = addButton(x + 7, y + 71 + 21, 0, 20, new StringTextComponent(""), true, true, btn ->
        {
            mode = mode == IFilter.MODE_WHITELIST ? IFilter.MODE_BLACKLIST : IFilter.MODE_WHITELIST;

            updateModeButton(mode);

            sendUpdate();
        });

        updateModeButton(mode);

        nameField = new TextFieldWidget(font, x + 34, y + 121, 137 - 6, font.FONT_HEIGHT, new StringTextComponent(""));
        nameField.setText(name);
        nameField.setEnableBackgroundDrawing(false);
        nameField.setVisible(true);
        nameField.setCanLoseFocus(true);
        nameField.setFocused2(false);
        nameField.setTextColor(RenderSettings.INSTANCE.getSecondaryColor());
        nameField.setResponder(name -> sendUpdate());

        addButton(nameField);

        //addSideButton(new FilterTypeSideButton(this));
    }

    private void updateModeButton(int mode)
    {
        ITextComponent text = mode == IFilter.MODE_WHITELIST
            ? new TranslationTextComponent("sidebutton.refinedstorage.mode.whitelist")
            : new TranslationTextComponent("sidebutton.refinedstorage.mode.blacklist");

        modeButton.setWidth(font.getStringWidth(text.getString()) + 12);
        modeButton.setMessage(text);
        //modFilterCheckBox.x = modeButton.x + modeButton.getWidth() + 4;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers)
    {
        if (key == GLFW.GLFW_KEY_ESCAPE)
        {
            minecraft.player.closeScreen();

            return true;
        }

        if (nameField.keyPressed(key, scanCode, modifiers) || nameField.canWrite())
        {
            return true;
        }

        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public void tick(int x, int y)
    {
    	
    }

    @Override
    public void renderBackground(MatrixStack matrixStack, int x, int y, int mouseX, int mouseY)
    {
        bindTexture(RS.ID, "gui/filter.png");

        blit(matrixStack, x, y, 0, 0, xSize, ySize);
    }

    @Override
    public void renderForeground(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        renderString(matrixStack, 7, 7, title.getString());
        renderString(matrixStack, 7, 137, I18n.format("container.inventory"));
        
        super.renderForeground(matrixStack, mouseX, mouseY);
    }

    public void sendUpdate()
    {
    	StorageTech.INSTANCE_CHANNEL.sendToServer(new PacketChemicalFilterUpdateMessage(mode, nameField.getText()));
    }

}
