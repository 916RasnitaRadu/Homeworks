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
    b db 15
    c db 2
    d dw 10
    e dw 3
    f dw 3
    

; our code starts here
segment code use32 class=code
    start:
       
        
        
        
        
        ; a,b,c,d-byte, e,f,g,h-word
        ; ex 9: (2*d+e)/a ( a = 3, d = 15, e = 3)
        mov al, 2
        mul byte[d]
        add ax, [e] ; AX = (2*d+e)
        
      ;  mov bl, [a]
      ;  div bl ; AH = AX mod BL and AL = AX / BL
        mov dx, 0
        div word[f]
        
        
        
        
        
        ; ex 24: [(a-d)+b]*2/c ( a  = 15, b = 4 , c = 3, d = 12)
        
        mov al, [a]
        sub al, [d]
        add al, [b] ; AL = (a-d)+b = 7
        
        mov bl, 2
        mul bl ; AX = [(a-d)+b]*2 = 14
        
        mov bl, [c]
        div bl ; AH = AX mod BL and AL = AX / BL
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
