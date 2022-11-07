bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 50
    b dw 20
    c dd 40
    d dq 1458744
    r resq 1

; our code starts here
segment code use32 class=code
    start:
        ; a - byte, b - word, c - double word, d - qword
        ; ex9: a-d+b+b+c
        mov al,[a]
        cbw
        cwde
        cdq
        sub eax, dword[d+0]
        sbb edx, dword[d+4]
        mov ecx, eax ; 
        mov ebx, edx
        
        mov ax,[b]
        cwde
        add ecx, eax
        adc ecx, eax
        mov eax,[c]
        add ecx, eax
        adc ebx, edx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
