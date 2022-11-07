bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 12345678h, 1A2C3C4Dh, 98FCDD76h, 12783A2Bh
    len equ ($-s)/4
    d dd 0
    two db 2
    ff db 0FFh

; our code starts here
segment code use32 class=code
    start:
        ; ex.9 A list of doublewords is given. Starting from the low part of the doubleword, obtain the doubleword made of the high even bytes of the low words of each doubleword from the given list. If ;there are not enough bytes, the remaining bytes of the doubleword will be filled with the byte FFh.
        mov esi, s
        mov edi, d
        mov ecx, len
        cld
        again:
            lodsw ; In AX we will have the low part of the low part of the current doubleword. (inferior word)
            shr ax, 8 ; Ne intereseaza byte-ul mai semnificativ din acest word
            mov ah, 0 ; In AX vom avea doar byte-ul superior din word
            mov dl, al ; ii facem o copie octetului din al
            div byte[two]
            cmp ah,0
            
            jnz impar ; Daca octetul este impar, adica restul din ah nu este zero, vom sari peste urmatorii pasi
            mov al, dl ; mutam in al byte-ul anterior retinut 
            stosb
            impar:
            lodsw
        loop again
            
        mov ecx, 4 ; 4 in ecx pt ca un dd are 4 bytes
        mov esi, 0
        mov ebx, d
        mov ax,0
        again2:
            mov al, [ebx+esi]
            cmp al,0
            jnz continua ; verificam daca este 0, iar daca da punem pe pozitia lui byte-ul 0FFh
            mov byte[ebx+esi], 0FFh 
            
            continua:
            inc esi
        loop again2
        
        mov ebx, d
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
