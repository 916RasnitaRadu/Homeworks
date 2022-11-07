bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db
    b dw
    c dd
    d dq 12345678h

; our code starts here
segment code use32 class=code
    start:
        ; ((a + b) + (a + c) + (b + c)) - d
        mov ah,0
        mov al,[a]
        add ax, [b]; AX = a + b
        mov bx,ax ; BX = a + b
        
        mov al,[a]
        mov ah,0
        mov dx,0
        
        push dx
        push ax
        pop eax
        
        add eax, [c] ; EAX = a + c
        mov ebx,eax ; EBX = a+c
       
        mov ecx,[c]
        mov ax,[b]
        mov dx,0
        
        push dx
        push ax
        pop eax
        
        add ecx,eax
        add ebx,ecx ; EBX = (a + c) + (b + c)
        
        mov ax,bx
        mov dx
        
        push dx
        push ax
        pop eax
        
        add eax,ebx
        sub eax, dword[d+0]
        mov edx, 0
        sbb edx, dword[d+4]
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
 