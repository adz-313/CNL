#include <iostream>
#include <string.h>
using namespace std;

int main()
{
    string input, divisor;
    cout << "Enter input: ";
    getline(cin,input);
    cout << "Enter divisor: ";
    getline(cin,divisor);
    string msg = "";
    msg += input;
    int iplen = input.length();
    int divlen = divisor.length();
    //Division
    for(int i = 0; i <= msg.length()-divlen; )
    {
        for(int j=0; j<divlen; j++)
        {
            msg[i+j] = msg[i+j] == divisor[j] ? '0' : '1';
        }
        for(; i<msg.length() && msg[i] != '1'; i++);
    }
    string rem=msg.substr(msg.length()-divisor.length());
    for(int i = 0; i < rem.length(); i++)
    {
        if(rem.at(i) != '0')
        {
            cout << "Error";
            return 0;
        }
    }
    cout << "No error";
    return 0;
}
