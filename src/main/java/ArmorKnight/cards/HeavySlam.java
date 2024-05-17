package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.tokens.Plating;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Headbutt;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class HeavySlam extends AbstractEasyCard {
    public final static String ID = makeID(HeavySlam.class.getSimpleName());

    public HeavySlam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        baseDamage = countWeight();
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        baseDamage = countWeight();
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    private int countWeight() {
        int weight = magicNumber;
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c != this) {
                weight += WeightPatches.WeightField.weight.get(c);
            }
        }
        return weight;
    }

    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upp() {
        //upgradeBaseCost(0);
        upgradeMagicNumber(3);
    }

    @Override
    public String cardArtCopy() {
        return HeavyBlade.ID;
    }
}