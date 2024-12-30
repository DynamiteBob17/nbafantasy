package hr.mlinx.core.data.calculation.visitor;

import hr.mlinx.core.data.Player;

public interface PlayerVisitor {
    void visit(Player player);
}
