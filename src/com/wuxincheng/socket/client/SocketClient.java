package com.wuxincheng.socket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Socket 客户端模拟
 */
public class SocketClient {

	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		
		client.start();
	}

	private void start() {
		BufferedReader inputReader = null;
		
		BufferedWriter writer = null;
		
		BufferedReader reader = null;
		
		String inputContent;
		
		Socket socket = null;
		
		try {
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			
			socket = new Socket("127.0.0.1", 9999);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(!("bye".equals(inputContent = inputReader.readLine()))){
				System.out.println("socket client inputContent: " + inputContent);
				
				writer.write(inputContent  + "\n");
				writer.flush();
				// 
				String response = reader.readLine();
				System.out.println("server response: " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
				inputReader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
