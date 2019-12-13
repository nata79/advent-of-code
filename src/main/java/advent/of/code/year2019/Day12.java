package advent.of.code.year2019;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String[] args) {
        List<Moon> moons = List.of(
                new Moon(new int[]{14, 15, -2}),
                new Moon(new int[]{17, -3, 4}),
                new Moon(new int[]{6, 12, -13}),
                new Moon(new int[]{-2, 10, -8})
        );

        System.out.println(energyAfterSteps(moons.stream().map(Moon::clone).collect(Collectors.toList()), 1000));
        System.out.println(stepsToRepeatUniverse(moons));
    }

    static long stepsToRepeatUniverse(List<Moon> moons) {
        int[][] initialPositions = moons.stream()
                .map(Moon::getPosition)
                .map(position -> Arrays.copyOf(position, position.length))
                .toArray(int[][]::new);

        int[][] initialVelocities = moons.stream()
                .map(Moon::getVelocity)
                .map(position -> Arrays.copyOf(position, position.length))
                .toArray(int[][]::new);

        long[] freqs = new long[]{0, 0, 0};

        long steps = 1;
        while (Arrays.stream(freqs).anyMatch(freq -> freq == 0)) {
            step(moons);

            for (int i = 0; i < freqs.length; i++) {
                if (freqs[i] == 0) {
                    boolean found = true;
                    for (int j = 0; j < moons.size(); j++) {
                        Moon moon = moons.get(j);
                        if (moon.getPosition()[i] != initialPositions[j][i] || moon.getVelocity()[i] != initialVelocities[j][i]) {
                            found = false;
                            break;
                        }
                    }
                    if (found)
                        freqs[i] = steps;
                }
            }

            steps++;
        }


        return lcm(freqs[0], lcm(freqs[1], freqs[2]));
    }

    private static long lcm(long a, long b) {
        long lcm = Math.max(a, b);

        while (lcm % Math.min(a, b) != 0)
            lcm += Math.max(a, b);

        return lcm;
    }

    static int energyAfterSteps(List<Moon> moons, int steps) {
        for (int i = 0; i < steps; i++) {
            step(moons);
        }

        return energy(moons);
    }

    private static int energy(List<Moon> moons) {
        return moons.stream().mapToInt(Moon::getEnergy).sum();
    }

    static void step(List<Moon> moons) {
        for (int i = 0; i < moons.size(); i++) {
            Moon moon = moons.get(i);

            for (int j = i + 1; j < moons.size(); j++) {
                if (i != j) {
                    Moon moon2 = moons.get(j);
                    moon.addGravity(moon2);
                }
            }
        }

        for (Moon moon : moons) {
            moon.incrementVelocity();
        }
    }

    static class Moon {
        private final int[] position;
        private final int[] velocity;

        Moon(int[] position) {
            this.position = position;
            this.velocity = new int[]{0, 0, 0};
        }

        Moon(int[] position, int[] velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        int[] getPosition() {
            return position;
        }

        int[] getVelocity() {
            return velocity;
        }

        int getEnergy() {
            return Arrays.stream(position).map(Math::abs).sum() * Arrays.stream(velocity).map(Math::abs).sum();
        }

        void addGravity(Moon moon) {
            for (int i = 0; i < position.length; i++) {
                int delta = Integer.compare(position[i], moon.getPosition()[i]);
                velocity[i] += -delta;
                moon.getVelocity()[i] += delta;
            }
        }

        void incrementVelocity() {
            for (int i = 0; i < position.length; i++)
                position[i] += velocity[i];
        }

        @Override
        public Moon clone() {
            return new Moon(Arrays.copyOf(position, position.length), Arrays.copyOf(velocity, velocity.length));
        }
    }
}
