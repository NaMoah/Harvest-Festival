package joshie.harvest.cooking.render;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import joshie.harvest.api.HFApi;
import joshie.harvest.blocks.tiles.TileCooking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.ArrayList;

import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.FIXED;

@SideOnly(Side.CLIENT)
public abstract class SpecialRendererCookware<T extends TileCooking> extends TileEntitySpecialRenderer<T> {
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private TIntObjectMap<EntityItem> items = new TIntObjectHashMap<>();

    @Override
    public final void renderTileEntityAt(T tile, double x, double y, double z, float tick, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        renderCookware(tile);
        GlStateManager.popMatrix();
    }

    protected void renderCookware(T tile) {
        ArrayList<ItemStack> ingredients = tile.getIngredients();
        ItemStack result = tile.getResult();
        if (result != null) {
            renderItem(-1, tile.getWorld(), result, tile.getPos());
        }

        int max = ingredients.size();
        for (int i = 0; i < max; i++) {
            ItemStack ingredient = ingredients.get(i);
            ResourceLocation fluid = HFApi.COOKING.getFluid(ingredient);
            if (fluid == null) {
                renderItem(i, tile.getWorld(), ingredient, tile.heightOffset[i], tile.rotations[i], tile.offset1[i], tile.offset2[i]);
            } else renderFluid(i, tile.getWorld(), fluid);
        }
    }

    public void renderFluid(int i, World world, ResourceLocation fluid) {}

    protected EntityItem getEntityItem(int id, World world, ItemStack stack) {
        EntityItem item = items.get(id);
        if (item == null) {
            item = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
            item.getEntityItem().stackSize = 1;
            item.hoverStart = 0.0F;
            items.put(id, item);
        }

        item.setEntityItemStack(stack);
        item.getEntityItem().stackSize = 1;
        return item;
    }

    public abstract void translateIngredient(boolean isBlock, float position, float rotation, float offset1, float offset2);
    public abstract void translateResult(boolean isBlock);

    private void renderItem(int id, World world, ItemStack stack, float position, float rotation, float offset1, float offset2) {
        EntityItem item = getEntityItem(id, world, stack);
        GlStateManager.pushMatrix();
        translateIngredient(stack.getItem() instanceof ItemBlock, position, rotation, offset1, offset2);
        MINECRAFT.getRenderItem().renderItem(item.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderItem(int id, World world, ItemStack stack) {
        EntityItem item = getEntityItem(id, world, stack);
        GlStateManager.pushMatrix();
        translateResult(stack.getItem() instanceof ItemBlock);

        //IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, world, null);
        //MINECRAFT.getRenderItem().renderItem(stack, model);
        //MINECRAFT.getRenderItem().renderItem(item.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderItem(int id, World world, ItemStack stack, BlockPos pos) {
        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (Minecraft.isAmbientOcclusionEnabled())  {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else  {
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        translateResult(stack.getItem() instanceof ItemBlock);
        GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
        GL14.glBlendColor(1F, 1F, 1F, 1F);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, FIXED);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    protected void setColorFromInteger(int color) {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, 1.0F);
    }
}
