package com.gabrielsimmer.destination.states;

import java.util.Collection;
import java.util.HashMap;

import me.olivervscreeper.networkutilities.game.Game;
import me.olivervscreeper.networkutilities.game.events.PlayerDeathInArenaEvent;
import me.olivervscreeper.networkutilities.game.players.GamePlayer;
import me.olivervscreeper.networkutilities.game.states.GameState;
import me.olivervscreeper.networkutilities.messages.Message;
import me.olivervscreeper.networkutilities.messages.MessageDisplay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gabrielsimmer.destination.Main;

public class MainGameState extends GameState implements Listener{

	int gameTimer = 30 * 60;
	String wName;
	World world;

	public MainGameState(Game game, String worldName) {
		super(game, "MainGameState", "MainGameState");
		wName = worldName;
	}

	@Override
	public String getDisplayName(){
		return "Main Game State";
	}

	@Override
	public boolean onStateBegin() {
		registerListener(this);
		//Make the world border
		world = Main.plugin.getServer().getWorld("UHC-" + wName);
		WorldBorder border = world.getWorldBorder();
		border.setCenter(0,0); //Center on 0,0
		border.setSize(500); //Make it 500x500
		border.setSize(25, gameTimer); //Shrink it to 25 blocks in gameTime (30 minutes)
		Bukkit.broadcastMessage("WorldBorder Set!"); //Debug message, remove
		return true;
	}

	@Override
	public boolean onStateEnd() {
		unregisterListener(this);
		return true;
	}

	//EventHandlers for in-game
	@EventHandler
	public void onRegen(EntityRegainHealthEvent e){
		if(e.getRegainReason() == RegainReason.SATIATED || e.getRegainReason() == RegainReason.REGEN)
			e.setCancelled(true);
	}
	@EventHandler
	public void onDeath(PlayerDeathInArenaEvent e){
		Player p = e.getPlayer();
		gameInstance.removePlayer(p);

	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		gameInstance.addSpectator(p);
		p.teleport(new Location(world, 0, 100, 0));
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		gameInstance.addSpectator(p);
	}
	//---

	@Override
	public void tick() {
		gameTimer--;
		Collection<? extends Player> players = Main.plugin.getServer().getOnlinePlayers();
		HashMap<String, GamePlayer> pawns = gameInstance.players;
		Message m = new Message(Message.BLANK);
		for (Player p : players){
			m.addRecipient(p);
		}
		
		if(gameTimer > -1) m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "Deathmatch: " + getTime(), MessageDisplay.ACTIONBAR);
		
		if (pawns.size() == 1){
			gameInstance.nextState();
		}
		if (gameTimer == 0){
			if (pawns.size() >= 10){
				m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "Too many players for Deathmatch!", MessageDisplay.ACTIONBAR);
				gameTimer = 20 * 60;
			}
			else{
				m.send(ChatColor.AQUA + "" + ChatColor.BOLD + "Deathmatch is here!", MessageDisplay.TITLE);
				
				gameInstance.nextState();
			}
		}
	}
	

	private String timeConversion() {
		int hours = gameTimer / 60 / 60;
		int minutes = (gameTimer - (hours * 60 * 60)) / 60;
		int seconds = gameTimer - (minutes * 60);

		if (seconds < 10) {
			return minutes + ":0" + seconds;
		}

		return minutes + ":" + seconds;
	}

	public String getTime() {
		return timeConversion();
	}

}
