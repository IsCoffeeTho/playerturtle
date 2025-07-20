package org.cwyl.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import org.cwyl.PlayerturtleClientMod;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class Server extends Thread {
	public static final Logger LOGGER = PlayerturtleClientMod.LOGGER;
	public InetAddress host;
	public int port;

	@Nullable
	private ServerSocket socket;

	public ArrayList<Client> clients;

	public Server(InetAddress host, int port) {
		this.host = host;
		this.port = port;
		this.clients = new ArrayList<Client>();
		try {
			this.socket = new ServerSocket(this.port, 50, this.host);
			this.start();
			LOGGER.info("Started Automation server (" + this.toAddressString() + ")");
		} catch (IOException err) {
			LOGGER.error("Automation server failed to start: " + err.getMessage());
		}
	}

	public void run() {
		while (true) {
			try {
				Client client = new Client(this, this.socket.accept());
				clients.add(client);
				client.run();
			} catch (IOException err) {
				LOGGER.error("Automation client failed to connect: " + err.getMessage());
			}
		}
	}
	
	public String toAddressString() {
		return "" + this.socket.getInetAddress().getHostAddress().toString() + ":" + this.socket.getLocalPort();
	}
}
