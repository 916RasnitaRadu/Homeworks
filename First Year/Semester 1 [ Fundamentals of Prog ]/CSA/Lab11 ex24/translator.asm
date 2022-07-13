bits 32
segment code use32 public code

global translator

;translator(alfabet, nr)

translator:
    mov eax, [esp+8]
    mov ebx, [esp+4]
    sub eax, 97
    xlat ; scadem 97 din codul ascii al caracterului (97-codul ascii al lui 'a') si il cautam in alfabet cu ajutorul instructiunii xlat care va pune in AL caracterul corespunzator
    
    ret 8
    