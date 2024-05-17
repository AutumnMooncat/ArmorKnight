package ArmorKnight.cards;

import ArmorKnight.actions.FiftyTwoPickupAction;
import ArmorKnight.actions.ThrowCardsAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.powers.interfaces.ThrowBoostPower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.cards.green.Expertise;
import com.megacrit.cardcrawl.cards.purple.Omniscience;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static ArmorKnight.MainModfile.makeID;

public class FiftyTwoPickup extends AbstractEasyCard {
    public final static String ID = makeID(FiftyTwoPickup.class.getSimpleName());

    public FiftyTwoPickup() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FiftyTwoPickupAction(m));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        baseInfo = info = countWeight();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        baseInfo = info = countWeight();
    }

    private int countWeight() {
        int weight = 0;
        for (AbstractCard c : Wiz.adp().drawPile.group) {
            if (c != this) {
                weight += WeightPatches.WeightField.weight.get(c);
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof ThrowBoostPower) {
                        weight += ((ThrowBoostPower) p).bonusDamage(c);
                    }
                }
            }
        }
        return weight;
    }

    @Override
    public void upp() {
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Omniscience.ID;
    }
}