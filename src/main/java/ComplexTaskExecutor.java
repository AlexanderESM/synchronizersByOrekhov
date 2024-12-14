import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Класс ComplexTaskExecutor отвечает за управление выполнением сложных задач в многопоточном режиме.
class ComplexTaskExecutor {
    private final int numberOfTasks; // Количество задач, которые будут выполняться.
    private final ExecutorService executorService; // Пул потоков для выполнения задач.

    // Конструктор инициализирует количество задач и создает пул потоков с фиксированным размером.
    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        this.executorService = Executors.newFixedThreadPool(numberOfTasks); // Фиксированный пул потоков.
    }

    // Метод executeTasks управляет выполнением задач и их синхронизацией.
    public void executeTasks(int numberOfTasks) {
        // Создаем новый пул потоков для выполнения задач.
        // Этот пул может отличаться от глобального executorService, что потенциально создаёт путаницу.
        ExecutorService executorService = Executors.newFixedThreadPool(this.numberOfTasks);

        // Создаем CyclicBarrier с числом потоков, равным количеству задач.
        // После завершения всех задач выполняется действие, указанное во втором параметре.
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
           // System.out.println("Этот блок выполняется, когда все потоки достигают барьера.");
        });

        // Создаем и запускаем задачи.
        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(new ComplexTask(i + 1, barrier)); // Назначаем задачу на выполнение.
        }

        // Закрываем пул потоков, больше не принимаются новые задачи.
        executorService.shutdown();
        try {
            // Ожидаем завершения всех задач в течение заданного времени (10 секунд).
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                // Если задачи не завершены за отведенное время, выводим сообщение и принудительно завершаем пул.
                // System.out.println("сли задачи не завершены за отведенное время, выводим сообщение и принудительно завершаем пул.");
                executorService.shutdownNow(); // Прерываем выполнение оставшихся задач.
            }
        } catch (InterruptedException e) {
            // Обрабатываем прерывание текущего потока, при этом завершаем пул потоков принудительно.
            executorService.shutdownNow();
            Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания.
        }
    }
}
