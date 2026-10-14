public class CourierDelivery extends AbstractDelivery {

    public CourierDelivery(
            String productName,
            double weight,
            ITransport transport) {

        super(productName, weight, transport);

        if (!(transport instanceof Motorbike)
                && !(transport instanceof Van)) {
            throw new IllegalArgumentException(
                    "Для курьерской доставки нужен мотоцикл или фургон");
        }
    }

    @Override
    public String getType() {
        return "Курьерская";
    }

    public static double calculateCostForWeight(double weight) {
        return 300 + weight * 20;
    }

    @Override
    public double calculateCost() {
        return calculateCostForWeight(getWeight());
    }
}