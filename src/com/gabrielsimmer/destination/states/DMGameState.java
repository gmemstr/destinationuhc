package com.gabrielsimmer.destination.states;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import me.olivervscreeper.networkutilities.game.Game;
import me.olivervscreeper.networkutilities.game.events.PlayerDeathInArenaEvent;
import me.olivervscreeper.networkutilities.game.players.GamePlayer;
import me.olivervscreeper.networkutilities.game.states.GameState;
import me.olivervscreeper.networkutilities.messages.Message;
import me.olivervscreeper.networkutilities.messages.MessageDisplay;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gabrielsimmer.destination.Main;

public class DMGameState extends GameState implements Listener{

	int gameTimer = 20 * 60;
	String wString;

	public DMGameState(Game game, String worldName) {
		super(game, "DMGameState", "DMGameState");
		wString = worldName;
	}

	@Override
	public String getDisplayName(){
		return "DM Game State";
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
		gameInstance.addSpectator(p);
		p.teleport(new Location(Main.plugin.getServer().getWorld(wString), 100, 0, 0));
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
		Player winner = gameInstance.players.get(1).getPlayer();
		Message m = new Message(Message.BLANK);
		for (Player p : players){
			m.addRecipient(p);
		}
		if (players.size() == 1){
			m.send(ChatColor.AQUA + "" + ChatColor.BOLD + winner.getDisplayName() + " wins UHC!", MessageDisplay.CHAT);
			gameInstance.nextState();
		}
	}
}
