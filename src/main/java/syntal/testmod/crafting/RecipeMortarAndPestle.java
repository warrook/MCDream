package syntal.testmod.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RecipeMortarAndPestle implements IRecipe
{
    public RecipeMortarAndPestle()
    {

    }

    public boolean matches(InventoryCrafting inv, World worldIn) {
        return false;
    }

    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return null;
    }

    public boolean canFit(int width, int height) {
        return false;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    public IRecipe setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return null;
    }

    public Class<IRecipe> getRegistryType() {
        return null;
    }
}
