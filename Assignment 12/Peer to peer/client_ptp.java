import java.net.*;
import java.util.*;
import java.io.*;

class MyClass 
{

	public void receiveMessage()
	{
		try
		{
			ServerSocket connectionSocket = new ServerSocket(5000);
			connectionSocket.setSoTimeout(10000);
			Socket dataSocket = connectionSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			System.out.println("Server: " + br.readLine());
			dataSocket.close();
			connectionSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg)
	{
		try
		{
			Socket dataSocket = new Socket("localhost", 5000);
			PrintStream socketOutput = new PrintStream(dataSocket.getOutputStream());
			socketOutput.println(msg);
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
		MyClass obj = new MyClass();
		Scanner sc = new Scanner(System.in);
		int input;
		while(true)
		{
			System.out.println("1. Send");
			System.out.println("2. Receive");
			System.out.println("3. Exit");
			System.out.println("Enter choice: ");
			input = sc.nextInt();
			String msg;
			if(input == 1)
			{
				sc.nextLine();
				msg = sc.nextLine();
				obj.sendMessage(msg);
			}
			else if(input == 2) 
			{
				obj.receiveMessage();
			}
			else
			{
				return;
			}
		}
	}
}