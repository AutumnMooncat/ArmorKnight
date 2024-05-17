package ArmorKnight.cardmods;

import ArmorKnight.MainModfile;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MiracleTracker extends AbstractCardModifier {
    public static String ID = MainModfile.makeID(MiracleTracker.class.getSimpleName());
    @Override
    public AbstractCardModifier makeCopy() {
        return new MiracleTracker();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
