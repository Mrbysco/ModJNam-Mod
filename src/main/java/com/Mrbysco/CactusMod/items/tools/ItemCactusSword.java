package com.Mrbysco.CactusMod.items.tools;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.item.ItemSword;

public class ItemCactusSword extends ItemSword{
	private final float attackDamage;
    private final ToolMaterial material;
    
	public ItemCactusSword(String registryName) {
		super(CactusItems.cactusMaterial);
		
		this.material = CactusItems.cactusMaterial;
		
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.attackDamage = 3.0F + material.getAttackDamage();

		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
}
