public class PostalDelivery extends AbstractDelivery {

    public PostalDelivery(String productName, double weight) {
        super(productName, weight, null);
    }

    @Override
    public String getType() {
        return "Почтовая";
    }

    public static double calculateCostForWeight(double weight) {
        return 300 + weight * 10;
    }

    @Override
    public double calculateCost() {
        return calculateCostForWeight(getWeight());
    }
}