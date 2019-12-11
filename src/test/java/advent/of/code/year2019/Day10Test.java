package advent.of.code.year2019;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    @Test
    void testBestMonitoringStation() {
        char[][] grid = Day10.loadGrid("/day10_test_1.txt", this.getClass());
        assertEquals(33, Day10.bestMonitoringStation(grid).getAsteroidsInSightCount());
    }

    @Test
    void testFind200thKill() {
        char[][] grid = Day10.loadGrid("/day10_test_2.txt", this.getClass());
        Day10.MonitoringStation monitoringStation = Day10.bestMonitoringStation(grid);
        assertEquals(802, Day10.find200thKill(monitoringStation, grid));
    }
}
