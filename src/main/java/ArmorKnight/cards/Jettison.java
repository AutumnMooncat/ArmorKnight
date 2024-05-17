package ArmorKnight.cards;

import ArmorKnight.actions.ThrowCardsAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.tokens.Plating;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.powers.interfaces.ThrowBoostPower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.cards.red.Armaments;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static ArmorKnight.MainModfile.makeID;

public class Jettison extends AbstractEasyCard {
    public final static String ID = makeID(Jettison.class.getSimpleName());

    public Jettison() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        exhaust = true;
        baseInfo = info = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ThrowCardsAction(p.hand.size(), m, false, cards -> addToTop(new DrawCardAction(cards.size()))));
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
        for (AbstractCard c : Wiz.adp().hand.group) {
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
        return BladeDance.ID;
    }
}