package ArmorKnight.cards;

import ArmorKnight.actions.ThrowCardsAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.powers.interfaces.ThrowBoostPower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Distraction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static ArmorKnight.MainModfile.makeID;

public class SkillIssue extends AbstractEasyCard {
    public final static String ID = makeID(SkillIssue.class.getSimpleName());

    public SkillIssue() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseInfo = info = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ThrowCardsAction(p.hand.size(), m, c -> c.type == CardType.SKILL));
        addToBot(new GainEnergyAction(magicNumber));
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
            if (c != this && c.type == CardType.SKILL) {
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
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return Distraction.ID;
    }
}