package petrolpark.mc.pquality.core;

import org.apache.commons.lang3.math.Fraction;

public interface IQuality extends Comparable<IQuality> {

    public int priority();

    public double multiply(double base);

    public double bigMultiply(double base);

    public double reduce(double base);

    public int multiply(int base);

    public int bigMultiply(int base);

    public int reduce(int base);

    public int reduceToZero(int base);

    public float multiply(float base);

    public float bigMultiply(float base);

    public float reduce(float base);

    public Fraction multiply(Fraction base);

    public Fraction bigMultiply(Fraction base);

    public Fraction reduce(Fraction base);

    @Override
    default int compareTo(IQuality o) {
        return priority() - o.priority();
    };
};
