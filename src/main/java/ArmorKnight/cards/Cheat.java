package ArmorKnight.cards;

import ArmorKnight.actions.ModifyCardsInHandAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Cheat extends AbstractEasyCard {
    public final static String ID = makeID(Cheat.class.getSimpleName());

    public Cheat() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ModifyCardsInHandAction(1, false, c -> c.cost >= 1, cards -> {
            for (AbstractCard c : cards) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
                c.superFlash(Color.GOLD.cpy());
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return CalculatedGamble.ID;
    }
}