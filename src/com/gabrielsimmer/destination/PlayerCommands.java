/* This class has player commands
 * 
 * e.g uhckit, uhcquit, etc.
 */

package com.gabrielsimmer.destination;

import java.util.List;

import me.olivervscreeper.networkutilities.command.Command;
import me.olivervscreeper.networkutilities.messages.Message;

import org.bukkit.entity.Player;

public class PlayerCommands {

	@Command(label="piing", permission="destination.player.piing")
	public void ping(Player p, List<String> args){
		new Message(Message.INFO).addRecipient(p).send("PONG!");
	}
	
}
