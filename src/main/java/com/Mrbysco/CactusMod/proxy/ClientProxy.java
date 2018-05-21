package com.Mrbysco.CactusMod.proxy;

import com.Mrbysco.CactusMod.entities.EntityActualSpike;
import com.Mrbysco.CactusMod.entities.EntityCactiCart;
import com.Mrbysco.CactusMod.entities.EntityCactoni;
import com.Mrbysco.CactusMod.entities.EntityCactusBoat;
import com.Mrbysco.CactusMod.entities.EntityCactusCow;
import com.Mrbysco.CactusMod.entities.EntityCactusGolem;
import com.Mrbysco.CactusMod.entities.EntityCactusSheep;
import com.Mrbysco.CactusMod.entities.EntityCactusSlime;
import com.Mrbysco.CactusMod.entities.EntityCactusSnowman;
import com.Mrbysco.CactusMod.entities.EntityCactusTnt;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusCreeper;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusSkelly;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusSpider;
import com.Mrbysco.CactusMod.render.RenderActualSpike;
import com.Mrbysco.CactusMod.render.RenderCactiCart;
import com.Mrbysco.CactusMod.render.RenderCactoni;
import com.Mrbysco.CactusMod.render.RenderCactusBoat;
import com.Mrbysco.CactusMod.render.RenderCactusCow;
import com.Mrbysco.CactusMod.render.RenderCactusCreeper;
import com.Mrbysco.CactusMod.render.RenderCactusGolem;
import com.Mrbysco.CactusMod.render.RenderCactusSheep;
import com.Mrbysco.CactusMod.render.RenderCactusSkeleton;
import com.Mrbysco.CactusMod.render.RenderCactusSlime;
import com.Mrbysco.CactusMod.render.RenderCactusSnowman;
import com.Mrbysco.CactusMod.render.RenderCactusSpider;
import com.Mrbysco.CactusMod.render.RenderCactusTnt;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityActualSpike.class, RenderActualSpike.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusCreeper.class, RenderCactusCreeper.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSnowman.class, RenderCactusSnowman.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSlime.class, RenderCactusSlime.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSheep.class, RenderCactusSheep.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSpider.class, RenderCactusSpider.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSkelly.class, RenderCactusSkeleton.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusBoat.class, RenderCactusBoat.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactoni.class, RenderCactoni.FACTORY);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCactusChest.class, new TileEntityCactusChestRenderer());
	}
}
