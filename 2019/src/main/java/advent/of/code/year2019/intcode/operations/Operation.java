package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeParameter;

public interface Operation {
    int getOpCode();

    int getNumberOfParameters();

    void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters);

    default void moveStackPointer(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        intCodeProcess.setStackPointer(intCodeProcess.getStackPointer() + 1 + getNumberOfParameters());
    }
}
