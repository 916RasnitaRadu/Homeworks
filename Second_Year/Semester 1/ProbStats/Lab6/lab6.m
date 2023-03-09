# exercise 1
# a)
# what are we interested finding out, what is our unknown, what needs estimation?
# H0: miu = 9
# H1: miu < 9
# this is a left-tailed test for the mean when sigma is known

alpha = input("Please enter the significance level: ");
x = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7]; # the sample
n = length(x);
% the null hypothesis is H0: miu = 9
% the alternative hypothesis is H1: miu < 9
% left-tailed test for the mean, when sigma known
printf("Left tailed test for miu when sigma known\n");
m0 = 9;
sigma = 5;

% [H, PVAL, CI, Z, ZCRIT] = ztest (X, M, S, NAME, VALUE)
% H is either 0 or 1
% if H == 0 then H0 is rejected
% if H == 1 then H0 is not rejected
% Z is TS0
% X is the datasample
% M is m0
% S is sigma
% NAME belongs {"alpha", "tail"} - name of parameter
% VALUE depends on name - value of the parameter

[h,p,ci,z,zcrit] = ztest(x,m0,sigma,"alpha",alpha,"tail","left");

% the quantile of order alpha
z2 = norminv(alpha);
RR = [-inf, z2]

printf("The value of H is %d.\n", h);
if h == 1
  printf("The null hypothesis H0 is rejected.\n");
  printf("The data sugests that the standard is not met.\n");
else
  printf("The null hypothesis H0 is not rejected.\n");
  printf("The data sugests that the standard is met.\n");
endif
printf("The rejection region is (%4.4f, %4.4f).\n", RR);
printf("The observe value of the test statistic is %4.4f.\n",z);
printf("The P-value of the test is %4.4f.\n", p);

# b)
# now miu = 5.5
m0 = 5.5;
% right-tailed test for the mean, when sigma unknown
% [H,P,CI,STATS] = ttest(X,M,NAME, VALUE)
% STATS is a struct which contains TS0 => STATS.tstat

[h,p,ci,STATS] = ttest(x,m0,"alpha",alpha, "tail","right");
t2 = tinv(1-alpha, n-1);
RR = [t2,inf];

printf("/nThe rejection region is (%4.4f, %4.4f).\n", RR);
printf("The observe value of the test statistic is %4.4f.\n",STATS.tstat);
printf("The P-value of the test is %4.4f.\n", p);

























