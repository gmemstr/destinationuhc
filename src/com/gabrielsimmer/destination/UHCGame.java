package com.gabrielsimmer.destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.olivervscreeper.networkutilities.NULogger;
import me.olivervscreeper.networkutilities.game.Game;
import me.olivervscreeper.networkutilities.game.states.GameState;
import me.olivervscreeper.networkutilities.serialization.LocationSerialization;

import org.bukkit.Location;
import org.bukkit.event.Listener;

import com.gabrielsimmer.destination.states.DMGameState;
import com.gabrielsimmer.destination.states.MainGameState;
import com.gabrielsimmer.destination.states.PreGameState;
import com.gabrielsimmer.destination.states.RestartState;

public class UHCGame extends Game implements Listener{

	Random rand = new Random();
	int wName = rand.nextInt();
	String wString = String.valueOf(wName);
	
	//List<String> seeds = Main.seedList.getStringList("seeds");
	
	public UHCGame(NULogger logger){
		super(logger, "DestinationUHC", "DestinationUHC");
	}

	List<GameState> states = new ArrayList<GameState>();
	
	@Override
	public List<GameState> getAllStates() {
		states.add(new PreGameState(this, wString));
		states.add(new MainGameState(this, wString));
		states.add(new DMGameState(this, wString));
		states.add(new RestartState(this));
		return states;
	}

	@Override
	public Location getLobbyLocation() {
		return LocationSerialization.getLocationMeta("{\"world\":\"Lobby\",\"x\":944,\"y\":6,\"z\":-1065,\"pitch\":-180,\"yaw\":-14.4}");
		//944 6 -1065
	}
	
}
