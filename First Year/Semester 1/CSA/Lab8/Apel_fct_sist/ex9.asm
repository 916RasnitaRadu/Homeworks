bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 0
    message1 db "a = ", 0
    b dd 0
    message2 db "b = ", 0
    format db "%d", 0
    rezultat db 0

; our code starts here
segment code use32 class=code
    start:
    ;Sa se citeasca de la tastatura doua numere a si b (in baza 10) si sa se calculeze: (a+b) / (a-b). Catul impartirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date). Valorile se considera cu semn.
        push dword message1
        call [printf]
        add esp, 4  ; afisam mesajul "a="
    
        push dword a
        push dword format
        call [scanf]
        add esp, 8   ; citim numarul natural a de la tastatura
        
        push dword message2
        call [printf]
        add esp, 4  ; afisam mesajul "b="
    
        push dword b
        push dword format
        call [scanf]
        add esp, 8   ; citim numarul natural b de la tastatura
    
        mov eax, [a]
        add eax, [b] ; Am calculat a+b
        cdq          ; Convertim rezultatul la quadword, deci (a+b) - quadword
        
        mov ebx, [a]
        sub ebx, [b] ; Am calculat (a-b) - doubleword
        
        idiv ebx ; Impartim cu ebx. In eax vom avea catul, iar in edx restul
        
        mov [rezultat], eax
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
