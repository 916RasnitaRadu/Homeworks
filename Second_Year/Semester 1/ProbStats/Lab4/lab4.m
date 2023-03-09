%Lab4

%ex2
clear
p = input('give the probability: ');
N = input('give the nb of sim: '); # random numbers that you wish
U = rand(1,N);
X = (U < p);
U_X = unique(X)
n_X = hist(X,length(U_X)); # how much 0's and how much 1's I have
rel_freq = n_X / N

%ex3
clear
p = input('give the probability: ');
n = input('give the nb of trials: ');
N = input('give the nb of sim: ');
for i=1:N
	U = rand(n,1);
	X(i) = sum(U<p);
endfor
k=0:n;
U_X = unique(X);
n_X = hist(X,length(U_X));
rel_freq = n_X / N;
plot(U_X,rel_freq,'*',k,binopdf(k,n,p),'*r')

%ex4
clear
p = input('give the probability ');
N = input('give the nb of sim ');
for i=1:N
	X(i) = 0;
	while rand >= p
		X(i) = X(i)+1;
	endwhile
endfor
k=0:20;
U_X = unique(X);
n_X = hist(X,length(U_X));
rel_freq = n_X / N;
plot(U_X,rel_freq,'*',k,geopdf(k,p),'*r')

% exercise 2d it is going to be a combination betw 2b and 2c
