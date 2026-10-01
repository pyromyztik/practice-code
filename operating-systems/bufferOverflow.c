// bufferOverflow.c - compile with protections OFF to see the raw behavior:
// gcc -fno-stack-protector -z execstack -no-pie -o vuln.out bufferOverflow.c
#include <stdio.h>
#include <string.h>

void greet(char *input) {
    char buffer[16];
    strcpy(buffer, input);   // the flaw
    printf("Hello, %s\n", buffer);
}
// try this 
// ./vuln.out $(python3 -c "print('A'*500)")

int main(int argc, char *argv[]) {
    if (argc < 2) { printf("Usage: %s <name>\n", argv[0]); return 1; }
    greet(argv[1]);
    printf("Function returned normally.\n");
    return 0;
}