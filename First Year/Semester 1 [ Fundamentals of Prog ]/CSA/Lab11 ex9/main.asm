bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf   
import exit msvcrt.dll    
import scanf msvcrt.dll
import printf msvcrt.dll

extern signed, unsigned
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a dd 0
    format_hexa db "%x", 0
    format_deci_sign db "%d", 0
    format_deci_unsign db "%u", 0
    format_ch db "%c", 0

; our code starts here   
segment code use32 class=code
    start:
    
        ; signed(format, val) / unsigned(format, val)
       ;  push val
       ;  push format
       ;  call signed/unsigned
        ; ex9: Se cere sa se citeasca de la tastatura un sir de numere, date in baza 16 . Sa se afiseze valoarea zecimala a nr atat ca numere fara semn cat si ca numere cu semn.
        
        ; citim cate un nr(sau tot sirul)
        ; facem un modul pt afisare cu semn si unul pt afisare fara semn?
        
        mov ecx, 0
        again:
            mov ebx, ecx
            
            push dword a
            push dword format_hexa
            call [scanf]
            add esp, 8
            
        
            cmp [a], dword 0
            je final
            
            push dword [a]
            push format_deci_sign
            call signed
            
            
            push dword 32
            push format_ch
            call [printf]
            add esp, 8
            
            push dword [a]
            push format_deci_unsign
            call unsigned
       
            
            push dword 32
            push format_ch
            call [printf]
            add esp, 8
            
            mov ecx, ebx
        loop again
        final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
