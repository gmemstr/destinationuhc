package com.gabrielsimmer.destination.states;

import java.util.Collection;
import java.util.Random;

import me.olivervscreeper.networkutilities.game.Game;
import me.olivervscreeper.networkutilities.game.states.GameState;
import me.olivervscreeper.networkutilities.messages.Message;
import me.olivervscreeper.networkutilities.messages.MessageDisplay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.gabrielsimmer.destination.Main;

public class PreGameState extends GameState implements Listener {
	
	private int lobbyTimer = 40;
	String wString;
	Random rand = new Random();
	
	public PreGameState(Game game, String worldName){
		super(game, "PreGameState", "PreGameState");
		wString = worldName;
	}
	
	@Override
	public String getDisplayName(){
		return "Lobby";
	}

	@Override
	public boolean onStateBegin() {
		registerListener(this);
		return true;
	}

	@Override
	public boolean onStateEnd() {
		unregisterListener(this);
		return true;
	}
	//Event handlers for canceling events in lobby
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		gameInstance.addPlayer(player);
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(new Location(Main.plugin.getServer().getWorld("Lobby"), 944, 6, -1065));
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
	}
	

	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onHit(EntityDamageEvent e){
		e.setCancelled(true);
	}
	//---

	@Override
	public void tick() {
		lobbyTimer--;
		Collection<? extends Player> players = Main.plugin.getServer().getOnlinePlayers();
		Message m = new Message(Message.BLANK);
		for (Player p : players){
			m.addRecipient(p);
		}
		
		if(lobbyTimer == 25) m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "Game starts in 25 seconds!", MessageDisplay.CHAT);
		if (lobbyTimer < 10 && lobbyTimer > -1) m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "00:0" + lobbyTimer, MessageDisplay.TITLE);
		
		if(lobbyTimer == 0){
			if (players.size() < 4){
				m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "Not enough players! Restarting timer.", MessageDisplay.TITLE);
				lobbyTimer = 60;
			}
			else if (players.size() >= 4){
				m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "Generating World...", MessageDisplay.TITLE);

				World world = Main.plugin.getServer().createWorld(new WorldCreator("UHC-" + wString));
				
				int x;
				int z;
				int y;

				for (Player all : Bukkit.getOnlinePlayers()){
					x = rand.nextInt(200);
					if (x % 2 == 0) x *= -1;
					z = rand.nextInt(200);
					if (z % 2 == 0) z *= -1;
					y = world.getHighestBlockYAt(x, z);
					all.teleport(new Location(world, x, y, z));
				}
				gameInstance.nextState();
			}
		}
	}

}
