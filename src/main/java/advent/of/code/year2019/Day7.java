package advent.of.code.year2019;

import advent.of.code.Utils;
import advent.of.code.year2019.intcode.IOProvider;
import advent.of.code.year2019.intcode.IntCodeProgram;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class Day7 {
    public static void main(String[] args) {
        IntCodeProgram program = IntCodeProgram.load("/2019/day7.txt");

        System.out.println(findMaxSignal(program));
        System.out.println(findMaxSignal(program, Utils.buildLongArray(5, 6, 7, 8, 9)));
    }

    static Long findMaxSignal(IntCodeProgram program) {
        Long[] sequence = Utils.buildLongArray(0, 1, 2, 3, 4);
        return findMaxSignal(program, sequence);
    }

    static Long findMaxSignal(IntCodeProgram program, Long[] sequence) {
        IntContainer max = new IntContainer(0L);

        generateAllPermutations(sequence, (permutation) ->
                max.setValue(Math.max(max.getValue(), runPipeline(permutation, program)))
        );

        return max.getValue();
    }

    private static class IntContainer {
        private Long value;

        IntContainer(Long value) {
            this.value = value;
        }

        Long getValue() {
            return value;
        }

        void setValue(Long value) {
            this.value = value;
        }
    }

    private static void generateAllPermutations(Long[] sequence, Consumer<Long[]> generate) {
        int[] index = new int[sequence.length];
        Arrays.fill(index, 0);

        generate.accept(sequence);

        int n = sequence.length;
        int i = 0;

        while (i < n) {
            if (index[i] < i) {
                if (i % 2 == 0)
                    swap(sequence, 0, i);
                else
                    swap(sequence, index[i], i);

                generate.accept(sequence);

                index[i]++;
                i = 0;
            } else {
                index[i] = 0;
                i++;
            }
        }
    }

    private static void swap(Long[] input, int a, int b) {
        Long tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    static Long runPipeline(Long[] sequence, IntCodeProgram program) {
        QueueIOProvider[] ioProviders = new QueueIOProvider[sequence.length];

        for (int i = 0; i < sequence.length; i++) {
            BlockingQueue<Long> inputQueue;

            if (i == 0)
                inputQueue = new LinkedBlockingQueue<>();
            else
                inputQueue = ioProviders[i - 1].getOutputQueue();

            inputQueue.add(sequence[i]);

            BlockingQueue<Long> outputQueue;

            if (i == sequence.length - 1)
                outputQueue = ioProviders[0].getInputQueue();
            else
                outputQueue = new LinkedBlockingQueue<>();

            ioProviders[i] = new QueueIOProvider(inputQueue, outputQueue);
        }

        ioProviders[0].getInputQueue().add(0L);

        CompletableFuture[] futures = new CompletableFuture[ioProviders.length];

        for (int i = 0; i < ioProviders.length; i++) {
            futures[i] = program.runAsync(ioProviders[i]);
        }

        for (CompletableFuture future : futures)
            future.join();

        return ioProviders[0].nextInt();
    }

    static class QueueIOProvider implements IOProvider {
        private BlockingQueue<Long> inputQueue;
        private BlockingQueue<Long> outputQueue;

        QueueIOProvider(BlockingQueue<Long> inputQueue, BlockingQueue<Long> outputQueue) {
            this.inputQueue = inputQueue;
            this.outputQueue = outputQueue;
        }

        BlockingQueue<Long> getInputQueue() {
            return inputQueue;
        }

        BlockingQueue<Long> getOutputQueue() {
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
}
