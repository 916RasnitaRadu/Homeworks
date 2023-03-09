P = [22.3, 21.7, 24.5,23.4,21.6,23.3,22.4,21.6,24.8,20.0];
R = [17.7,14.8,19.6,19.6,12.1,14.8,15.4,12.6,14.0,12.2];

n1 = length(P);
n2 = length(R);

# we compute the means
x_1 = mean(P);
x_2 = mean(R);

# a) Assuming σ1=σ2
% For the difference of 2 population means niu_1 - niu_2, for large samples, assuming
% that we have sigma1 = sigma2 both unknown, we use the formulas where the quantiles refer to
% T(n1+n2-2) distribution

% we ask for the confidence level
confidence_level = input("Please input the 1-alpha: ");
alpha = 1 - confidence_level;


% next, we compute the sample standard variances

s1 = var(P);
s2 = var(R);

% and we compute the sp

sp = sqrt(((n1-1)*s1 + (n2-1)*s2)/(n1+n2-2));

% we compute the quantile

t = tinv(1-alpha/2,n1+n2-2);

% next, we compute the bounds

lower_bound = x_1 - x_2 - t*sp*sqrt(1/n1 + 1/n2);
upper_bound = x_1 - x_2 + t*sp*sqrt(1/n1 + 1/n2);

printf("The confidence interval for the difference of the true means, assuming sigma1 = sigma2 is (%4.3f, %4.3f).\n",lower_bound, upper_bound);

# b) Assuming σ1!=σ2
% For the difference of 2 population means niu_1 - niu_2, for large samples, assuming
% that we have sigma1 != sigma2 both unknown, we use the formulas where the quantiles refer to
% T(n) distribution

% we ask for the confidence level
confidence_level = input("Please input the 1-alpha: ");
alpha = 1 - confidence_level;

% we use s1,s2,n1,n2 to compute c

c = (s1/n1)/(s1/n1+s2/n2);

% next we compute n

n = 1 / (c*c/(n1-1) + (1-c)*(1-c)/(n2-1));

% next we compute the quantile

t = tinv(1-alpha/2,n);

% finally, we compute the bounds

lower_bound1 = x_1 - x_2 - t * sqrt(s1/n1 + s2/n2);
upper_bound1 = x_1 - x_2 + t * sqrt(s1/n1 + s2/n2);

printf("The confidence interval for the difference of the true means, assuming sigma1 != sigma2 is (%4.3f, %4.3f).\n",lower_bound1, upper_bound1);

# Find the confidence interval for the ratio of the variances

% For the ratio of 2 population variances for normal underlying populations and independent samples
% we use the formulas where the quantiles refer to F(n1-1,n2-1) distribution.

% we ask for the confidence level
confidence_level = input("Please input the 1-alpha: ");
alpha = 1 - confidence_level;

% next we compute the quantiles

f1 = finv(1-alpha/2,n1-1,n2-1);
f2 = finv(alpha/2,n1-1,n2-1);

% now we compute the bounds

lower_bound2 = 1/f1 * s1/s2;
upper_bound2 = 1/f2 * s1/s2;

printf("The confidence interval for the ratio of the variances is (%4.3f, %4.3f).\n",lower_bound2, upper_bound2);







