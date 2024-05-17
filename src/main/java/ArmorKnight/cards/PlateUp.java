package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.tokens.Plating;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Armaments;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class PlateUp extends AbstractEasyCard {
    public final static String ID = makeID(PlateUp.class.getSimpleName());

    public PlateUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Plating();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.makeInHand(new Plating(), magicNumber);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Armaments.ID;
    }
}