package ArmorKnight.cards;

import ArmorKnight.actions.DoAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.tokens.Plating;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.purple.SpiritShield;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Manifest extends AbstractEasyCard {
    public final static String ID = makeID(Manifest.class.getSimpleName());

    public Manifest() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new Plating();
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoAction(() -> {
            int amount = BaseMod.MAX_HAND_SIZE - p.hand.size();
            if (amount > 0) {
                addToBot(new MakeTempCardInHandAction(new Plating(), amount));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public String cardArtCopy() {
        return SpiritShield.ID;
    }
}