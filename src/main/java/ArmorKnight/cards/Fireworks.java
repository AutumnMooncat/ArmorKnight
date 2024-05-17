package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.vfx.VFXContainer;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.blue.Chaos;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Fireworks extends AbstractEasyCard {
    public final static String ID = makeID(Fireworks.class.getSimpleName());

    public Fireworks() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnManualDiscard() {
        for (int i = 0 ; i < magicNumber ; i++) {
            addToTop(new VFXAction(VFXContainer.fireworkEffect(), 0.1f));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(20);
    }

    @Override
    public String cardArtCopy() {
        return Chaos.ID;
    }
}