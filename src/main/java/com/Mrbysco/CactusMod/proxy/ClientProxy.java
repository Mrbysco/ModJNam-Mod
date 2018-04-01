package com.Mrbysco.CactusMod.proxy;

import com.Mrbysco.CactusMod.entities.EntityCactiCart;
import com.Mrbysco.CactusMod.entities.EntityCactusCow;
import com.Mrbysco.CactusMod.entities.EntityCactusGolem;
import com.Mrbysco.CactusMod.entities.EntityCactusTnt;
import com.Mrbysco.CactusMod.entities.EntitySpike;
import com.Mrbysco.CactusMod.render.RenderCactiCart;
import com.Mrbysco.CactusMod.render.RenderCactusCow;
import com.Mrbysco.CactusMod.render.RenderCactusGolem;
import com.Mrbysco.CactusMod.render.RenderCactusTnt;
import com.Mrbysco.CactusMod.render.RenderSpike;
import com.Mrbysco.CactusMod.render.TileEntityCactusChestRenderer;
import com.Mrbysco.CactusMod.tileentities.TileEntityCactusChest;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit()
	{
		RegisterEntityRenders();
	}
	
	@Override
	public void init()
	{
		
	}
	
	public static void RegisterEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusGolem.class, RenderCactusGolem.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusCow.class, RenderCactusCow.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactiCart.class, RenderCactiCart.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusTnt.class, RenderCactusTnt.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpike.class, RenderSpike.FACTORY);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCactusChest.class, new TileEntityCactusChestRenderer());
	}
}
