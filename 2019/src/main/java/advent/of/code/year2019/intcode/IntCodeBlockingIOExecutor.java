package advent.of.code.year2019.intcode;

import java.util.LinkedList;
import java.util.Queue;

public class IntCodeBlockingIOExecutor {
    private final IntCodeProcess intCodeProcess;
    private boolean finished;
    private BlockingIOProvider ioProvider;

    IntCodeBlockingIOExecutor(Long[] code) {
        finished = false;
        ioProvider = new BlockingIOProvider(new LinkedList<>(), new LinkedList<>());
        intCodeProcess = new IntCodeProcess(code, ioProvider);
    }

    public boolean isRunning() {
        return !finished;
    }

    public Queue<Long> runUntilInputNeeded() {
        finished = intCodeProcess.runUntilInputNeeded(() -> ioProvider.getInputQueue().isEmpty());
        return ioProvider.getOutputQueue();
    }

    public void provideInput(Long... inputs) {
        for (Long input : inputs)
            ioProvider.getInputQueue().add(input);
    }

    private static class BlockingIOProvider implements IOProvider {
        private final Queue<Long> inputQueue;
        private final Queue<Long> outputQueue;

        BlockingIOProvider(Queue<Long> inputQueue, Queue<Long> outputQueue) {
            this.inputQueue = inputQueue;
            this.outputQueue = outputQueue;
        }

        Queue<Long> getInputQueue() {
            return inputQueue;
        }

        Queue<Long> getOutputQueue() {
            return outputQueue;
        }

        @Override
        public Long nextInt() {
            return inputQueue.remove();
        }

        @Override
        public void putInt(Long value) {
            outputQueue.add(value);
        }
    }
}
