import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class Day7 {
    public static void main(String[] args) {
        String input = Utils.readFile("day7.txt");
        Integer[] program = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);

        System.out.println(findMaxSignal(program));
        System.out.println(findMaxSignal(program, new int[]{5, 6, 7, 8, 9}));
    }

    static int findMaxSignal(Integer[] program) {
        int[] sequence = new int[5];
        Arrays.setAll(sequence, index -> index);

        return findMaxSignal(program, sequence);
    }

    static int findMaxSignal(Integer[] program, int[] sequence) {
        IntContainer max = new IntContainer(0);

        generateAllPermutations(sequence, (permutation) ->
                max.setValue(Math.max(max.getValue(), runPipeline(permutation, program)))
        );

        return max.getValue();
    }

    private static class IntContainer {
        private int value;

        IntContainer(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }
    }

    private static void generateAllPermutations(int[] sequence, Consumer<int[]> generate) {
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

    private static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    static int runPipeline(int[] sequence, Integer[] program) {
        QueueIOProvider[] ioProviders = new QueueIOProvider[sequence.length];

        for (int i = 0; i < sequence.length; i++) {
            BlockingQueue<Integer> inputQueue;

            if (i == 0)
                inputQueue = new LinkedBlockingQueue<>();
            else
                inputQueue = ioProviders[i - 1].getOutputQueue();

            inputQueue.add(sequence[i]);

            BlockingQueue<Integer> outputQueue;

            if (i == sequence.length - 1)
                outputQueue = ioProviders[0].getInputQueue();
            else
                outputQueue = new LinkedBlockingQueue<>();

            ioProviders[i] = new QueueIOProvider(inputQueue, outputQueue);
        }

        ioProviders[0].getInputQueue().add(0);

        CompletableFuture[] futures = new CompletableFuture[ioProviders.length];

        for (int i = 0; i < ioProviders.length; i++) {
            int finalI = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                IntCode intCode = new IntCode(ioProviders[finalI]);
                intCode.processIntcode(Arrays.copyOf(program, program.length));
                return 0;
            });

            futures[i] = future;
        }

        for (CompletableFuture future : futures)
            future.join();

        return ioProviders[0].nextInt();
    }

    static class QueueIOProvider implements IntCode.IOProvider {
        private BlockingQueue<Integer> inputQueue;
        private BlockingQueue<Integer> outputQueue;

        QueueIOProvider(BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue) {
            this.inputQueue = inputQueue;
            this.outputQueue = outputQueue;
        }

        BlockingQueue<Integer> getInputQueue() {
            return inputQueue;
        }

        BlockingQueue<Integer> getOutputQueue() {
            return outputQueue;
        }

        @Override
        public int nextInt() {
            try {
                return inputQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public void putInt(int value) {
            outputQueue.add(value);
        }
    }
}
