bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db 1, 2, 4, 6, 10, 20, 25
    l equ $-s
    d times l-1 db 0

; our code starts here
segment code use32 class=code
    start:
        ; ex. 9: A byte string S of length l is given. Obtain the string D of length l-1 so that the elements of D represent the difference between every two consecutive elements of S.
        mov ecx, l-1    ; move in ecx the len of the string
        mov esi, 0      
        jecxz Sfarsit
        Repeta:
            mov al, [s+esi+1] ; move in al the next byte
            mov bl, [s+esi] ; move in bl the current byte
            sub al,bl ; compute the difference between the second and the first byte
            mov [d+esi], al ; we move in destination string the result
            inc esi
        loop Repeta
        Sfarsit:
            
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
