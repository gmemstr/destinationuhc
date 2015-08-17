/* This file contains all 'Admin' commands
 * 
 * e.g uhcforce, uhcabort, etc.
 */

package com.gabrielsimmer.destination;

import java.util.List;

import me.olivervscreeper.networkutilities.command.Command;
import me.olivervscreeper.networkutilities.messages.Message;

import org.bukkit.entity.Player;

public class AdminCommands {
	
	@Command(label="ping", permission="destination.admin.ping")
	public void ping(Player p, List<String> args){
		new Message(Message.INFO).addRecipient(p).send("PONG!");
	}
	@Command(label="feed", permission="destination.admin.feed")
	public void feed(Player p, List<String> args){
		p.setFoodLevel(20);
	}
	@Command(label="nextstate", permission="destination.admin.statechange")
	public void nextState(Player p, List<String> args){
		Main.game.nextState();
	}
	@Command(label="numofplayers", permission="destination.admin.listpl")
	public void numberOfPlayers(Player p, List<String> args){
		int pawns = Main.game.players.size();
		new Message(Message.INFO).addRecipient(p).send(Integer.toString(pawns));
	}
	
}
