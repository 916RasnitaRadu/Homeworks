bits 32
global _translator

segment data public data use32
    alfabet db 'OPQRSTUVWXYZABCDEFGHIJKLMN'
segment code public code use32

_translator:
    push ebp
    mov ebp, esp
    mov ebx, alfabet
    mov eax, [ebp+8]
    sub eax, 97
    xlat
    mov bl, al
    mov eax, 0
    mov al, bl
    mov esp, ebp
    pop ebp
    ret