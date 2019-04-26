package main.java.com.mrunknown404.serdana.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import main.java.com.mrunknown404.serdana.Main;
import main.java.com.mrunknown404.serdana.util.ColorHelper;

public class BlockListener implements Listener {
	
	private final Main main;
	
	public BlockListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		if (main.getComponent(Main.Components.StopNamedItemUse)) {
			if (e.getItemInHand().hasItemMeta() && e.getItemInHand().getItemMeta().hasDisplayName()) {
				if (e.getBlock().getType() != Material.CHEST && e.getBlock().getType() != Material.TRAPPED_CHEST) {
					if (!e.getBlock().getType().toString().contains("SHULKER_BOX")) {
						e.getPlayer().sendMessage(ColorHelper.setColors("&cYou cannot place named blocks!"));
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
