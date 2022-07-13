bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 2, 1, -3, 0
    b db 4, 5, 7, 6, 2, 1
    l1 equ b-a
    l2 equ $-b
    r times (l1+l2) db 0

; our code starts here
segment code use32 class=code
    start:
        ; ex24: Two byte strings A and B are given. Obtain the string R by concatenating the elements of B in reverse order and the elements of A in reverse order
        mov ecx, l1 ; move in ecx the len of the first string
        add ecx, l2 ; add to ecx the len of the second string
        mov esi, l2 
        mov ebx, 0
        jecxz Sfarsit
        Repeta1:
            mov al, [b+esi-1] ; we move in AL the last byte from the second string
            mov [r+ebx], al ; we move in the destination string the last byte from the second string
            inc ebx ; in ebx we count the position of the current byte in destination string
            dec esi
            cmp ebx, l2
            je et2 ; if ebx is equal to the length of the second we jump to the next stage
        loop Repeta1
        
        
        et2:
        sub ecx, 1 ; we need to subtract 1 from ecx because the program jumped and the loop didn't end
        mov esi, l1
        mov ebx, 0
        jecxz Sfarsit
        Repeta2:
            mov al, [a+esi-1] ;we move in the destination string the last bye from the first string
            mov [r+ebx+l2], al
            inc ebx
            sub esi, 1
        loop Repeta2
        
        Sfarsit:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
