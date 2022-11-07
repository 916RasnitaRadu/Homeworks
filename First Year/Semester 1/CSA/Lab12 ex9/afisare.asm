bits 32
global _afisare
extern _printf
segment data public data use32
    format_signed db "%d ", 0
    format_unsigned db "%u", 10, 0
segment code public code use32

_afisare:
    push ebp
    mov ebp, esp
    mov eax, [ebp+8]
    push eax
    push dword format_signed
    call _printf
    add esp, 8
    
    mov eax, [ebp+8]
    push eax
    push dword format_unsigned
    call _printf
    add esp, 8
    mov esp, ebp
    pop ebp
    ret 8