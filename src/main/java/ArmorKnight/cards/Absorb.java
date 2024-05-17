package ArmorKnight.cards;

import ArmorKnight.actions.BetterSelectCardsInHandAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.patches.WeightPatches;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Aggregate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Absorb extends AbstractEasyCard {
    public final static String ID = makeID(Absorb.class.getSimpleName());

    public Absorb() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterSelectCardsInHandAction(1, RecycleAction.TEXT[0], false, false, c -> true, l -> {
            for (AbstractCard c : l) {
                addToTop(new GainEnergyAction(WeightPatches.WeightField.weight.get(c)));
                addToTop(new ExhaustSpecificCardAction(c, p.hand));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return Aggregate.ID;
    }
}