package com.wembassy;

import com.wembassy.blocks.PandorasBlock;
import com.wembassy.settings.PandorasBlockConfigurationHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid="pandorasblock", name="Pandora's Block", version="0.0.1")
public class PandorasBlockCore {

	public static String MODID="pandorasblock";
	public static String NAME="Pandora's Block";
	public static String VERSION = "0.0.1";
	
	public static Block pandoraBlock;
	
	@Instance(value = "pandorablock")
	public static PandorasBlockCore instance;
	
	// Configuration related variables
	public static PandorasBlockConfigurationHandler configuration;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) { 
		// Load Configuration File
		configuration = new PandorasBlockConfigurationHandler();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) { 
		System.out.println("PandorasBlock is opening, are you prepared?");
		// Register New Block with forge
		pandoraBlock = new PandorasBlock(Material.anvil);
		GameRegistry.registerBlock(pandoraBlock, MODID);
		
		// Register New Recipe for PandorasBlock
		GameRegistry.addShapedRecipe(
				new ItemStack(PandorasBlockCore.pandoraBlock, 1), 
				new Object[] { 
						"XXX",
						"XYX",
						"XXX",
						'X', Item.itemRegistry.getObject("chest"),
						'Y', Item.itemRegistry.getObject("egg")
				}
		);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) { 
		System.out.println("PandorasBlock has opened.");
	}
	
}

/***************************************************

Mod Goals:
1. Create a new block when broken triggers a random event.  
2. Load possible events from a XML configuration file.
3. Allow the new block to be craftable.

****/