/* DestinationUHC (c) Gabriel Simmer 2015
 * 
 * DestinationUHC is a UHC plugin built for Utopian Realms Network
 */

package com.gabrielsimmer.destination;

import me.olivervscreeper.networkutilities.NULogger;
import me.olivervscreeper.networkutilities.NetworkUtilities;
import me.olivervscreeper.networkutilities.command.CommandManager;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	public static Main plugin;
	public static UHCGame game = null;

	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		CommandManager manager = NetworkUtilities.getCommandManager();
		manager.registerCommands(new AdminCommands());
		manager.registerCommands(new PlayerCommands());
		game = new UHCGame(new NULogger(true));
		plugin = this;
		getLogger().info("DestinationUHC Loaded :)");
	}
	@Override
	public void onDisable(){
		getLogger().info("DestinationUHC Disabled :(");
	}
	
}
