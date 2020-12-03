package advent.of.code.year2019.intcode;

public interface IOProvider {
    Long nextInt();

    void putInt(Long value);
}
