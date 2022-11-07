bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 1010_0001_1011_1100b
    b db 1100_1010b
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ex. 9 : Se de cuvantul A si octetul B. Sa se obtina dublucuvantul C astfel:
        ;    bitii 0-3 ai lui C coincid cu bitii 6-9 ai lui A
        ;    bitii 4-5 ai lui C au valoarea 1
        ;    bitii 6-7 ai lui C coincid cu bitii 1-2 ai lui B
        ;    bitii 8-23 ai lui C coincid cu bitii lui A
        ;    bitii 24-31 ai lui C coincid cu bitii lui B
        
        mov ebx, 0
                    ;      5432 1098 7654 3210
        mov ax, [a] ; AX = 1010_0001_1011_1100b
        and ax, 0000_0011_1100_0000b
        mov cl, 6
        ror ax, cl
        
        mov dx, 0
        push dx
        push ax
        pop eax
        
        or ebx, eax
        or ebx, 0000_0000_0000_0000_0000_0000_0011_0000b
        
        mov al, [b]
        and al, 0000_0110b
        mov cl, 5
        rol al, cl
        
        mov ah, 0
        mov dx, 0
        push dx
        push ax
        pop eax
        
        or ebx, eax
        
        mov ax, [a]
        mov dx, 0
        push dx
        push ax
        pop eax
        
        mov cl,8
        rol eax, cl
        
        or ebx, eax
        
        mov al, [b]
        mov ah, 0
        mov dx, 0
        
        push dx
        push ax
        pop eax
        
        mov cl, 24
        rol eax, cl
        
        or ebx, eax
        
        mov dword[c], ebx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
