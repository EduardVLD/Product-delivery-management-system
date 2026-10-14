public class Van implements ITransport {
    private final double maxWeight = 1500.0;

    @Override
    public String getName() {
        return "Фургон";
    }

    @Override
    public boolean canCarry(double weight) {
        return weight > 0 && weight <= maxWeight;
    }
}