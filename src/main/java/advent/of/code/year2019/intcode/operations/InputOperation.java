package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeParameter;

import java.math.BigInteger;

public class InputOperation implements Operation {
    @Override
    public int getOpCode() {
        return 3;
    }

    @Override
    public int getNumberOfParameters() {
        return 1;
    }

    @Override
    public void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        BigInteger[] program = intCodeProcess.getProgram();
        program[parameters[0].getPointer()] = intCodeProcess.getIoProvider().nextInt();
    }
}
