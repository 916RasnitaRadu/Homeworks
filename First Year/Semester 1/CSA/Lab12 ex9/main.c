#include <stdio.h>

int afisare(int x);

int main()
{
    // Read from the keyboard a string of numbers, given in the base 16 (a string of characters is read from the keyboard and a string of numbers must be stored in memory). Show the decimal value of the number both as unsigned and signed numbers.
    int x;
    printf("Introduceti numerele: ");
    scanf("%x", &x);
    while(x != 0)
    {
        afisare(x);
        scanf("%x", &x);
    }

}
