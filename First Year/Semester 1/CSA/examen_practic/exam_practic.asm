bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, printf, fscanf
import exit msvcrt.dll   
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fscanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    nume_fisier db "prob.txt", 0 ;numele fisierului
    mod_acces db "r+", 0    ; modul de acces
    format_dec db "%d", 0   ; formatul pt decimal
    format_str db "%s", 0   ; formatul pt string
    format_chr db "%c", 0   ; formatul pt character
    descriptor_fis dd -1    ; descriptorul fisierului   
    text_litere db "Numarul literelor mari este: ", 0
    text_cifre db "Numarul cifrelor este: ", 0
    spatiu db " ", 0
    chr dd 0                ; variabila in care va fi stocat caracterul citit
    majuscule dd 0          ; variabila in care vom numara cate litere mari am gasit
    cifre dd 0              ; variabila in care vom numara cate cifre am gasit
    
; our code starts here
segment code use32 class=code
    start:
        ; Se citesc dintr-un fisier caractere, pana la intalnirea caracterului *. Sa se afiseze la consola numarul literelor mari, urmat de numarul cifrelor citite.
        
        ; deschidem fisierul din care citim
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 8
        
        ;verificam daca fisierul a fost deschis corect
        cmp eax, 0
        je final
        
        mov [descriptor_fis], eax
        
        Repeta:
            ; citim cate un caracter din fisier
            push dword chr 
            push dword format_chr
            push dword [descriptor_fis]
            call [fscanf]
            add esp, 12
            
            ; verificam daca caracterul citit este '*'. iar daca da iesim din loop
            mov eax, [chr]
            cmp eax, '*'
            je final_loop
            
            ; verificam daca caracterul citit este litera_mare
            cmp eax, 'A'
            jae next_lit
            
            ; verificam daca caracterul citit este litera_mare
            cmp eax, '0'
            jae next_cif
            
            ; daca nu este nici litera mare, nici cifra continuam sa citim caractere
            jmp continua
            
            next_lit:
            cmp eax, 'Z'
            jbe litera_mare
            ja continua
            
            ; daca este litera mare incrementam variabila majuscule 
            litera_mare:
            add [majuscule], dword 1
            jmp continua
            
            next_cif:
            cmp eax, '9'
            jbe cifra
            ja continua
            
            ; daca este cifra incrementam variabila cifre
            cifra:
            add [cifre], dword 1
            
            continua:
        jmp Repeta
        final_loop:
        
        ; afisam nr de litere mari
        push dword text_litere
        push dword format_str
        call [printf]
        add esp, 8
        
        push dword [majuscule]
        push dword format_dec
        call [printf]
        add esp, 8
        
        ; afisam un spatiu
        push dword 10
        push dword format_chr
        call [printf]
        add esp, 8
        
        ; afisam nr de cifre
        push dword text_cifre
        push dword format_str
        call [printf]
        add esp, 8
        
        push dword [cifre]
        push dword format_dec
        call [printf]
        add esp, 8
        
        ; inchidem fisierul
        push dword [descriptor_fis]
        call [fclose]
        add esp, 4
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
