package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.GhostlyArmor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static ArmorKnight.MainModfile.makeID;

public class CursedShell extends AbstractEasyCard {
    public final static String ID = makeID(CursedShell.class.getSimpleName());

    public CursedShell() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 15;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new VulnerablePower(p, 1, false));
    }

    @Override
    public void upp() {
        upgradeBlock(5);
    }

    @Override
    public String cardArtCopy() {
        return GhostlyArmor.ID;
    }
}