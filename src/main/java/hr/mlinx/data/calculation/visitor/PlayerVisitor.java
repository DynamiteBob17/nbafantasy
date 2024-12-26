package hr.mlinx.data.calculation.visitor;

import hr.mlinx.data.Player;

public interface PlayerVisitor {
    void visit(Player player);
}
