package syntal.testmod.crafting;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import syntal.testmod.TestMod;

import java.util.Map;

public class MortarAndPestleRecipes
{
    private static final MortarAndPestleRecipes INSTANCE = new MortarAndPestleRecipes();
    private final Map<ItemStack, ItemStack> recipeList = Maps.newHashMap();

    public MortarAndPestleRecipes() {
    }

    // Need multiple slots, can't just copy furnace
    public void addRecipe(ItemStack input, ItemStack output) {
        if (getResult(input) != ItemStack.EMPTY) {
            TestMod.LOGGER.warn("Ignoring mortar and Pestle recipe with conflicting input:");
            return;
        }
        this.recipeList.put(input, output);
    }

    // Returns the crafting result of given item
    public ItemStack getResult(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : this.recipeList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey()))
                return entry.getValue();
        }

        return ItemStack.EMPTY;
    }

    // Checks stacks against each other
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}
