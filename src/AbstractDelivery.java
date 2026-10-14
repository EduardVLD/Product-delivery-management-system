public abstract class AbstractDelivery
        implements ITrackable, IBillable {

    private int id;
    private final String productName;
    private final double weight;
    private DeliveryStatus status;
    private final ITransport transport;

    public AbstractDelivery(
            String productName,
            double weight,
            ITransport transport) {

        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Название товара не может быть пустым");
        }

        if (!Double.isFinite(weight) || weight <= 0) {
            throw new IllegalArgumentException(
                    "Вес должен быть положительным числом");
        }

        if (transport != null && !transport.canCarry(weight)) {
            throw new IllegalArgumentException(
                    "Транспорт не подходит для этого веса");
        }

        this.productName = productName;
        this.weight = weight;
        this.transport = transport;
        this.status = DeliveryStatus.CREATED;
    }

    public abstract String getType();

    public int getId() {
        return id;
    }

    void assignId(int id) {
        if (this.id != 0) {
            throw new IllegalStateException(
                    "Доставка уже добавлена в систему");
        }
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public double getWeight() {
        return weight;
    }

    public ITransport getTransport() {
        return transport;
    }

    public String getTransportName() {
        return transport == null
                ? "Не назначен"
                : transport.getName();
    }

    @Override
    public DeliveryStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(DeliveryStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Статус не может быть пустым");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Тип: " + getType()
                + " | Товар: " + productName
                + " | Вес: " + weight + " кг"
                + " | Транспорт: " + getTransportName()
                + " | Статус: " + status;
    }
}