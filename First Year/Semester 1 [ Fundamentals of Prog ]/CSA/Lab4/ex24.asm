bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;    1098 7654 3210 9876 5432 1098 7654 3210
    m dd 0101_0011_1000_1101_1001_1111_0100_0111b
    m_new dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ex 24. : 
        ;Se da doublewordul M. Sa se obtina doublewordul MNew astfel:
        ;    bitii 0-3 a lui MNew sunt identici cu bitii 5-8 a lui M
        ;    bitii 4-7 a lui MNew au valoarea 1
        ;    bitii 27-31 a lui MNew au valoarea 0
        ;    bitii 8-26 din MNew sunt identici cu bitii 8-26 a lui M.
        
        mov ebx, 0
        mov eax, [m]
        and eax, 0000_0000_0000_0000_0000_0001_1110_0000b ; Am izolat bitii 5-8 ai lui M
        mov cl, 5
        ror eax, cl ; Am rotit bitii izolati cu 5 pozitii la dreapta
        
        or ebx, eax ; Am adaugat bitii 5-8 ai lui M pe pozitiile 0-3 ale rezultatului
        or ebx, 0000_0000_0000_0000_0000_0000_1111_0000b ; Am adaugat val 1 pe pozitiile 4-7
        
        mov eax, [m]
        and eax, 0000_0111_1111_1111_1111_1111_0000_0000b ; Am izolat bitii 8-26 ai lui M
        or ebx, eax ; I-am adaugat pe pozitiile 8-26 ale lui MNew
        
        or ebx, 0000_0000_0000_0000_0000_1111_0000_0011b
        
        mov dword[m_new], ebx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
