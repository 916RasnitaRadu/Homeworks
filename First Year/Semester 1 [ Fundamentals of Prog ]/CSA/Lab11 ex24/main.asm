bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               
import exit msvcrt.dll    
import printf msvcrt.dll
import scanf msvcrt.dll
extern translator
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    alfabet db 'OPQRSTUVWXYZABCDEFGHIJKLMN'
    text times 100 db 0
    format_str db "%s", 0
    rezultat times 100 db 0
    a db 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ex 24 Sa se citeasca un sir s1 (care contine doar litere mici). Folosind un alfabet (definit in segmentul de date), determinati si afisati sirul s2 obtinut prin substituirea fiecarei litere a sirului s1 cu litera corespunzatoare din alfabetul dat.
            ;Exemplu:
            ;Alfabetul: OPQRSTUVWXYZABCDEFGHIJKLMN
            ;Sirul s1:  anaaremere
            ;Sirul s2:  OBOOFSASFS
        push dword text
        push dword format_str
        call [scanf]        ; citim primul sir de la tastatura
        add esp, 8
        
        mov ecx, 100
        mov ebx, alfabet    ; punem in ebx adresa sirului-alfabet
        mov esi, text       ; punem in esi adresa sirului-sursa (text)
        mov edi, rezultat   ; punem in edi adresa sirului-destinatie (rezultat)
        again:
            lodsb           ; incarcam primul caracter in al
            
            cmp al, 0       ; daca acesta este zero vom iesi din loop
            je final
                        
            mov [a], al
            push dword [a]
            push ebx
            call translator
            
            stosb           ; incarcam in sirul-destinatie caracterul gasit
        loop again
        
        final:
        push dword rezultat
        push dword format_str
        call [printf]       ; afisam rezultatul
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
