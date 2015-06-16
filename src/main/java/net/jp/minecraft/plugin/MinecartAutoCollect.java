package net.jp.minecraft.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author syokkendesuyo
 */


public class MinecartAutoCollect extends JavaPlugin implements Listener {


	/**
	 * プラグインが有効になったときに呼び出されるメソッド
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onCreatePortal (EntityCreatePortalEvent event){
		event.setCancelled(true);
	}

	@EventHandler
	public void onMinecartRemoveEvent (VehicleDestroyEvent event){
		Player player = (Player) event.getAttacker();
		Vehicle vehicle = event.getVehicle();
		if(event.getVehicle() instanceof Minecart){
			player.sendMessage(ChatColor.AQUA + "□ マインカートを回収しました。");
			player.getInventory().addItem(new ItemStack(Material.MINECART));
			vehicle.remove();
		}
	}

	@EventHandler
	 public void onVehicleExit(VehicleExitEvent event) {
		Vehicle vehicle = event.getVehicle();
		Player player = (Player) event.getExited();
		if(event.getExited() instanceof Player && event.getVehicle() instanceof RideableMinecart){
			player.sendMessage(ChatColor.AQUA + "□ マインカートを回収しました。");
			player.getInventory().addItem(new ItemStack(Material.MINECART));
			vehicle.remove();
		}
	}
	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		if (event.getVehicle() instanceof RideableMinecart) {
			event.setCancelled(true);
		}
	}
/*
	@EventHandler
	public void onEntityCreatePortal(EntityCreatePortalEvent event){
		Player player = (Player) event.getEntity();
		player.sendMessage("□ 当サーバではポータルを作成できません。");
		event.setCancelled(true);
	}
*/
	@EventHandler
	public void onCreatePortal(PlayerPortalEvent event){
		Player player = event.getPlayer();
		if(event.getCause() == PlayerPortalEvent.TeleportCause.NETHER_PORTAL){
			player.sendMessage(ChatColor.RED + "＝＝＝＝＝＝ お知らせ ＝＝＝＝＝＝");
			player.sendMessage(ChatColor.WHITE + "□ 当サーバではネザーポータルは利用できません。");
			player.sendMessage(ChatColor.WHITE + "□ ネザーへの移動は 「/warp nether」 で可能です。");
			player.sendMessage(ChatColor.RED + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
			event.setCancelled(true);
		}
	}
}
