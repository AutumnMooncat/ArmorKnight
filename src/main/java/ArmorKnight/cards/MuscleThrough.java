package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractPlateableCard;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static ArmorKnight.MainModfile.makeID;

public class MuscleThrough extends AbstractPlateableCard {
    public final static String ID = makeID(MuscleThrough.class.getSimpleName());

    public MuscleThrough() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 3;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    protected void applyPowersToBlock() {
        int base = baseBlock;
        if (Wiz.adp().powers.stream().anyMatch(p -> p.type == AbstractPower.PowerType.DEBUFF)) {
            baseBlock += magicNumber;
        }
        super.applyPowersToBlock();
        baseBlock = base;
        isBlockModified = baseBlock != block;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.adp().powers.stream().anyMatch(p -> p.type == AbstractPower.PowerType.DEBUFF)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeBlock(1);
        upgradeMagicNumber(3);
    }

    @Override
    public String cardArtCopy() {
        return Flex.ID;
    }
}