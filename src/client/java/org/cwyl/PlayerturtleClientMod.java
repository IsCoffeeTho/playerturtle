package org.cwyl;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerturtleClientMod implements ClientModInitializer {
	
	public static final String MOD_ID = "playerturtle";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitializeClient() {
		
		LOGGER.info("Starting TCP Server");
	}
}