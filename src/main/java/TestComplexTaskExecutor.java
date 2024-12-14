public class TestComplexTaskExecutor {

    public static void main(String[] args) {
        // Создаем экземпляр ComplexTaskExecutor с количеством задач 5.
        // Этот объект управляет выполнением задач и их синхронизацией.
        ComplexTaskExecutor taskExecutor = new ComplexTaskExecutor(5); // Количество задач для выполнения

        // Определяем объект Runnable, который запускает выполнение задач в отдельном потоке.
        Runnable testRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + " Уведомляем о старте теста.");

            // Запускаем выполнение задач через taskExecutor.
            taskExecutor.executeTasks(5);
            System.out.println(Thread.currentThread().getName() + " Уведомляем о завершении выполнения задач в текущем потоке");
        };

        // Создаем два потока для выполнения тестов.
        Thread thread1 = new Thread(testRunnable, "TestThread-1"); // Первый поток с именем "TestThread-1".
        Thread thread2 = new Thread(testRunnable, "TestThread-2"); // Второй поток с именем "TestThread-2".

        // Запускаем оба потока.
        thread1.start(); // Стартуем "TestThread-1".
        thread2.start(); // Стартуем "TestThread-2".

        try {
            // Ожидаем завершения потоков, чтобы главный поток продолжил выполнение только после них.
            thread1.join(); // Ждем завершения "TestThread-1".
            thread2.join(); // Ждем завершения "TestThread-2".
        } catch (InterruptedException e) {
            // В случае прерывания устанавливаем флаг прерывания текущего потока.
            Thread.currentThread().interrupt();
        }
    }
}
