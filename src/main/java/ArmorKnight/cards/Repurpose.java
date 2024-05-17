package ArmorKnight.cards;

import ArmorKnight.actions.BetterSelectCardsInHandAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.tokens.Plating;
import ArmorKnight.patches.WeightPatches;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Recycle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Repurpose extends AbstractEasyCard {
    public final static String ID = makeID(Repurpose.class.getSimpleName());

    public Repurpose() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new Plating();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterSelectCardsInHandAction(1, RecycleAction.TEXT[0], false, false, c -> true, l -> {
            for (AbstractCard c : l) {
                addToTop(new MakeTempCardInHandAction(new Plating(), WeightPatches.WeightField.weight.get(c)));
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
        return Recycle.ID;
    }
}