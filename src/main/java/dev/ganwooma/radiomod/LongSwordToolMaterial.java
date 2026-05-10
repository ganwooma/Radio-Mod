package dev.ganwooma.radiomod;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class LongSwordToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 2147483647;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 6.0f;
    }

    @Override
    public float getAttackDamage() {
        return 13.0f; // 기본 데미지
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY; // 수리 재료
    }
}
