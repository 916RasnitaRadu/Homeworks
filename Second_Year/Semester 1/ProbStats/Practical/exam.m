B = [3.1,2.9,3.8,3.3,2.7,3.0,2.8,2.5,2.6,2.0,3.2,2.4,2.3,3.1,2.1,3.4];
O = [6.9,6.4,4.7,4.3,5.1,6.3,5.9,5.4,5.3,5.2,5.1,5.9,5.8,4.9];

n1 = length(B); % bank length
n2 = length(O); % other length
v1 = var(B);
v2 = var(O);
# a) significance leve = 0.05
printf("a) We have a two tailed test so we use the vartest2 function.\n");
alpha = 0.05;

[H,P,CI,ZVAL] = vartest2(B,O,"alpha",alpha, 'tail','both');

% if H == 0 the H0 is not rejected, that is the population variances are equal
% if H == 1 the H1 is rejected, that is the population variances differ.

if H == 0
  printf("The null hypothesis is not rejected.\n");
  printf("The data suggests that the population variances do not differ.\n");
else
  printf("The null hypothesis is rejected.\n");
  printf("The data suggests that the population variances differ.\n");
endif

# next we compute the quantiles for the rejection region RR
f1 = finv(alpha/2,n1-1,n2-1);
f2 = finv(1-alpha/2,n1-1,n2-1);
printf("The rejection region is (-inf,%4.4f) U (%4.4f, +inf).\n",f1,f2);
printf("The P-value is %4.4f.\n",P);
printf("The observe value of the test statistic is %4.4f.\n",ZVAL.fstat);

# b) same significance level
# H0: miu1 = miu2
# H1: miu1 < miu2 - left tailed test

printf("b) We have a left tailed test.\n");

[H,P,CI,STATS] = ttest2(B,O,'alpha',alpha,'tail','left','vartype','equal');
% the vartype is set to equal because the population variances do not differ

if H == 0
  printf("The null hypothesis is not rejected.\n");
  printf("The data suggests that the other employees dispose of more white paper per year.\n");
else
  printf("The null hypothesis is rejected.\n");
  printf("The data suggests that the other employees do not dispose of more white paper per year.\n");
endif

% since the population variances do not differ, we will use the T(n1+n2-2) distribution to compute the quantile for rejection region

n = n1+n2-2;
# we compute the quantile
q = tinv(alpha,n);

printf("The rejection region is (-inf, %4.4f).\n", q);
printf("The P-value is %4.4f.\n",P);
printf("The observe value of test statistic is %4.4f.\n",STATS.tstat);

