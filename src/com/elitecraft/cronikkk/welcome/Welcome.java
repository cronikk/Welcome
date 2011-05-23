package com.elitecraft.cronikkk.welcome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Welcome extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	
	static String mainDirectory = "plugins/Welcome";
	File file = new File(mainDirectory + File.separator + "config.yml");
	
	private final WelcomePlayerListener playerListener = new WelcomePlayerListener();

	public Configuration load(){
		try {
			Configuration config = new Configuration(file);
			config.load();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		PluginManager pm = this.getServer().getPluginManager();
		log.info("[Welcome] Plugin Version " + pdfFile.getVersion() + " Has Been Enabled!");
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
		new File(mainDirectory).mkdir();
		if(!file.exists()) {
			try{
				file.createNewFile();
				Configuration config = load();
				config.setProperty("WelcomeMessage","Welcome to the Server!!");
				config.save();
				log.info("[Welcome] Configuration File Created!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void onDisable() {
		log.info("[Welcome] Plugin Has Been Disabled!");
	}
	
	// Command(s)
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		String commandName = command.getName().toLowerCase();
		if (sender instanceof Player){
			Player playerInfo = (Player)sender;
			if(!sender.isOp()){
				sender.sendMessage(ChatColor.GOLD + "Insufficent Privliges!");
			} else {
				if (commandName.equalsIgnoreCase("welcome")) {
					if(args.length == 0)
					{
						sender.sendMessage(ChatColor.GOLD + "To update the welcome message, type /welcome newmessage");
					} else if(args.length > 0)
					{
						String fullMessage = "";
						int maxLength = args.length;
						for(int i = 0; i < args.length; i++) {
							fullMessage += " " + args[i];
						}
						Configuration config = load();
						config.setProperty("WelcomeMessage",fullMessage);
						config.save();
						log.info("[Welcome] " + playerInfo.getDisplayName() + " has changed the welcome message!");
					}
				}
			}
		}
		return true;
	}
}
