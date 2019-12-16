package advent.of.code.year2019;

import advent.of.code.Utils;

import java.util.*;

public class Day14 {
    public static void main(String[] args) {
        Map<String, Reaction> chemicalRequirements = loadReactions("/2019/day14.txt", Day14.class);
        System.out.println(new Refinery(chemicalRequirements).fuelPrice());
        System.out.println(new Refinery(chemicalRequirements).produceFuel(1000000000000L));
    }

    static Map<String, Reaction> loadReactions(String filepath, Class klass) {
        Map<String, Reaction> chemicalRequirements = new HashMap<>();
        String input = Utils.readFile(filepath);

        for (String line : input.split("\n")) {
            String[] reactionSpec = line.split("=>");

            ChemicalQuantity chemicalQuantity = splitQuantityChemical(reactionSpec[1]);
            Reaction reaction = new Reaction(chemicalQuantity);

            for (String requirement : reactionSpec[0].split(",")) {
                reaction.addIngredient(splitQuantityChemical(requirement));
            }

            chemicalRequirements.put(chemicalQuantity.getChemical(), reaction);
        }

        return chemicalRequirements;
    }

    private static ChemicalQuantity splitQuantityChemical(String s) {
        String[] split = s.strip().split(" ");
        return new ChemicalQuantity(Integer.parseInt(split[0]), split[1].strip());
    }

    static class Refinery {
        private final Map<String, Reaction> reactions;
        private final Map<String, Long> bank;

        Refinery(Map<String, Reaction> reactions) {
            this.reactions = reactions;
            bank = new HashMap<>();
        }

        long fuelPrice() {
            return buildFuel();
        }

        long produceFuel(long ores) {
            bank.put("ORE", ores);
            long fuel = 0;

            while (buildFuel() == 0)
                fuel++;

            return fuel;
        }

        long buildFuel() {
            Stack<ChemicalQuantity> stack = new Stack<>();
            stack.push(new ChemicalQuantity(1, "FUEL"));

            long missingOre = 0;

            while (!stack.empty()) {
                ChemicalQuantity chemical = stack.pop();
                long quantityToProduce = chemical.getQuantity();

                if (bank.containsKey(chemical.getChemical())) {
                    long bankQuantity = bank.getOrDefault(chemical.getChemical(), 0L);
                    if (bankQuantity < chemical.getQuantity()) {
                        bank.put(chemical.getChemical(), 0L);
                        quantityToProduce -= bankQuantity;
                    } else {
                        bank.put(chemical.getChemical(), bankQuantity - quantityToProduce);
                        quantityToProduce = 0;
                    }
                }

                if (quantityToProduce > 0) {
                    Reaction reaction = reactions.get(chemical.getChemical());

                    if (reaction == null)
                        missingOre += quantityToProduce;

                    else {
                        long reactionProductionQuantity = reaction.getResult().getQuantity();
                        long quantityProduced = 0;
                        int executions = Double.valueOf(Math.ceil(quantityToProduce / (double) reactionProductionQuantity)).intValue();

                        for (int i = 0; i < executions; i++) {
                            for (ChemicalQuantity ingredient : reaction.getIngredients()) {
                                Optional<ChemicalQuantity> optionalExistingIngredient = stack.stream()
                                        .filter(c -> c.getChemical().equals(ingredient.getChemical()))
                                        .findFirst();
                                if (optionalExistingIngredient.isPresent()) {
                                    optionalExistingIngredient.get().incrementQuantity(ingredient.getQuantity());
                                } else {
                                    stack.push(ingredient.clone());
                                }
                            }

                            quantityProduced += reactionProductionQuantity;
                        }

                        long excess = quantityProduced - quantityToProduce;
                        bank.put(chemical.getChemical(), bank.getOrDefault(chemical.getChemical(), 0L) + excess);
                    }
                }
            }

            return missingOre;
        }
    }

    static class Reaction {
        private final List<ChemicalQuantity> ingredients;
        private final ChemicalQuantity result;

        Reaction(ChemicalQuantity result) {
            this.result = result;
            ingredients = new ArrayList<>();
        }

        Reaction(List<ChemicalQuantity> ingredients, ChemicalQuantity result) {
            this.ingredients = ingredients;
            this.result = result;
        }

        void addIngredient(ChemicalQuantity chemicalQuantity) {
            ingredients.add(chemicalQuantity);
        }

        List<ChemicalQuantity> getIngredients() {
            return ingredients;
        }

        ChemicalQuantity getResult() {
            return result;
        }

        @Override
        protected Reaction clone() {
            List<ChemicalQuantity> newIngredients = new ArrayList<>();
            ingredients.forEach(chemicalQuantity -> newIngredients.add(chemicalQuantity.clone()));
            return new Reaction(newIngredients, result.clone());
        }
    }

    static class ChemicalQuantity {
        private long quantity;
        private final String chemical;

        ChemicalQuantity(long quantity, String chemical) {
            this.quantity = quantity;
            this.chemical = chemical;
        }

        long getQuantity() {
            return quantity;
        }

        String getChemical() {
            return chemical;
        }

        void incrementQuantity(long increment) {
            quantity += increment;
        }

        @Override
        protected ChemicalQuantity clone() {
            return new ChemicalQuantity(quantity, chemical);
        }
    }
}