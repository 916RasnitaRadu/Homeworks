# Application from lab 2 solved
# Write the distribution in the commentary

% a) Bino(3,0.5)
% X = [ [0 1 2 3]
%       [1/8 3/8 3/8 1/8] ]
%

# c)

%P(X=0)
binocdf(0,3,0.5)

%P(x!=1)
1 - binopdf(0,3,0.5)

# d)
%P(x<=2)
binocdf(2,3,0.5)

%P(X<=1) and P(X<2)
binocdf(1,3,0.5)

# e)

% P(X>=1) = 1 - P(X<1) = 1 - P(X<=0)
1 - binocdf(0,3,0.5)

%P(X>1) = 1 - P(X<=1)
1 - binocdf(1,3,0.5)

# f)


