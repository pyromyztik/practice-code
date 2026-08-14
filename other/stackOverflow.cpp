#include <iostream>

using namespace std;

void stackOverflow() {
    stackOverflow();
    cout << "Impossible to reach.";
}

int main() {
    stackOverflow();
    return 0;
}