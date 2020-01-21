package com.mrbysco.cactusmod.proxy;

import com.mrbysco.cactusmod.entities.EntityActualSpike;
import com.mrbysco.cactusmod.entities.EntityCactiCart;
import com.mrbysco.cactusmod.entities.EntityCactoni;
import com.mrbysco.cactusmod.entities.EntityCactusBoat;
import com.mrbysco.cactusmod.entities.EntityCactusCow;
import com.mrbysco.cactusmod.entities.EntityCactusGolem;
import com.mrbysco.cactusmod.entities.EntityCactusPig;
import com.mrbysco.cactusmod.entities.EntityCactusSheep;
import com.mrbysco.cactusmod.entities.EntityCactusSlime;
import com.mrbysco.cactusmod.entities.EntityCactusSnowman;
import com.mrbysco.cactusmod.entities.EntityCactusTnt;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusCreeper;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusSkelly;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusSpider;
import com.mrbysco.cactusmod.render.RenderActualSpike;
import com.mrbysco.cactusmod.render.RenderCactiCart;
import com.mrbysco.cactusmod.render.RenderCactoni;
import com.mrbysco.cactusmod.render.RenderCactusBoat;
import com.mrbysco.cactusmod.render.RenderCactusCow;
import com.mrbysco.cactusmod.render.RenderCactusCreeper;
import com.mrbysco.cactusmod.render.RenderCactusGolem;
import com.mrbysco.cactusmod.render.RenderCactusPig;
import com.mrbysco.cactusmod.render.RenderCactusSheep;
import com.mrbysco.cactusmod.render.RenderCactusSkeleton;
import com.mrbysco.cactusmod.render.RenderCactusSlime;
import com.mrbysco.cactusmod.render.RenderCactusSnowman;
import com.mrbysco.cactusmod.render.RenderCactusSpider;
import com.mrbysco.cactusmod.render.RenderCactusTnt;
import com.mrbysco.cactusmod.render.TileEntityCactusChestRenderer;
import com.mrbysco.cactusmod.tileentities.TileEntityCactusChest;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusPig.class, RenderCactusPig.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSpider.class, RenderCactusSpider.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusSkelly.class, RenderCactusSkeleton.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactusBoat.class, RenderCactusBoat.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCactoni.class, RenderCactoni.FACTORY);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCactusChest.class, new TileEntityCactusChestRenderer());
	}
}
