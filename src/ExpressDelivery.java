public class ExpressDelivery extends AbstractDelivery {

    public ExpressDelivery(
            String productName,
            double weight,
            ITransport transport) {

        super(productName, weight, transport);

        if (!(transport instanceof Motorbike)
                && !(transport instanceof Van)) {
            throw new IllegalArgumentException(
                    "Для экспресс-доставки нужен мотоцикл или фургон");
        }
    }

    @Override
    public String getType() {
        return "Экспресс";
    }

    public static double calculateCostForWeight(double weight) {
        return 500 + weight * 50;
    }

    @Override
    public double calculateCost() {
        return calculateCostForWeight(getWeight());
    }
}