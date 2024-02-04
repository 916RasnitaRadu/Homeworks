package algorithms;

import model.Polynomial;
import java.util.*;

public class SequentialClassic {

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        List<Integer> coeffs = new ArrayList<>();

        for (int i = 0; i <= p1.getDegree() + p2.getDegree(); i++) {
            coeffs.add(0);
        }

        Polynomial result = new Polynomial(coeffs);

        for (int i = 0; i < p1.getDegree() + 1; i++) {
            for (int j = 0; j < p2.getDegree() + 1; j++) {
                int newValue = result.getCoefficients().get(i + j) + p1.getCoefficients().get(i) * p2.getCoefficients().get(j);
                result.getCoefficients().set(i + j, newValue);
            }
        }
        return result;
    }
}
