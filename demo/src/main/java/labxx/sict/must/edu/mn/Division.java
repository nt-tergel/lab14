package labxx.sict.must.edu.mn;

/**
 * Division calculator class.
 */
public class Division {
    /**
     * Divides two numbers.
     *
     * @param a Dividend
     * @param b Divisor
     * @return Quotient of a divided by b
     * @throws IllegalArgumentException if b is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}
