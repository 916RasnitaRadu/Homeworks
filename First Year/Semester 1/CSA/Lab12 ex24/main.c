#include <stdio.h>
#include <string.h>

char translator(char c);

int main()
{
    /*
    Read a string s1 (which contains only lowercase letters). Using an alphabet (defined in the data segment), determine and display the string s2 obtained by substitution of each letter of the string s1 with the corresponding letter in the given alphabet.
    Example:
    The alphabet:  OPQRSTUVWXYZABCDEFGHIJKLMN
    The string s1: anaaremere
    The string s2: OBOOFSASFS
    */
    char s[100];
    printf("Introduceti string-ul: ");
    scanf("%s", s);
    for (int i = 0; i < strlen(s); i++)
        s[i] = translator(s[i]);
    printf(s);
}
