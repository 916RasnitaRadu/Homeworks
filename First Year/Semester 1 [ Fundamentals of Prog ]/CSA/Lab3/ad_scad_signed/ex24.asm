bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 8
    b dw 234
    c dd 643
    d dq 13464324

; our code starts here
segment code use32 class=code
    start:
        ; a - byte, b - word, c - double word, d - qword
        ; ex24: (a + b + c) - d + (b - c)
        mov al, [a]
        cbw
        add ax,[b]
        cwde 
        add eax, [c] ; EAX = a + b + c
        cdq
        
        sub eax, dword[d+0]
        sbb edx, dword[d+4] ; EDX:EAX = (a + b + c) - d
        
        mov ebx, eax 
        mov ecx, edx
        
        mov ax, [b]
        cwde
        sub eax,[c]
        mov edx,0
        
        add ebx, eax
        adc ecx, edx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
