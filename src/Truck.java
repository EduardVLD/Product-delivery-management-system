public class Truck implements ITransport {
    private final double maxWeight = 13000.0;

    @Override
    public String getName() {
        return "Грузовик";
    }

    @Override
    public boolean canCarry(double weight) {
        return weight > 0 && weight <= maxWeight;
    }
}