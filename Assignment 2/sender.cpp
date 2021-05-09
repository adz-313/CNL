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
    //Adding bits to end of input
    for(int i=iplen; i<(iplen+divlen)-1; i++)
    {
        msg += '0';
    }
    //Division
    for(int i = 0; i <= msg.length()-divlen; )
    {
        for(int j=0; j<divlen; j++)
        {
            msg[i+j] = msg[i+j] == divisor[j] ? '0' : '1';
        }
        for(; i<msg.length() && msg[i] != '1'; i++);
    }
    cout << input + msg.substr(msg.length()-divlen+1);
}
