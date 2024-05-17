package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class PowerSurge extends AbstractEasyCard {
    public final static String ID = makeID(PowerSurge.class.getSimpleName());

    public PowerSurge() {
        this(0);
    }

    public PowerSurge(int timesUpgraded) {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        this.timesUpgraded = timesUpgraded;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ORB_PLASMA_EVOKE"));
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeName();
        upp();
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new PowerSurge(timesUpgraded);
    }

    @Override
    public String cardArtCopy() {
        return LimitBreak.ID;
    }
}