package advent.of.code.year2019.intcode;

import java.math.BigInteger;

public interface IOProvider {
    BigInteger nextInt();

    void putInt(BigInteger value);
}
