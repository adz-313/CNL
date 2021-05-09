import java.util.Scanner;
import java.lang.Math;

class MyClass
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter input ip address: ");
		String input = sc.nextLine();
		String inputArr[] = input.split("\\.");
		int[] ip = new int[4];
		for(int i=0; i<4; i++)
		{
			ip[i] = Integer.parseInt(inputArr[i]);
		}
		System.out.print("Enter subnet bits: ");
		int subnet = sc.nextInt();
		double arr[] = new double[8];
		for(int i=0; i<8; i++)
		{
			arr[7-i] = Math.pow(2,i);
		}
		int posn = 0; 
		while(subnet > 8)
		{
			posn++;
			subnet -= 8;
		}
		int sum = 0;
		for(int i=0; i<subnet; i++)
		{
			sum += arr[i];
		}
		String subnetMask = "";
		for(int i=0; i<posn; i++)
		{
			subnetMask += "255.";
		}
		subnetMask += String.valueOf(sum);
		for(int i=posn+1; i<4; i++)
		{
			subnetMask += ".0";
		}
		System.out.println("Subnet Mask: " + subnetMask);
		sum=-1;
		while(sum < ip[posn])
		{
			sum += (arr[subnet-1]);
		}
		sum -= (arr[subnet-1]-1);
		inputArr[posn] = String.valueOf(sum);
		System.out.print("1st Address: ");
		for(int i=0; i<4; i++)
		{
			System.out.print(inputArr[i] + ".");	
		}
		sum += arr[subnet-1]-1;
		inputArr[posn] = String.valueOf(sum);
		System.out.println("");
		System.out.print("Last Address: ");
		for(int i=0; i<4; i++)
		{
			System.out.print(inputArr[i]);
			if(i != 3)
			{
				System.out.print(".");
			}	
		}
	}
}