package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.powers.ExNihiloPower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Metallicize;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class ExNihilo extends AbstractEasyCard {
    public final static String ID = makeID(ExNihilo.class.getSimpleName());

    public ExNihilo() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ExNihiloPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Metallicize.ID;
    }
}