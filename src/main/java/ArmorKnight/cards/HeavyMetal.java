package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.tokens.Plating;
import ArmorKnight.powers.HeavyMetalPower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.BattleHymn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class HeavyMetal extends AbstractEasyCard {
    public final static String ID = makeID(HeavyMetal.class.getSimpleName());

    public HeavyMetal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new Plating();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new HeavyMetalPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(1);
        isInnate = true;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return BattleHymn.ID;
    }
}