package org.cwyl;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.font.UnihexFont.Dimensions;

import java.net.InetAddress;
import java.util.function.Consumer;

import org.cwyl.config.PlayerturtleConfig;
import org.cwyl.controller.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerturtleClientMod implements ClientModInitializer {

	public static final String MOD_ID = "playerturtle";
	public static final String VERSION = "1.0.0";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final PlayerturtleConfig CONFIG = PlayerturtleConfig.createAndLoad();
	
	public Server SERVER;

	@Override
	public void onInitializeClient() {
		CONFIG.subscribeToEnabled(enabled -> {
			if (enabled)
				startAutomationServer();
			else
				stopAutomationServer();
		});

		if (!CONFIG.enabled()) {
			LOGGER.info("Automation server will not be started automatically.");
			return;
		}
		
		this.startAutomationServer();
	}
	
	public void startAutomationServer() {
		LOGGER.info("Starting Automation Server");
		try {
			this.SERVER = new Server(InetAddress.getByName(CONFIG.socketAddress()), CONFIG.socketPort());
		} catch (Exception err) {
			LOGGER.error(err.getLocalizedMessage());
		}
	}
	
	public void stopAutomationServer() {
		LOGGER.info("Stopping Automation Server");
		
	}
}