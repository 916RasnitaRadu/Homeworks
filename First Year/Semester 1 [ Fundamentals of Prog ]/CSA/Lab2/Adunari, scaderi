bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; a,b,c,d - byte
    a dw 15
    b dw 5 
    c dw 2
    d dw 3

; our code starts here
segment code use32 class=code
    start:
        ; a,b,c,d - byte
        ; ex 9: (d+d-b)+(c-a)+d (a = 2, b =3, c = 4, d = 5)
        mov al, [d]
        add al, [d]
        sub al, [b] ; AL = d+d-b = 7
        mov bl, [c]
        sub bl, [a] ; BL = c - a = 2
        add al,bl   ; AL = 9
        add al, [d] ; AL = 14
        
        ; ex 24: (a-b-b-c)+(a-c-c-d) ( a = 15, b = 1, c = 2, d = 3)
        mov al, [a]
        sub al, [b]
        sub al, [b]
        sub al, [c] ; AL = a-b-b-c = 11
        mov bl, [a]
        sub bl, [c]
        sub bl, [c]
        sub bl, [d] ; BL = a-c-c-d = 8
        add al,bl   ; AL = 19
        
        ; a,b,c,d - word
        ; ex 9: a-d+b+b+c ( a = 15, b = 1, c = 2, d = 3)
        mov ax, [a]
        sub ax, [d]
        add ax, [b]
        add ax, [b]
        add ax, [c] ; AX = 16
        
        ; ex 24: (a-c)+(b-d) ( a = 15, b = 5, c = 2, d = 3)
        mov ax, [a]
        sub ax, [c] ; AX = 13
        mov bx, [b]
        sub bx, [d] ; BX = 2
        add ax, bx
        
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
