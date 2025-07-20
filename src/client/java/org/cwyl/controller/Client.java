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
	
	static String EOF = ""+((char)-1);

	public Client(Server parent, Socket socket) {
		this.parent = parent;
		this.socket = socket;
		try {
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();
			this.socketWriter = new PrintWriter(output, true);
			this.socketReader = new InputStreamReader(input);
		} catch (Exception err) {
			LOGGER.error(err.getMessage());
		}
	}

	public void run() {
		LOGGER.info("Automation client connected (" + this.toAddressString() + ")");
		try {
			while (this.socket.isConnected()) {
				String command = this.read();
				if (command == EOF)
					break;
				/** @TODO implement a simple protocol to automate the client */
				LOGGER.info("CLIENT SAID '" + command + "'");
				
			}
			destroy();
		} catch (Exception err) {
			LOGGER.error("Automation client had an error: " + err.getMessage());
		}
	}
	
	public void write(String pkt) {
		this.socketWriter.write(pkt + "\n");
		this.socketWriter.flush();
	}
	
	public String read() {
		try {
			String retval = "";
			while (this.socket.isConnected()) {
				int character = this.socketReader.read();
				if (character == -1)
					return EOF;
				if (character == '\n')
					return retval;
				retval += (char)character;
			}
			return retval;
		} catch (Exception err) {
			return EOF;
		}
	}

	public void destroy() throws IOException {
		parent.clients.remove(this);
		if (!this.socket.isClosed())
			this.socket.close();
		LOGGER.info("Automation connection closed (" + this.toAddressString() + ")");
	}

	public String toAddressString() {
		return "" + this.socket.getInetAddress().getHostAddress().toString() + ":" + this.socket.getPort();
	}
}
