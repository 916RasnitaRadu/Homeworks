package util;

import model.Polynomial;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static Polynomial add(Polynomial p1, Polynomial p2) {
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());

        List<Integer> coeffs = new ArrayList<>(maxDegree + 1);

        // Add 2 polynomials
        for(int i = 0; i <= minDegree; i++){
            coeffs.add(p1.getCoefficients().get(i) + p2.getCoefficients().get(i));
        }


        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coeffs.add(p1.getCoefficients().get(i));
                }
            }
            else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coeffs.add(p2.getCoefficients().get(i));
                }
            }
        }
        return new Polynomial(coeffs);
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());

        List<Integer> coeffs = new ArrayList<>(maxDegree + 1);

        for(int i = 0; i <= minDegree; i++){
            coeffs.add(p1.getCoefficients().get(i) - p2.getCoefficients().get(i));
        }

        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coeffs.add(p1.getCoefficients().get(i));
                }
            }
            else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coeffs.add(p2.getCoefficients().get(i));
                }
            }
        }

        int i = coeffs.size() - 1;
        while (coeffs.get(i) == 0 && i > 0) {
            coeffs.remove(i);
            i--;
        }
        return new Polynomial(coeffs);
    }

    public static Polynomial addZeros(Polynomial p, int offset) {
        List<Integer> coefficients = IntStream.range(0, offset).mapToObj(i -> 0).collect(Collectors.toList());
        coefficients.addAll(p.getCoefficients());
        return new Polynomial(coefficients);
    }
}
