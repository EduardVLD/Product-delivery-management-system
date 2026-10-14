
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final DeliveryManager manager =
            new DeliveryManager();

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Система управления доставкой");

        while (running) {
            printMenu();

            int choice = readInt("Выберите действие: ");

            try {
                switch (choice) {
                    case 1:
                        addDelivery();
                        break;
                    case 2:
                        showAllDeliveries();
                        break;
                    case 3:
                        calculateDeliveryCost();
                        break;
                    case 4:
                        changeStatus();
                        break;
                    case 5:
                        searchByStatus();
                        break;
                    case 6:
                        showTransport();
                        break;
                    case 7:
                        calculateWithoutCreating();
                        break;
                    case 8:
                        deleteDelivery();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Программа завершена.");
                        break;
                    default:
                        System.out.println(
                                "Такого пункта меню нет.");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("================================");
        System.out.println("  СИСТЕМА УПРАВЛЕНИЯ ДОСТАВКОЙ");
        System.out.println("================================");
        System.out.println("1. Добавить доставку");
        System.out.println("2. Показать все доставки");
        System.out.println("3. Рассчитать стоимость доставки");
        System.out.println("4. Изменить статус доставки");
        System.out.println("5. Найти доставки по статусу");
        System.out.println("6. Показать доступный транспорт");
        System.out.println("7. Рассчитать стоимость без создания");
        System.out.println("8. Удалить доставку");
        System.out.println("0. Выход");
        System.out.println("================================");
    }

    // 1. Добавление доставки
    private static void addDelivery() {
        System.out.println("Выберите тип доставки:");
        System.out.println("1. Курьерская");
        System.out.println("2. Почтовая");
        System.out.println("3. Экспресс");

        int type = readInt("Тип: ");

        if (type < 1 || type > 3) {
            System.out.println("Неизвестный тип доставки.");
            return;
        }

        System.out.print("Название товара: ");
        String productName = scanner.nextLine().trim();

        double weight = readDouble("Вес товара (кг): ");

        AbstractDelivery delivery;

        if (type == 2) {
            delivery = new PostalDelivery(
                    productName, weight);
        } else {
            ITransport transport = chooseTransport();

            if (transport == null) {
                System.out.println(
                        "Неверный выбор транспорта.");
                return;
            }

            if (type == 1) {
                delivery = new CourierDelivery(
                        productName, weight, transport);
            } else {
                delivery = new ExpressDelivery(
                        productName, weight, transport);
            }
        }

        manager.addDelivery(delivery);

        System.out.println("Доставка успешно добавлена!");
        System.out.println(delivery);
        System.out.println("Стоимость: "
                + formatMoney(delivery.calculateCost())
                + " руб.");
    }

    // Выбор транспорта
    private static ITransport chooseTransport() {
        System.out.println("Выберите транспорт:");
        System.out.println("1. Мотоцикл (до 20 кг)");
        System.out.println("2. Фургон (до 1000 кг)");

        int choice = readInt("Транспорт: ");

        switch (choice) {
            case 1:
                return new Motorbike();
            case 2:
                return new Van();
            default:
                return null;
        }
    }

    // 2. Просмотр всех доставок
    private static void showAllDeliveries() {
        AbstractDelivery[] all =
                manager.getAllDeliveries();

        if (all.length == 0) {
            System.out.println("Доставок пока нет.");
            return;
        }

        for (AbstractDelivery delivery : all) {
            System.out.println(delivery);
            System.out.println("Стоимость: "
                    + formatMoney(delivery.calculateCost())
                    + " руб.");
        }
    }

    // 3. Расчёт стоимости существующей доставки
    private static void calculateDeliveryCost() {
        int id = readInt("Введите ID доставки: ");

        AbstractDelivery delivery = manager.findById(id);

        if (delivery == null) {
            System.out.println("Доставка не найдена.");
            return;
        }

        System.out.println("Товар: "
                + delivery.getProductName());
        System.out.println("Тип: " + delivery.getType());
        System.out.println("Стоимость: "
                + formatMoney(delivery.calculateCost())
                + " руб.");
    }

    // 4. Изменение статуса доставки
    private static void changeStatus() {
        int id = readInt("Введите ID доставки: ");

        AbstractDelivery delivery = manager.findById(id);

        if (delivery == null) {
            System.out.println("Доставка не найдена.");
            return;
        }

        System.out.println("Выберите новый статус:");
        System.out.println("1. CREATED — создана");
        System.out.println("2. IN_PROGRESS — в пути");
        System.out.println("3. DELIVERED — доставлена");
        System.out.println("4. CANCELLED — отменена");

        int choice = readInt("Статус: ");

        if (choice < 1 || choice > 4) {
            System.out.println("Неверный статус.");
            return;
        }

        DeliveryStatus[] statuses = DeliveryStatus.values();

        delivery.setStatus(statuses[choice - 1]);

        System.out.println("Статус изменён: "
                + delivery.getStatus());
    }

    // 5. Поиск доставок по статусу
    private static void searchByStatus() {
        System.out.println("Выберите статус:");
        System.out.println("1. CREATED");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. DELIVERED");
        System.out.println("4. CANCELLED");

        int choice = readInt("Статус: ");

        if (choice < 1 || choice > 4) {
            System.out.println("Неверный статус.");
            return;
        }

        DeliveryStatus status =
                DeliveryStatus.values()[choice - 1];

        AbstractDelivery[] found =
                manager.findByStatus(status);

        if (found.length == 0) {
            System.out.println(
                    "Доставок с таким статусом нет.");
            return;
        }

        for (AbstractDelivery delivery : found) {
            System.out.println(delivery);
        }
    }

    // 6. Просмотр доступного транспорта
    private static void showTransport() {
        ITransport[] transports = {
                new Motorbike(),
                new Van(),
                new Truck()
        };

        System.out.println("Доступный транспорт:");

        for (ITransport transport : transports) {
            System.out.println("- " + transport.getName());
        }
    }

    // 7. Расчёт стоимости без создания доставки
    private static void calculateWithoutCreating() {
        System.out.println("Выберите тип доставки:");
        System.out.println("1. Курьерская");
        System.out.println("2. Почтовая");
        System.out.println("3. Экспресс");

        int type = readInt("Тип: ");

        if (type < 1 || type > 3) {
            System.out.println("Неизвестный тип доставки.");
            return;
        }

        double weight = readDouble("Вес товара (кг): ");
        double cost;

        switch (type) {
            case 1: {
                ITransport transport = chooseTransport();

                if (transport == null) {
                    System.out.println(
                            "Неверный выбор транспорта.");
                    return;
                }

                if (!transport.canCarry(weight)) {
                    System.out.println(
                            "Транспорт не подходит для этого веса.");
                    return;
                }

                cost = CourierDelivery
                        .calculateCostForWeight(weight);
                break;
            }

            case 2:
                cost = PostalDelivery
                        .calculateCostForWeight(weight);
                break;

            case 3: {
                ITransport transport = chooseTransport();

                if (transport == null) {
                    System.out.println(
                            "Неверный выбор транспорта.");
                    return;
                }

                if (!transport.canCarry(weight)) {
                    System.out.println(
                            "Транспорт не подходит для этого веса.");
                    return;
                }

                cost = ExpressDelivery
                        .calculateCostForWeight(weight);
                break;
            }

            default:
                return;
        }

        System.out.println("Расчётная стоимость: "
                + formatMoney(cost) + " руб.");

        System.out.println(
                "Доставка не создана и не добавлена в систему.");
    }

    // 8. Удаление доставки
    private static void deleteDelivery() {
        int id = readInt(
                "Введите ID доставки для удаления: ");

        AbstractDelivery delivery = manager.findById(id);

        if (delivery == null) {
            System.out.println(
                    "Доставка с таким ID не найдена.");
            return;
        }

        System.out.println("Будет удалена доставка:");
        System.out.println(delivery);

        System.out.print(
                "Подтвердить удаление? (да/нет): ");

        String answer = scanner.nextLine().trim();

        if (answer.equalsIgnoreCase("да")) {
            if (manager.deleteById(id)) {
                System.out.println(
                        "Доставка успешно удалена.");
            } else {
                System.out.println(
                        "Не удалось удалить доставку.");
            }
        } else {
            System.out.println("Удаление отменено.");
        }
    }

    // Ввод целого числа
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Введите целое число.");
            }
        }
    }

    // Ввод положительного числа
    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine()
                    .trim().replace(',', '.');

            try {
                double value = Double.parseDouble(input);

                if (Double.isFinite(value) && value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Запросим значение повторно.
            }

            System.out.println(
                    "Введите положительное число, например 5 или 2.5.");
        }
    }

    // Форматирование стоимости
    private static String formatMoney(double amount) {
        return String.format(
                Locale.forLanguageTag("ru-RU"),
                "%.2f", amount);
    }
}
