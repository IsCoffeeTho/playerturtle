package org.cwyl.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.cwyl.PlayerturtleClientMod;
import org.slf4j.Logger;

public class Client extends Thread {
	public static final Logger LOGGER = PlayerturtleClientMod.LOGGER;
	private Server parent;
	private Socket socket;
	private PrintWriter socketWriter;
	private InputStreamReader socketReader;

	public Client(Server parent, Socket socket) {
		this.parent = parent;
		this.socket = socket;
		try {
			OutputStream output = socket.getOutputStream();
			this.socketWriter = new PrintWriter(output, true);
			
			InputStream input = socket.getInputStream();
			this.socketReader = new InputStreamReader(input);
			
		} catch (Exception err) {
			LOGGER.error(err.getMessage());
		}
	}

	public void run() {
		LOGGER.info("Automation client connected (" + this.toAddressString() + ")");
		try {
			while (this.socket.isConnected()) {
				
				/** @TODO implement a simple protocol to automate the client */
				LOGGER.info("CLIENT SAID '" + this.read() + "'");
				
				
			}
		} catch (Exception err) {
			LOGGER.error("Automation client had a FATAL error: " + err.getMessage());
		}
	}
	
	public void write(String pkt) {
		this.socketWriter.write(pkt);
		this.socketWriter.flush();
	}
	
	public String read() {
		try {
			String retval = "";
			while (true) {
				int character = this.socketReader.read();
				if (character == 10)
					return retval;
				retval += (char)character;
			}
		} catch (Exception err) {
			return ""+((char)-1);
		}
	}

	public void destroy() throws IOException {
		parent.clients.remove(this);
		LOGGER.info("Automation connection closed (" + this.toAddressString() + ")");
		this.socket.close();
	}

	public String toAddressString() {
		return "" + this.socket.getInetAddress().getHostAddress().toString() + ":" + this.socket.getPort();
	}
}
