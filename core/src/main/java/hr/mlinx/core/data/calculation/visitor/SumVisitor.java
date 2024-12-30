package hr.mlinx.core.data.calculation.visitor;

import hr.mlinx.core.data.Player;

import java.util.function.Function;

public class SumVisitor implements PlayerVisitor {
    private double sum = 0;
    private final Function<Player, Double> fieldExtractor;

    public SumVisitor(Function<Player, Double> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public void visit(Player player) {
        sum += fieldExtractor.apply(player);
    }

    public double getSum() {
        return sum;
    }
}
