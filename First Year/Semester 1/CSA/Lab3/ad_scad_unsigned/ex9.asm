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
    b dw 4
    c dd
    d dq 134
    r resq 1

; our code starts here
segment code use32 class=code
    start:
        ; a - byte, b - word, c - doubleword, d - quadword
        ; ex. 9: (d+d-b)+(c-a)+d
        mov eax, dword[d+0]
        mov edx, dword[d+4]
        
        mov ebx, dword[d+0]
        mov ecx, dword[d+4]
        
        add eax,ebx
        adc edx,ecx
        
        mov dword[r+0], eax
        mov dword[r+4], edx ; r = d+d
        
        mov dx,0
        mov ax, [b]
        
        push dx
        push ax
        pop eax
        
        sub dword[r+0],eax ; r = d+d-b
        
        mov ebx, [c]
        mov ah,0
        mov dx,0
        mov al,[a] ; AX = a
        
        push dx
        push ax
        pop eax ; EAX = a
        
        sub ebx,eax ; EBX = c -a
        
        mov eax,ebx
        mov edx, 0
        add dword[r+0], eax
        adc dword[r+4], edx
        
        mov ebx, dword[d+0]
        mov ecx, dword[d+4]
        
        add dword[r+0], ebx
        adc dword[r+4], ecx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
