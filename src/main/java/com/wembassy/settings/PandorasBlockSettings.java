package com.wembassy.settings;

import net.minecraft.nbt.NBTTagCompound;

public class PandorasBlockSettings {
	
	public String type = "item"; // This determines what type of event or action to take when the block is broken.
	public Integer posX = 0, posY = 0, posZ = 0; // Allows setting the arbitrary position.
	public Integer offsetX, offsetY, offsetZ; // Places the element in the world with an offset to the block or set position.
	public Integer amount = 1; // How many times the drop will happen when the block is broke
	public Boolean reset = true; // Determines if each instance of a drop should have the properties randomized.
	public Boolean harmful = true; // Lets pandora know if this is a harmful drop or not.
	
	/** Item and Block Drop parameters */
	public Integer ID;
    public String Name;
    public Integer damage;
    public NBTTagCompound Tags;
	
    /** Structure parameters */
    
}
