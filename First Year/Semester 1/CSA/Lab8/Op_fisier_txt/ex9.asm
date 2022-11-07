bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, fopen, fclose, fread               
import exit msvcrt.dll    
import printf msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll  ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    nume_fisier db "caracter.txt", 0
    mod_acces db "r", 0
    descriptor_fis dd -1
    len equ 120
    text times len db 0
    sir times 256 db 0
    caracter db 0
    maxi db -1
    format1 db "%d", 0
    format2 db "%c", 0

; our code starts here
segment code use32 class=code
    start:
        ; e.9: Se da un fisier text. Sa se citeasca continutul fisierului, sa se determine caracterul special (diferit de litera) cu cea mai mare frecventa si sa se afiseze acel caracter, impreuna cu frecventa acestuia. Numele fisierului text este definit in segmentul de date.
        push dword mod_acces 
        push dword nume_fisier ; punem parametrii pe stiva
        call [fopen] ; apelam functia fopen
        add esp, 8 ; eliberam stiva 
        
        mov [descriptor_fis], eax ; salvam valoarea returnata de fopen in variabila descriptor_fis
        
        cmp eax, 0   ; verificam daca fisierul a fost deschis cu succes
        je final
        
        push dword [descriptor_fis]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 16
        
        mov esi, text
        mov ecx, eax
        cld
        
        again:
            lodsb
            
            cmp al, 0x41 ; compare al with "A"
            jl caracter_special ; daca e mai mic decat "A" este un caracter special
            
            cmp al, 0x5A ; compare al with "Z"
            jle sfarsit_loop ; daca e mai mic sau egal decat "Z" este o litera 
            
            cmp al, 0x61 ; compare al with "a"
            jl caracter_special ; daca e mai mic decat "a" e un caracter special (fiind intre "Z" si "a")
            
            cmp al, 0x7A ; compare al with "z"
            jg caracter_special ; daca e mai mare decat "z" e un caracter special
            
            caracter_special:
            inc byte[sir+eax]    ; Incrementam frecventa pentru caracterul special de pe pozitia respectiva cu 1
            
            sfarsit_loop:
        loop again
            
        
        mov ecx, 256
        mov esi, 0 ; mutam 0 in esi pentru ca vom parcurge sirul de caractere speciale cu ajutorul sau
        
        loop_max_ch:
            mov al, [sir+esi]   ; luam frecventa caracterului de pe pozitia curenta din sir
            
            cmp al, [maxi]      ; il comparam cu frecventa caracterului care are nr max de aparitii
            jg max_nou
            jmp next_max
            
            max_nou:            ; daca are nr mai mare de aparitii ii dam variabilei maxi valoarea respectiva si variabilei caracter care retine caracterul special caract resp
                mov [maxi], al
                mov [caracter], esi    
            
            next_max:
                inc esi     
        loop loop_max_ch
           
        done:     
        push dword [caracter]
        push dword format2
        call [printf]
        add esp, 8
        
        push dword [maxi]
        push dword format1
        call [printf]
        add esp, 8
        
        
        push dword [descriptor_fis]
        call [fclose]
        add esp, 4
        
        final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
