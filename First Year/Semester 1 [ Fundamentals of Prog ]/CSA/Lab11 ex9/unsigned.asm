bits 32
segment code use32 public code

global unsigned

extern printf
import printf msvcrt.dll
; signed(format, value) - afiseaza interpretarea fara semn din hexa in decimal

unsigned:
    mov eax, [esp+8]
    mov edx, [esp+4]
    
    push eax
    push edx
    call [printf]
    add esp, 8
    
    ret 8