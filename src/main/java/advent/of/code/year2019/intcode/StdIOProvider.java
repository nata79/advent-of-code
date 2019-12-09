package advent.of.code.year2019.intcode;

import java.math.BigInteger;
import java.util.Scanner;

class StdIOProvider implements IOProvider {
    @Override
    public BigInteger nextInt() {
        Scanner scanner = new Scanner(System.in);
        return BigInteger.valueOf(scanner.nextInt());
    }

    @Override
    public void putInt(BigInteger value) {
        System.out.println(value);
    }
}
