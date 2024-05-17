package ArmorKnight.actions;

import ArmorKnight.util.Wiz;

public class HolyAction extends DoIfAction {
    public HolyAction(Runnable runnable) {
        super(() -> Wiz.auraActive(), runnable);
    }
}
