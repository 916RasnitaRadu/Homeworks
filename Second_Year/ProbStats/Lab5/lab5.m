# B1a

x = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7] # the sample
n = length(x) # the sample length

x_ = mean(x);
sigma = 5;
confidence_level = input("Please insert the value of 1 - alpha: ");
alpha = 1 - confidence_level;

z1 = norminv(1 - alpha/2);
z2 = norminv(alpha/2);

m1 = x_ - sigma/(sqrt(n)) * z1;
m2 = x_ - sigma/(sqrt(n)) * z2;

printf("The confidence interval for the population mean when sigma known is %4.3f, %4.3f\n", m1, m2)

# B1b

s = std(x);
t1 = tinv(1 - alpha/2, n-1);
t2 = tinv(alpha/2, n-1);

u1 = x_ - s/(sqrt(n)) * t1;
u2 = x_ - s/(sqrt(n)) * t2;

printf("The confidence interval fot the population mean when sigma is unknown is %4.3f, %4.3f\n", u1, u2)

# B1c

sample_variance = var(x);
kai1 = chi2inv(1 - alpha/2, n-1);
kai2 = chi2inv(alpha/2, n - 1);

w1 = (n-1)*sample_variance/kai1;
w2 = (n-1)*sample_variance/kai2;

printf("The confidence interval for the variance is %4.3f, %4.3f\n", w1, w2)
printf("The confidence interval for the standard deviation is %4.3f, %4.3f\n", sqrt(w1), sqrt(w2))



