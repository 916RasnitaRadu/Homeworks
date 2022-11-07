# EX 2 Normal

p = input("p=")

for n = 1:10:1000
  x = [0:n]
  plot(x, binopdf(x, n, p))
  pause(0.5)

 endfor
