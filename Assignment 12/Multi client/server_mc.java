import java.net.*;
import java.util.*;
import java.io.*;

class User 
{
	String username;
	int portNo;
	User(String name, int port)
	{
		username = name;
		portNo = port;
	}
	String displayUser()
	{
		String temp = username + " : " + portNo + "\n";
		return temp;
	}
}

class Server 
{

	private ArrayList<User> portList = new ArrayList<User>();
	private int initPort;
	private boolean exit;
	String msg;

	Server()
	{
		initPort = 6000;
		msg = "";
	}

	private boolean listener()
	{
		System.out.println("Waiting for user...");
		try
		{
			ServerSocket connectionSocket = new ServerSocket(5000);
			Socket dataSocket = connectionSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			int choice = Integer.parseInt(br.readLine());
			if(choice == 1)
			{
				newUser(br.readLine());
			}
			else if(choice == 2)
			{
				sendActiveUsers();
			}
			else
			{
				String temp = br.readLine();
				int index = 0;
				for(User u : portList)
				{
					if(u.username.equals(temp))
					{
						index = portList.indexOf(u);
						break;
					}
				}
				portList.remove(index);
				System.out.println(temp + " offline");
			}
			dataSocket.close();
			connectionSocket.close();
			if(portList.size() == 0)
			{
				return true;
			}
			return false;
		}
		catch(BindException ex)
		{
			ex.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("No new client..." + e);
		}
		return false;
	}

	private void newUser(String name)
	{
		System.out.println(name + " online");
		portList.add(new User(name,initPort));
		sendPortNo(initPort);
		initPort++;
	}

	private void sendPortNo(int portNo)
	{
		try
		{
			Socket dataSocket = new Socket("localhost", 5005);
			PrintStream socketOutput = new PrintStream(dataSocket.getOutputStream());
			socketOutput.println(portNo);
		 	socketOutput.flush();
			dataSocket.close();
			sendActiveUsers();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void sendActiveUsers()
	{
		String userList = "Username : Port\n";
		for(User u : portList)
		{
			userList += u.displayUser();
		}
		try
		{
			Socket dataSocket = new Socket("localhost", 5050);
			PrintStream socketOutput = new PrintStream(dataSocket.getOutputStream());
			socketOutput.println(userList);
		 	socketOutput.flush();
			dataSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*private void receiveMessage()
	{
		try
		{
			ServerSocket connectionSocket = new ServerSocket(5000);
			connectionSocket.setSoTimeout(5000);
			Socket dataSocket = connectionSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			msg = br.readLine();
			dataSocket.close();
			connectionSocket.close();
			for(int i=0; i<portList.size(); i++)
			{
				sendMessage(msg,portList.get(i));
			}
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("No new message...");
		}
	}
	
	private void sendMessage(String msg, User user)
	{
		try
		{
			Socket dataSocket = new Socket("localhost", user.portNo);
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

	public void run()
	{
		while(!Thread.interrpted())
		{

		}
	}*/

	public static void main(String[] args) 
	{
		Server obj = new Server();
		boolean exit = false;
		while(!exit)
		{
			exit = obj.listener();
			/*try
			{
				Thread.sleep(5000);
			}
			catch(InterruptedException e){}*/
		}
	}
}