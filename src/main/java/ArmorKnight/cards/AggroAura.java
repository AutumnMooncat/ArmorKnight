package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.powers.AggroAuraPower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Berserk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class AggroAura extends AbstractEasyCard {
    public final static String ID = makeID(AggroAura.class.getSimpleName());

    public AggroAura() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AggroAuraPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(0);
        isInnate = true;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Berserk.ID;
    }
}