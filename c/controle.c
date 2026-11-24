#include <limits.h>
#include <stdio.h>

// en C le depassement d'un int signe est un comportement indefini :
// le compilateur a le droit de supposer que x + 100 ne deborde jamais
int deborde(int x) {
    return x + 100 < x;
}

int main(void) {
    printf("deborde(INT_MAX - 5) = %d\n", deborde(INT_MAX - 5));
    printf("1500 * 2000000       = %d\n", 1500 * 2000000);
    return 0;
}
