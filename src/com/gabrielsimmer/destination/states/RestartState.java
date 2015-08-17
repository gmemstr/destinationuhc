package com.gabrielsimmer.destination.states;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.olivervscreeper.networkutilities.game.Game;
import me.olivervscreeper.networkutilities.game.states.GameState;
import me.olivervscreeper.networkutilities.messages.Message;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.gabrielsimmer.destination.Main;

public class RestartState extends GameState implements Listener {

	private int restartTimer = 11;

	public RestartState(Game game) {
		super(game, "Restarting", "Restarting");
	}

	@Override
	public String getDisplayName() {
		return "Restarting";
	}

	@Override
	public String getName() {
		return "Restarting";
	}

	@Override
	public boolean onStateBegin() {
		return true;
	}

	@Override
	public boolean onStateEnd() {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		restartTimer--;
		Message m = new Message(Message.BLANK);
		if (restartTimer == 10) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				m.addRecipient(p);
			}
			m.send("Server Restarting in 10 seconds!");
		} else if (restartTimer == 5) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				m.addRecipient(p);
			}
			m.send("Server Restarting in 5 seconds!");
		} else if (restartTimer == 3) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				try {
					out.writeUTF("Connect");
					out.writeUTF("lobby");
				} catch (IOException eee) {
					p.sendMessage("Server Offline.");
					Bukkit.getLogger().info("You'll never see me!");
				}
				p.sendPluginMessage(Main.plugin, "BungeeCord", b.toByteArray());
			}
		} else if (restartTimer == 0){
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer("Server restarting!");
			}
			Bukkit.reload();
		}
	}
}
