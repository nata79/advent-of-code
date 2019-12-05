import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {
    public static void main(String[] args) {
        int start = 193651;
        int finish = 649729;

        int potentialPasswords = 0;

        for (int i = start; i < finish; i++) {
            if (validPassword(i))
                potentialPasswords++;
        }

        System.out.println(potentialPasswords);
    }

    static boolean validPassword(int i) {
        List<Integer> digits = Utils.getDigits(i);
        Map<Integer, Integer> repetitions = new HashMap<>();

        for (int j = 0; j < digits.size(); j++) {
            if (j > 0 && digits.get(j) < digits.get(j - 1))
                return false;

            if (j > 0 && digits.get(j).equals(digits.get(j - 1))) {
                int currentNumberRepeats = repetitions.getOrDefault(digits.get(j), 1);
                repetitions.put(digits.get(j), currentNumberRepeats + 1);
            }
        }

        return !repetitions.isEmpty() && repetitions.values().stream().anyMatch(integer -> integer == 2);
    }
}
