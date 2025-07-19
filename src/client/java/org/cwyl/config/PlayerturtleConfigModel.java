package org.cwyl.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Hook;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Sync;

@Modmenu(modId = "playerturtle")
@Sync(value = Option.SyncMode.NONE)
@Config(name = "playerturtle-config", wrapperName = "PlayerturtleConfig")
public class PlayerturtleConfigModel {

	@Hook
	public boolean enabled = true;

	public String socketAddress = "localhost";
	public int socketPort = 56060;
}