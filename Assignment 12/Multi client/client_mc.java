import java.net.*;
import java.util.*;
import java.io.*;

class Client
{
	private int portNo;
	private String username;

	private void sendAck(String option,String name)
	{
		try
		{
			Socket dataSocket = new Socket("localhost", 5000);
			dataSocket.setSoTimeout(15000);
			PrintStream socketOutput = new PrintStream(dataSocket.getOutputStream());
			socketOutput.println(option);
		 	socketOutput.flush();
		 	if(!name.equals("null"))
		 	{
		 		socketOutput.println(name);
		 		socketOutput.flush();
		 	}
			dataSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private int receivePortNo()
	{
		try
		{
			ServerSocket connectionSocket = new ServerSocket(5005);
			connectionSocket.setSoTimeout(15000);
			Socket dataSocket = connectionSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			String msg = br.readLine();
			portNo = Integer.parseInt(msg);
			dataSocket.close();
			connectionSocket.close();
			return portNo;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	private void receiveActiveUsers()
	{
		try
		{
			sendAck("2","null");
			ServerSocket connectionSocket = new ServerSocket(5050);
			connectionSocket.setSoTimeout(15000);
			Socket dataSocket = connectionSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			String temp = "";
			do
			{
				temp = br.readLine();
				System.out.println(temp);
			}while(!temp.equals(""));
			dataSocket.close();
			connectionSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void receiveMessage()
	{
		try
		{
			ServerSocket connectionSocket = new ServerSocket(portNo);
			Socket dataSocket = connectionSocket.accept();
			dataSocket.setSoTimeout(10000);
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			System.out.println(br.readLine());
			dataSocket.close();
			connectionSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String msg, int portNo)
	{
		String message = "[" + username + "]: " + msg;
		try
		{
			Socket dataSocket = new Socket("localhost",portNo);
			PrintStream socketOutput = new PrintStream(dataSocket.getOutputStream());
			socketOutput.println(message);
		 	socketOutput.flush();
			dataSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{

		Client obj = new Client();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username: ");
		obj.username = sc.nextLine();
		obj.sendAck("1",obj.username);
		System.out.println("Your port: " + obj.receivePortNo());
		obj.receiveActiveUsers();
		String message = ""; 
		int input,port;
		while(true)
		{
			System.out.println("1. Send");
			System.out.println("2. Receive");
			System.out.println("3. Show active users");
			System.out.println("4. Exit");
			System.out.println("Enter choice: ");
			input = sc.nextInt();
			String msg;
			if(input == 1)
			{
				sc.nextLine();
				System.out.println("Enter message: ");
				message = sc.nextLine();
				System.out.println("Enter port number: ");
				port = sc.nextInt();
				obj.sendMessage(message,port);
			}
			else if(input == 2) 
			{
				obj.receiveMessage();
			}
			else if(input == 3)
			{
				obj.sendAck("2","null");
				obj.receiveActiveUsers();
			}
			else
			{
				obj.sendAck("3",obj.username);
				return;
			}
		}
	}
}