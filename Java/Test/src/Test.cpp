//============================================================================
// Name        : Test.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <cfloat>
#include <cmath>
using namespace std;

int main() {
	float f1 = 15.43000;
	float f2 = 15.43001;
	bool b = true;
	cout << fabs(f1-f2) << endl;
	if( fabs(f1-f2) <= FLT_EPSILON )
	{
		cout << "equal" << endl;
	}
	else
	{
		cout << "not equal" << endl;
	}
	return 0;
}
