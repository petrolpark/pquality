package petrolpark.mc.pquality.core;

public final class NoQuality implements IQuality {

    NoQuality() {};

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    };

    @Override
    public double multiply(double base) {
        return base;
    };

    @Override
    public double bigMultiply(double base) {
        return base;
    };

    @Override
    public double reduce(double base) {
        return base;
    };

    @Override
    public int multiply(int base) {
        return base;
    };

    @Override
    public int bigMultiply(int base) {
        return base;
    };

    @Override
    public int reduce(int base) {
        return base;
    };

    @Override
    public int reduceToZero(int base) {
        return base;
    };

    @Override
    public float multiply(float base) {
        return base;
    };

    @Override
    public float bigMultiply(float base) {
        return base;
    };

    @Override
    public float reduce(float base) {
        return base;
    };
    
};
