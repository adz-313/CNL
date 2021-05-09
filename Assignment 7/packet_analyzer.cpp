#include<iostream>
#include <fstream>
using namespace std;

int main(int argc, char const *argv[])
{
	fstream fout;
	fout.open("packets.csv", ios::in | ios::binary);
	string no,time,source,destination,protocol,length,info,protocol_choice;
	int choice;
	cout << "****Packet Analyzer****" << endl;
	cout << "1. FTP" << endl;
	cout << "2. IP" << endl;
	cout << "3. TCP" << endl;
	cout << "4. UDP" << endl;
	cout << "Enter choice: ";
	cin >> choice;
	switch(choice)
	{
	    case 1:
	        protocol_choice = "FTP";
	        cout << "\n\n FTP packets: \n";
	        break;
        case 2:
	        protocol_choice = "\"IP\"";
	        cout << "\n\n IP packets: \n";
	        break;
        case 3:
	        protocol_choice = "\"TCP\"";
	        cout << "\n\n TCP packets: \n";
	        break;
        case 4:
	        protocol_choice = "\"UDP\"";
	        cout << "\n\n UDP packets: \n";
	        break;

	}
	while(!fout.eof())
    {
        getline(fout,no,',');
        getline(fout,time,',');
        getline(fout,source,',');
        getline(fout,destination,',');
        getline(fout,protocol,',');
        getline(fout,length,',');
        getline(fout,info);
        if(protocol == protocol_choice)
        {
            cout << no << " " << time << " " << source << " " << destination << " " << protocol << " " << length << " " << info << "\n";
        }
    }
	return 0;
}
