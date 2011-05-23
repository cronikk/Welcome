package com.elitecraft.cronikkk.welcome;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.Listener;
import org.bukkit.util.config.Configuration;

public class WelcomePlayerListener extends PlayerListener {
	static String mainDirectory = "plugins/Welcome";
	File file = new File(mainDirectory + File.separator + "config.yml");

	public Configuration load() {
		try {
			Configuration config = new Configuration(file);
			config.load();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Configuration config = load();
		player.sendMessage(config.getString("WelcomeMessage"));
	}

}
