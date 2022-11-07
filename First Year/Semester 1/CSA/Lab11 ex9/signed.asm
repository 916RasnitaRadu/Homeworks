bits 32
segment code use32 public code

global signed

extern printf
import printf msvcrt.dll
; signed(format, value) - afiseaza interpretarea cu semn din hexa in decimal

signed:
    mov eax, [esp+8]
    mov edx, [esp+4]
    
    push eax
    push edx
    call [printf]
    add esp, 8
    
    ret 8
    


    