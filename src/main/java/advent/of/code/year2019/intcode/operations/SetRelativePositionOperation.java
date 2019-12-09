package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeParameter;

public class SetRelativePositionOperation implements Operation {
    @Override
    public int getOpCode() {
        return 9;
    }

    @Override
    public int getNumberOfParameters() {
        return 1;
    }

    @Override
    public void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        intCodeProcess.setRelativePosition(intCodeProcess.getRelativePosition() + parameters[0].getValue().intValue());
    }
}
