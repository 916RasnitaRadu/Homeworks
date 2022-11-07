bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 3 
    b db 7
    c dw 30
    e dd 120
    x dq 2300
    
; our code starts here
segment code use32 class=code
    start:
        ; (a-b+c*128)/(a+b)+e-x; a,b-byte; c-word; e-doubleword; x-qword
        mov al, [a]
        sub al, [b]
        cbw
        mov bx,ax ; BX = a-b
        mov ax,128
        imul word[c]
        
        push dx
        push ax
        pop eax ; EAX = 128*c
        
        mov ecx, eax
        mov ax,bx
        cwde
        add eax,ecx ; EAX = (a-b+c*128)
        
        mov ebx,eax ; EBX = (a-b+c*128) 
        mov al, [a]
        add al, [b] 
        cbw         ; AX = a+b
        mov cx, ax
        mov eax,ebx
        idiv cx     ; AX = EAX/CX and DX = EAX%CX
                    ; so AX = (a-b+c*128)/(a+b)
        cwde
        add eax,[e]
        cdq
        mov ebx, dword[x+0]
        mov ecx, dword[x+4]
        sub eax, ebx
        sbb edx, ecx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
