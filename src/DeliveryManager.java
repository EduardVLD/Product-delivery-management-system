import java.util.Arrays;

public class DeliveryManager {

    private AbstractDelivery[] deliveries =
            new AbstractDelivery[5];

    private int count = 0;
    private int nextId = 1;

    public void addDelivery(AbstractDelivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException(
                    "Доставка не может быть пустой");
        }

        if (count == deliveries.length) {
            deliveries = Arrays.copyOf(
                    deliveries, deliveries.length * 2);
        }

        delivery.assignId(nextId++);
        deliveries[count] = delivery;
        count++;
    }

    public AbstractDelivery[] getAllDeliveries() {
        return Arrays.copyOf(deliveries, count);
    }

    public AbstractDelivery findById(int id) {
        for (int i = 0; i < count; i++) {
            if (deliveries[i].getId() == id) {
                return deliveries[i];
            }
        }

        return null;
    }

    public AbstractDelivery[] findByStatus(
            DeliveryStatus status) {

        AbstractDelivery[] result =
                new AbstractDelivery[count];

        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (deliveries[i].getStatus() == status) {
                result[resultCount] = deliveries[i];
                resultCount++;
            }
        }

        return Arrays.copyOf(result, resultCount);
    }

    public int getCount() {
        return count;
    }
    public boolean deleteById(int id) {
        for (int i = 0; i < count; i++) {
            if (deliveries[i].getId() == id) {

                // Сдвигаем оставшиеся элементы влево
                for (int j = i; j < count - 1; j++) {
                    deliveries[j] = deliveries[j + 1];
                }

                // Очищаем последнюю занятую ячейку
                deliveries[count - 1] = null;
                count--;

                return true;
            }
        }

        return false;
    }
}