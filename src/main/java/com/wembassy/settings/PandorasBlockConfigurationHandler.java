package com.wembassy.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.minecraftforge.common.config.Configuration;

public class PandorasBlockConfigurationHandler {

	private static String path = "config/pandoras_block.cfg";
	
	public static Configuration config;
	public List<PandorasBlockSettings> drops = new ArrayList<PandorasBlockSettings>();

	
	public PandorasBlockConfigurationHandler () {
		try {
			File f = new File(path);
			if (f.exists()) {
				// Load existing file
				JSONParser parser = new JSONParser();
				
				try {
					
					Object obj = parser.parse(new FileReader(path));
					JSONObject ConfigurationSettings = (JSONObject) obj;
					JSONArray DropList = (JSONArray) ConfigurationSettings.get("drops");
					
					Iterator<Object> iterator = DropList.iterator();
					while ( iterator.hasNext() ) {
						PandorasBlockSettings drop = new PandorasBlockSettings();
						JSONObject d = (JSONObject) iterator.next();

						// Basics
						drop.type    = (String) d.get("type");
						drop.ID      = (Integer) d.get("ID");
						drop.Name    = (String) d.get("Name");
						
						// Positions
						drop.posX = ( d.get("posX") != null ) ? Integer.parseInt( d.get("posX").toString() ) : null;
						drop.posY = ( d.get("posY") != null ) ? Integer.parseInt( d.get("posY").toString() ) : null;
						drop.posZ = ( d.get("posZ") != null ) ? Integer.parseInt( d.get("posZ").toString() ) : null;
						
						drop.offsetX = ( d.get("offsetX") != null ) ? Integer.parseInt( d.get("offsetX").toString() ) : 0;
						drop.offsetY = ( d.get("offsetY") != null ) ? Integer.parseInt( d.get("offsetY").toString() ) : 0;
						drop.offsetZ = ( d.get("offsetZ") != null ) ? Integer.parseInt( d.get("offsetZ").toString() ) : 0;
						
						// Damage
						drop.damage = ( d.get("damage") != null) ? (Integer) d.get("damage") : 0;
						
						drops.add(drop);
						
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				
				
			}
			else {
				createConfigFile();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void createConfigFile() throws IOException {
		// Start creating the configurations file.
		JSONObject obj = new JSONObject();
		JSONArray settings = new JSONArray();
		
		// Create two to test

		
		JSONObject setting = new JSONObject();
			setting.put("type", "item");
			setting.put("Name", "iron_pickaxe");
			setting.put("offsetY", 10);
		settings.add(setting);
		
		JSONObject setting1 = new JSONObject();
			setting.put("type", "item");
			setting.put("Name", "boat");
			setting.put("offsetY", 10);
		settings.add(setting1);
	
		JSONObject setting2 = new JSONObject();
			setting.put("type", "item");
			setting.put("Name", "bone");
			setting.put("offsetY", 10);
		settings.add(setting2);
		
		obj.put("Author", "Christopher McIntosh <cmcintosh@wembassy.com> @cmcintosh");
		obj.put("drops", settings);
		
		// Write to the file
		FileWriter file = new FileWriter(path);
		try {
			file.write(obj.toJSONString());
			System.out.println("Initialized Configuration file.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			file.flush();
			file.close();
		}
	}
}
