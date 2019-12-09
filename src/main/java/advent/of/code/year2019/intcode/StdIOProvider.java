package advent.of.code.year2019.intcode;

import java.util.Scanner;

class StdIOProvider implements IOProvider {
    @Override
    public Long nextInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    @Override
    public void putInt(Long value) {
        System.out.println(value);
    }
}
