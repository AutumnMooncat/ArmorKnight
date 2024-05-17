package ArmorKnight.cards;

import ArmorKnight.actions.DoAction;
import ArmorKnight.cardmods.WeightMod;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Skim;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class WeightedDraw extends AbstractEasyCard {
    public final static String ID = makeID(WeightedDraw.class.getSimpleName());

    public WeightedDraw() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber, new DoAction(() -> {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                CardModifierManager.addModifier(c, new WeightMod(magicNumber));
            }
        })));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Skim.ID;
    }
}