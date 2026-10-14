public class Motorbike implements ITransport {
    private final double maxWeight = 25.0;

    @Override
    public String getName() {
        return "Мотоцикл";
    }

    @Override
    public boolean canCarry(double weight) {
        return weight > 0 && weight <= maxWeight;
    }
}