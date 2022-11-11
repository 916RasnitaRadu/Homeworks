# binocdf

n=input("Please enter the number of trials: ")
p=input("Please input the probability of success: ")
x=[0:n]
y=binopdf(x,n,p)
plot(x,y,"*")
hold on # keep the graphs on the same window

# binocdf

xx=[0:0.01:n]
yy=binocdf(xx,n,p)
plot(xx,yy)
