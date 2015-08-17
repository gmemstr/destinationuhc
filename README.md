# DestinationUHC GitHub Release

## What is DestinationUHC?

DestinationUHC is a plugin developed to be the core of any UHC game type.

## Features
- No regeneration
- Lobby with countdown
   - No block breaking in lobby
   - No damage in lobby
- Deathmatch

## Limitations
Because this is a 'free' release on GitHub I assume you know how to read, edit and compile Spigot plugins and Java code. Due to this, some features, such as the seed list file and config file, have been removed. To purchase the full plugin with configuration files and seed list, contact @gmemstr (bladesimmer@gmail.com).

## Dependencies

This plugin requires [NetworkUtilities](https://github.com/oliverdunk/NetworkUtilities/) to both compile and run.

## Known Issues
- For some reason this does not trigger when only one player is in the game (is inside of tick method).

```
//Code edited for visibility
//Found in MainGameState.java
//Issue also present in DMGameState.java

HashMap<String, GamePlayer> pawns = gameInstance.players;

if (pawns.size() == 1){
	gameInstance.nextState();
}
```

- Sometimes timer does not show up or 'Generating World' title not shows (PreGameState.java in tick()).

Find a new issue? Submit it!
