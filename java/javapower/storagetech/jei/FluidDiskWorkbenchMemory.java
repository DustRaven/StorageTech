package javapower.storagetech.jei;

import com.mojang.blaze3d.matrix.MatrixStack;

import javapower.storagetech.core.ResourceLocationRegister;
import javapower.storagetech.core.StorageTech;
import javapower.storagetech.render.ClientDiskOverlay;
import javapower.storagetech.util.Tools;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class FluidDiskWorkbenchMemory
{
	public static class Category implements IRecipeCategory<Wrapper>
	{
		
		public static final ResourceLocation UID = new ResourceLocation(StorageTech.MODID,"diskfwbm");
		private String localizedName, info_value;
		private IDrawableStatic background;
		
		public Category(IGuiHelper guiHelper)
		{
			localizedName = I18n.format("jei."+StorageTech.MODID+".category.diskfwbm");
			info_value = I18n.format("jei."+StorageTech.MODID+".info.diskwbm.value");
			
			background = guiHelper.createDrawable(ResourceLocationRegister.textrue_gui_jei_recipe, 28, 81, 56, 82);
		}

		@Override
		public ResourceLocation getUid()
		{
			return UID;
		}

		@Override
		public Class<? extends Wrapper> getRecipeClass()
		{
			return Wrapper.class;
		}

		@Override
		public String getTitle()
		{
			return localizedName;
		}

		@Override
		public IDrawable getBackground()
		{
			return background;
		}

		@Override
		public IDrawable getIcon()
		{
			return null;
		}

		@Override
		public void setIngredients(Wrapper recipe, IIngredients ingredients)
		{
			ingredients.setInput(VanillaTypes.ITEM, recipe.input);
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipe, IIngredients ingredients)
		{
			IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
			itemStacks.init(0, true, 19, 1);
			itemStacks.set(ingredients);
		}
		
		@Override
		public void draw(Wrapper recipe, MatrixStack matrixStack, double mouseX, double mouseY)
		{
			IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
			
            Matrix4f textLocation = matrixStack.getLast().getMatrix();
            String text = recipe.getString(info_value);
            int text_x = -(ClientDiskOverlay.minecraft.fontRenderer.getStringWidth(text))/2 + background.getWidth()/2;
        	ClientDiskOverlay.minecraft.fontRenderer.renderString(text, /*X*/ text_x, /*Y*/ 71, 0xffffff, false, textLocation, renderType, false, 0, 15728880);
			renderType.finish();
		}
		
	}
	
	public static class Wrapper
	{
		private ItemStack input;
		private long value;
		
		public Wrapper(ItemStack _input, long _value)
		{
			input = _input;
			value = _value;
		}
		
		public void getIngredients(IIngredients ing)
		{
			ing.setInput(VanillaTypes.ITEM, input);
		}
		
		public String getString(String info_value)
		{
			if(value == -1)
				return info_value;
			
			return "+ "+Tools.longFormatToString(value);
		}
	}
}
