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
		start(); // 执行
	}

	public static void start() {
		Socket socket = null;
		String inputContent = null;
		
		// 1. 获取用户输入的信息
		try {
			inputContent = SocketClient.processSystemInMessage();
		} catch (IOException eUserPrint) {
			System.out.println("[socket client] 用户输入信息出现异常:");
			eUserPrint.printStackTrace();
			return;
		}
		
		// 2. 连接到Socket服务器
		try {
			System.out.println("[socket client] 正在连接Socket服务器...");
			socket = new Socket("127.0.0.1", 9999); // Socket连接主机端口
			System.out.println("[socket client] 连接已创建");
		} catch (IOException e) {
			System.out.println("[socket client] Socket服务器连接失败:");
			e.printStackTrace();
			return;
		}

		// 3. 发送请求数据
		try {
			SocketClient.sendMessage(socket, inputContent);
		} catch (IOException e) {
			System.out.println("[socket client] 请求发送出现异常:");
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		// 4. 接收返回数据
		String response = null;
		try {
			response = SocketClient.recevieMessage(socket);
			System.out.println("已接收服务器返回数据: " + response);
		} catch (IOException e) {
			System.out.println("[socket client] 接收服务器返回数据出现异常:");
			e.printStackTrace();
			return;
		} finally {
			try {
				// 关闭对象
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 4. 处理返回数据
		System.out.println("处理response数据: " + response);
	}
	
	/**
	 * 获取用户在控制台输入的信息
	 * 
	 * @return
	 * @throws IOException
	 */
	private static String processSystemInMessage() throws IOException{
		String inputContent;
		
		// 读取从控制台输入的数据
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		
		// 读取从控制台输入的数据
		inputContent = inputReader.readLine();
		
		// 打印出输入的文字
		System.out.println("[socket client] 用户输入的内容: " + inputContent);
		
		inputReader.close(); // 关闭
		
		return inputContent;
	}
	
	/**
	 * 向Socket服务器发送请求数据
	 * 
	 * @param socket
	 * @return
	 * @throws IOException 
	 */
	private static String sendMessage(Socket socket, String sendData) throws IOException{
		BufferedWriter writer = null;
		
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.write(sendData  + "\n");
		writer.flush();
		
		System.out.println("[socket client] 请求已发送");
		
		writer.close();
		
		return null;
	}
	
	/**
	 * 接收从Socket服务器返回的数据
	 * 
	 * @param socket
	 * @return
	 * @throws IOException 
	 */
	private static String recevieMessage(Socket socket) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String response = reader.readLine();
		
		System.out.println("[socket client] 接收到服务器返回的信息: " + response);
		
		reader.close();
		
		return response;
	}
	
	// ================== 无聊的分隔线 ==================
	@Deprecated
	public void start2() {
		BufferedReader inputReader = null;
		
		BufferedWriter writer = null;
		
		BufferedReader reader = null;
		
		String inputContent;
		
		Socket socket = null;
		
		try {
			// 读取从控制台输入的数据
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			
			socket = new Socket("127.0.0.1", 9999); // Socket连接主机端口
			
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(!("bye".equals(inputContent = inputReader.readLine()))){
				// 打印出输入的文字
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
				// 关闭对象
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
