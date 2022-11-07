bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 5,6,7,8,9,10,11
    len equ ($-s)/4
    d times len dd 0 

; our code starts here
segment code use32 class=code
    start:
        ; ex.24: Being given a string of doublewords, build another string of doublewords which will include only the doublewords from the given string which have an even number of bits with the value 1.
        mov ecx, len
        jecxz final
        
        mov esi, s
        mov edi, d
        
        cld
        again:
            lodsd ; in eax will be the next word from the source string
            mov edx, 0 ; we will count in edx the number of bits 1
            shr eax, 1
            
            count_bits:
            adc dl, 0
            shr eax, 1
            jnz count_bits
            adc dl, 0
            
            test dl, 1
            jnz is_odd
            
            mov ebx, esi
            sub ebx, s
            sub ebx, 4
            mov eax, [s+ebx]
            stosd
            
            is_odd:
        loop again
         
            
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
