bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 300
    b db 10
    c db 50
    d db 4
    x dq 12345

; our code starts here
segment code use32 class=code
    start:
        ; ex24: a-(7+x)/(b*b-c/d+2); a-doubleword; b,c,d-byte; x-qword
        mov al, [b]
        imul byte[b] ; AX = b*b
        mov bx,ax    ; BX = b*b
        
        mov al,[c]
        cbw           ; AX = c
        idiv byte[d]  ; AL = c/d and AH = c%d
        
        cbw
        sub bx, ax
        add bx, 2 ; BX = (b*b-c/d+2)
        mov eax, dword[x+0]
        mov edx, dword[x+4]
        add eax, 7
        adc edx, 0
        
        mov ecx,eax
        mov ax,bx
        cwde    ; EAX = (b*b-c/d+2)
        
        mov ebx, eax ; EBX = (b*b-c/d+2)
        mov eax, ecx
        idiv ebx 
        
        mov ecx, [a]
        sub ecx, eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
