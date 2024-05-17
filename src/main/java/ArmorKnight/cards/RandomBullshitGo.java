package ArmorKnight.cards;

import ArmorKnight.actions.GatherAndThrowAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.cards.green.Unload;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class RandomBullshitGo extends AbstractEasyCard {
    public final static String ID = makeID(RandomBullshitGo.class.getSimpleName());

    public RandomBullshitGo() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i++) {
            addToBot(new GatherAndThrowAction(m, c -> true));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Unload.ID;
    }
}