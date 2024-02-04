package polynomial;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Polynomial implements Serializable {
    private List<Integer> coefficients;

    private int degree;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
        this.degree = coefficients.size() - 1;
    }

    public Polynomial(int degree) {
        this.degree = degree;
        this.coefficients = new ArrayList<>(degree + 1);
        generateCoefficients();
    }

    public Polynomial() {
        this.coefficients = new ArrayList<>();
    }

    public void generateCoefficients() {
        Random random = new Random();

        for (int i = 0; i < degree; i++) {
            this.coefficients.add(1);
        }
        this.coefficients.add(1);
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public int getDegree() {
        return degree;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public int getSize() { return this.coefficients.size(); }

    public static Polynomial addZeros(Polynomial p, int offset) {
        List<Integer> coefficients = IntStream.range(0, offset).mapToObj(i -> 0).collect(Collectors.toList());
        coefficients.addAll(p.getCoefficients());
        return new Polynomial(coefficients);
    }

    public static Polynomial add(Polynomial p1, Polynomial p2) {
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        //Add the 2 polynomials
        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) + p2.getCoefficients().get(i));
        }

        addRemainingCoefficients(p1, p2, minDegree, maxDegree, coefficients);

        return new Polynomial(coefficients);
    }


    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        //Subtract the 2 polynomials
        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) - p2.getCoefficients().get(i));
        }

        addRemainingCoefficients(p1, p2, minDegree, maxDegree, coefficients);

        //remove coefficients starting from biggest power if coefficient is 0

        int i = coefficients.size() - 1;
        while (coefficients.get(i) == 0 && i > 0) {
            coefficients.remove(i);
            i--;
        }

        return new Polynomial(coefficients);
    }

    private static void addRemainingCoefficients(Polynomial p1, Polynomial p2, int minDegree, int maxDegree, List<Integer> coefficients) {
        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p1.getCoefficients().get(i));
                }
            } else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p2.getCoefficients().get(i));
                }
            }
        }
    }

    public static Polynomial buildEmptyPolynomial(int degree){
        List<Integer> zeros = IntStream.range(0, degree).mapToObj(i -> 0).collect(Collectors.toList());
        return new Polynomial(zeros);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (coefficients.get(degree) != 0) stringBuilder.append(coefficients.get(degree)).append("x^").append(degree);

        for (int i = degree - 1; i > 0; i--) {
            if (coefficients.get(i) != 0) {
                stringBuilder.append("+").append(coefficients.get(i)).append("x^").append(i);
            }
        }

        if (coefficients.get(0) != 0) {
            stringBuilder.append("+").append(coefficients.get(0));
        }

        return stringBuilder.toString();
    }
}
