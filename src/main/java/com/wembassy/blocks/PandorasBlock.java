package com.wembassy.blocks;

import java.lang.reflect.Constructor;
import java.util.Random;

import com.wembassy.PandorasBlockCore;
import com.wembassy.settings.PandorasBlockSettings;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class PandorasBlock extends Block {

	public IIcon[] icons = new IIcon[6];
	private Boolean activateDrop = false;
	private Integer myDrop;
	
	public PandorasBlock(Material mat) {
		super(mat);
		this.setBlockName("Pandora's Block");
		this.setCreativeTab(CreativeTabs.tabMisc);
		
		this.setHardness(0.5f);
		this.setStepSound(Block.soundTypeAnvil);
		this.setBlockTextureName(PandorasBlockCore.MODID + ":pandorasblocktexture");
		this.setHarvestLevel("pickaxe", 1);
		
	}
	
	/**
	 * Returns the Correct icon texture for the provided side.
	 */
	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icons[side];
	}
	
	/**
	 * 
	 */
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < 6; i++) {
			this.icons[i] = reg.registerIcon(this.textureName);
		}
	}
	
	/**
	 * When a player destroys the block, trigger one of our canned events.
	 * 
	 */
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) { 
		
		Random rand = new Random();
		myDrop = rand.nextInt(PandorasBlockCore.configuration.drops.size());
		PandorasBlockSettings drop = PandorasBlockCore.configuration.drops.get(myDrop);
		
		if (drop.harmful) {
			  world.playSoundEffect(x, y, z, "ambient.weather.thunder", 2.0f, 1.0f);
			}
			else {
			  world.playSoundEffect(x, y, z, "fireworks.launch", 2.0f, 1.0f);
			  world.playSoundEffect(x, y, z, "fireworks.largeBlast", 2.0f, 1.0f);
			}
		
		if ( drop.type.compareTo( new String("item" ) ) == 0 && activateDrop) {
			dropItem(world, x, y, z, meta);
			activateDrop = false;
		}
		else if ( drop.type.compareTo( new String("block" ) ) == 0 && activateDrop ) {
			dropBlock(world, x, y, z, meta);
			activateDrop = false;
		}
		else if ( drop.type.compareTo( new String("entity" ) ) == 0 && activateDrop ) {
			dropEntity(world, x, y, z, meta);
			activateDrop = false;
		}
		else {
			activateDrop = true;
		}	
	}
	
	/**
	 * This callback will drop the configured entity.
	 */
	private void dropEntity(World world, double x, double y, double z, int meta) {
		PandorasBlockSettings drop = PandorasBlockCore.configuration.drops.get(myDrop);
		Double  xPos = ( drop.posX != null ? drop.posX : x) + drop.offsetX, 
	 			yPos = ( drop.posY != null ? drop.posY : y) + drop.offsetY, 
	 			zPos = ( drop.posZ != null ? drop.posZ : z) + drop.offsetZ;
		
		Entity conjuredEntity;
		try {
		
		conjuredEntity = EntityList.createEntityByName(drop.Name, world);
		conjuredEntity.setPosition(xPos, yPos, zPos); // put the location here that you want
		
		world.spawnEntityInWorld(conjuredEntity);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This callback will drop the configured block.
	 */
	private void dropBlock(World world, double x, double y, double z, int meta) {
		PandorasBlockSettings drop = PandorasBlockCore.configuration.drops.get(myDrop);
		Double  xPos = ( drop.posX != null ? drop.posX : x) + drop.offsetX, 
	 			yPos = ( drop.posY != null ? drop.posY : y) + drop.offsetY, 
	 			zPos = ( drop.posZ != null ? drop.posZ : z) + drop.offsetZ;
		Block block;
		if (drop.Name.length() > 0) {
			block = GameData.getBlockRegistry().getObject(drop.Name);
		}
		else {
			block = GameData.getBlockRegistry().getObjectById(drop.ID);
		}
		ItemStack stack = new ItemStack(block);
		EntityItem item = new EntityItem( world, xPos, yPos, zPos, stack);
		world.spawnEntityInWorld(item);
	}
	
	/**
	 * This callback will drop the configured item.
	 */
     private void dropItem(World world, double x, double y, double z, int meta) { 
    	 	PandorasBlockSettings drop = PandorasBlockCore.configuration.drops.get(myDrop);
    	 	Double  xPos = ( drop.posX != null ? drop.posX : x) + drop.offsetX, 
    	 			yPos = ( drop.posY != null ? drop.posY : y) + drop.offsetY, 
    	 			zPos = ( drop.posZ != null ? drop.posZ : z) + drop.offsetZ;
    	 	
    	 	EntityItem item;
    	 	ItemStack stack;
    	 	if (drop.Name.length() > 0) {
    	 		// Get Item by name.
    	 		stack = new ItemStack( GameData.getItemRegistry().getObject(drop.Name));
    	 	}
    	 	else {
    	 		// Get Item by ID.
    	 		stack = new ItemStack( GameData.getItemRegistry().getObjectById(drop.ID) );
    	 	}
    	 	item = new EntityItem( world, xPos, yPos, zPos, stack);
    	 	world.spawnEntityInWorld(item);
     }
	 
}
