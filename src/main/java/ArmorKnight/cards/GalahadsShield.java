package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.interfaces.OnOtherCardUpgradedCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class GalahadsShield extends AbstractEasyCard implements OnOtherCardUpgradedCard {
    public final static String ID = makeID(GalahadsShield.class.getSimpleName());

    public GalahadsShield() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 14;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(4);
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return AutoShields.ID;
    }

    @Override
    public void onOtherCardUpgraded(AbstractCard card) {
        if (card.hasTag(CardTags.STARTER_DEFEND) && canUpgrade()) {
            superFlash();
            upgrade();
        }
    }
}