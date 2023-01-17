# B. Confidence intervals

x = [7,7,4,5,9,9,4,12,8,1,8,7,3,13,2,1,17,7,12,5,6,2,1,13,14,10,2,4,9,11,3,5,12,6,10,7];
n = length(x);

# a) sigma = 5 (KNOWN) find a 100(1-alpha)% confidence interval for the average nr of files stored

% Because sigma is known and we have a large sample(n>30) we find the mean x_ and we use
% the formula where the quantiles refer to the N(0,1) distribution
sigma = 5;
x_ = mean(x);
% we ask for the confidence_level
confidence_level = input("Please input the valuea of 1-alpha: ");
alpha = 1 - confidence_level;
# we compute the quantiles
z1 = norminv(1-alpha/2);
z2 = norminv(alpha/2);

lower_bound = x_ - (sigma * z1)/sqrt(n);
upper_bound = x_ - (sigma * z2)/sqrt(n);

printf("The confidence interval for the population mean when sigma known is (%4.3f, %4.3f).\n",lower_bound, upper_bound);

# b) sigma unknown

% Because sigma is unknown, we use the formula where the quantiles refer to the T(n-1) distribution.
% we ask for the confidence_level
confidence_level = input("Please input the valuea of 1-alpha: ");
alpha = 1 - confidence_level;
# we compute the sample standard deviation
s = std(x);

z3 = tinv(1-alpha/2,n-1); # we compute the quantiles
z4 = tinv(alpha/2, n-1);

lower_bound1 = x_ - (s * z3)/sqrt(n);
upper_bound1 = x_ - (s * z4)/sqrt(n);

printf("The confidence interval for the population mean when sigma unknown is (%4.3f, %4.3f).\n",lower_bound1, upper_bound1);

# c) the nr of files stored are approximately normally distributed, find conf intervals for the variance and for the standard deviation

# For a population variance, for a normal underlying population, we use the formula where the quantiles refer to the chi2(n-1) distribution.
% we ask for the confidence_level
confidence_level = input("Please input the valuea of 1-alpha: ");
alpha = 1 - confidence_level;
% we compute the sample standard variance
s2 = var(x);

# we compute the quantiles
z5 = chi2inv(1-alpha/2,n-1);
z6 = chi2inv(alpha/2,n-1);

lower_bound2 = ((n-1)*s2)/z5;
upper_bound2 = ((n-1)*s2)/z6;

printf("The confidence interval for the population variance is (%4.3f, %4.3f).\n",lower_bound2,upper_bound2);
# And the confidence interval bounds for the population standard deviation is simply the square roots of bounds for the population variance interval.
printf("The confidence interval for the population standard deviation is (%4.3f, %4.3f).\n",sqrt(lower_bound2),sqrt(upper_bound2));

