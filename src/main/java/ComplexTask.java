import java.util.concurrent.*;

/**
// Класс ComplexTask реализует интерфейс Runnable и представляет собой сложную задачу,
// которая будет выполняться в отдельном потоке.
// Задачи синхронизируются с помощью CyclicBarrier.
*/
class ComplexTask implements Runnable {
    private final int taskId;         // Уникальный идентификатор задачи.
    private final CyclicBarrier barrier; // Барьер, используемый для синхронизации задач.

    /**
    // Конструктор принимает ID задачи и объект CyclicBarrier,
    // который будет использоваться для ожидания других потоков.
    */
    public ComplexTask(int taskId, CyclicBarrier barrier) {
        this.taskId = taskId;         // Присваиваем ID задачи.
        this.barrier = barrier;       // Присваиваем общий барьер для синхронизации.
    }

    // Метод run() содержит основную логику задачи и вызывается при запуске потока.
    @Override
    public void run() {
        try {
            // Выполнение основной задачи (симуляция сложной работы).
            // В реальном приложении здесь может быть любая логика.
            // System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());

            Thread.sleep((long) (Math.random() * 3000)); // Симулируем работу задачи с задержкой.

            // После завершения работы задачи поток сообщает о завершении и синхронизируется с другими потоками.
            System.out.println("Задача " + taskId + " завершена " + Thread.currentThread().getName());

            // Вызываем barrier.await(), чтобы дождаться выполнения всех потоков,
            // участвующих в этом барьере. После этого выполняется общее действие (если задано).
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            // Обрабатываем исключения:
            // InterruptedException - если поток был прерван.
            // BrokenBarrierException - если барьер был сломан (например, если один из потоков не дошел до барьера).
            Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания.
            System.out.println("Задача " + taskId + " прервана.");
        }
    }
}
