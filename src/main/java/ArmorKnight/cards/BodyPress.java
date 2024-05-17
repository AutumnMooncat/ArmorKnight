package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.BodySlam;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;

import static ArmorKnight.MainModfile.makeID;

public class BodyPress extends AbstractEasyCard {
    public final static String ID = makeID(BodyPress.class.getSimpleName());

    public BodyPress() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 0;
        baseBlock = block = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        baseDamage = countBlock();
        calculateCardDamage(m);
        blck();
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        baseDamage = countBlock();
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    private int countBlock() {
        int amount = block;
        if (Wiz.adp().hasPower(NoBlockPower.POWER_ID)) {
            amount = 0;
        }
        amount += Wiz.adp().currentBlock;
        return amount;
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
        upgradeBlock(3);
    }

    @Override
    public String cardArtCopy() {
        return BodySlam.ID;
    }
}