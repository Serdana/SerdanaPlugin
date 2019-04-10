package main.java.com.mrunknown404.serdana.util.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.java.com.mrunknown404.serdana.Main;
import main.java.com.mrunknown404.serdana.util.ColorHelper;
import main.java.com.mrunknown404.serdana.util.PrayInfo;

public class PrayerHandler {
	
	private final File path;
	private final File file_unsortedPrayers = new File("UnsortedPrayers");
	private final File file_goodPrayers = new File("GoodPrayers");
	private final File file_badPrayers = new File("BadPrayers");
	
	private List<PrayInfo> unsortedPrayers = new ArrayList<PrayInfo>();
	private List<PrayInfo> goodPrayers = new ArrayList<PrayInfo>();
	private List<PrayInfo> badPrayers = new ArrayList<PrayInfo>();
	
	public static List<Inventory> unsortedPrayersInventory = new ArrayList<Inventory>();
	public static List<Inventory> goodPrayersInventory = new ArrayList<Inventory>();
	public static List<Inventory> badPrayersInventory = new ArrayList<Inventory>();
	
	public PrayerHandler(Main main) {
		this.path = main.getDataFolder();
	}
	
	private void createInventories() {
		unsortedPrayersInventory.clear();
		goodPrayersInventory.clear();
		badPrayersInventory.clear();
		
		for (int i = 0; i < unsortedPrayers.size(); i++) {
			if (i % 54 == 0 || i == 0) {
				unsortedPrayersInventory.add(Bukkit.createInventory(null, 54, ColorHelper.setColors("&cUnsorted Prayers [" + (int) Math.ceil(i / 54) + "]")));
			}
		}
		
		for (int i = 0; i < goodPrayers.size(); i++) {
			if (i % 54 == 0 || i == 0) {
				goodPrayersInventory.add(Bukkit.createInventory(null, 54, ColorHelper.setColors("&cGood Prayers [" + (int) Math.ceil(i / 54) + "]")));
			}
		}
		
		for (int i = 0; i < badPrayers.size(); i++) {
			if (i % 54 == 0 || i == 0) {
				badPrayersInventory.add(Bukkit.createInventory(null, 54, ColorHelper.setColors("&cBad Prayers [" + (int) Math.ceil(i / 54) + "]")));
			}
		}
	}
	
	private void addPrayerstoInventories() {
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
		
		for (int i = 0; i < unsortedPrayers.size(); i++) {
			PrayInfo pi = unsortedPrayers.get(i);
			item.setItemMeta(pi.getBook());
			
			unsortedPrayersInventory.get((int) Math.ceil(i / 54)).setItem(i % 54, item);
		}
		
		for (int i = 0; i < goodPrayers.size(); i++) {
			PrayInfo pi = goodPrayers.get(i);
			item.setItemMeta(pi.getBook());
			
			goodPrayersInventory.get((int) Math.ceil(i / 54)).setItem(i % 54, item);
		}
		
		for (int i = 0; i < badPrayers.size(); i++) {
			PrayInfo pi = badPrayers.get(i);
			item.setItemMeta(pi.getBook());
			
			badPrayersInventory.get((int) Math.ceil(i / 54)).setItem(i % 54, item);
		}
	}
	
	public void reload() {
		writeAllPrayers();
		readAllPrayers();
		createInventories();
		addPrayerstoInventories();
	}
	
	public void addPrayer(PrayInfo b, String type) {
		Bukkit.getPlayer(b.getOwnerUUID()).sendMessage(ColorHelper.setColors("&cPrayer sent!"));
		
		if (type.equalsIgnoreCase("unsorted")) {
			unsortedPrayers.add(b);
			writeUnsortedprayers();
		} else if (type.equalsIgnoreCase("good")) {
			goodPrayers.add(b);
			writeGoodPrayers();
		} else if (type.equalsIgnoreCase("bad")) {
			badPrayers.add(b);
			writeBadPrayers();
		}
		
		reload();
	}
	
	private void writeAllPrayers() {
		if (!new File(path + "/" + file_unsortedPrayers + ".yml").exists()) {
			writeUnsortedprayers();
		}
		
		if (!new File(path + "/" + file_goodPrayers + ".yml").exists()) {
			writeGoodPrayers();
		}
		
		if (!new File(path + "/" + file_badPrayers + ".yml").exists()) {
			writeBadPrayers();
		}
	}
	
	private void writeUnsortedprayers() {
		File f = new File(path + "/" + file_unsortedPrayers + ".yml");
		
		YamlConfiguration write = YamlConfiguration.loadConfiguration(f);
		write.set("Prayers", unsortedPrayers);
		
		try {
			write.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeGoodPrayers() {
		File f = new File(path + "/" + file_goodPrayers + ".yml");
		
		YamlConfiguration write = YamlConfiguration.loadConfiguration(f);
		write.set("Prayers", goodPrayers);
		
		try {
			write.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeBadPrayers() {
		File f = new File(path + "/" + file_badPrayers + ".yml");
		
		YamlConfiguration write = YamlConfiguration.loadConfiguration(f);
		write.set("Prayers", badPrayers);
		
		try {
			write.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readAllPrayers() {
		File f = new File(path + "/" + file_unsortedPrayers + ".yml");
		
		unsortedPrayers.clear();
		List<?> list = YamlConfiguration.loadConfiguration(f).getList("Prays");
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				unsortedPrayers.add((PrayInfo) list.get(i));
			}
		}
		
		f = new File(path + "/" + file_goodPrayers + ".yml");
		
		goodPrayers.clear();
		list = YamlConfiguration.loadConfiguration(f).getList("Prays");
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				goodPrayers.add((PrayInfo) list.get(i));
			}
		}
		
		f = new File(path + "/" + file_badPrayers + ".yml");
		
		badPrayers.clear();
		list = YamlConfiguration.loadConfiguration(f).getList("Prays");
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				badPrayers.add((PrayInfo) list.get(i));
			}
		}
	}
	
	public List<Inventory> getUnsortedInventories() {
		return unsortedPrayersInventory;
	}
	
	public List<Inventory> getGoodInventories() {
		return goodPrayersInventory;
	}
	
	public List<Inventory> getBadInventories() {
		return badPrayersInventory;
	}
}
