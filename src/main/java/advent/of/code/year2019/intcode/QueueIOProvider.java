package advent.of.code.year2019.intcode;

import java.util.concurrent.BlockingQueue;

public class QueueIOProvider implements IOProvider {
    private BlockingQueue<Long> inputQueue;
    private BlockingQueue<Long> outputQueue;

    public QueueIOProvider(BlockingQueue<Long> inputQueue, BlockingQueue<Long> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public BlockingQueue<Long> getInputQueue() {
        return inputQueue;
    }

    public BlockingQueue<Long> getOutputQueue() {
        return outputQueue;
    }

    @Override
    public Long nextInt() {
        try {
            return inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public void putInt(Long value) {
        outputQueue.add(value);
    }
}
