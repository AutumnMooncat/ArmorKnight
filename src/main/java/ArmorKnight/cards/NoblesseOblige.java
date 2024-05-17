package ArmorKnight.cards;

import ArmorKnight.actions.ModifyCardsInHandAction;
import ArmorKnight.cardmods.WeightMod;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.BurningPact;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static ArmorKnight.MainModfile.makeID;

public class NoblesseOblige extends AbstractEasyCard {
    public final static String ID = makeID(NoblesseOblige.class.getSimpleName());

    public NoblesseOblige() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ModifyCardsInHandAction(p.hand.size(), cards -> {
            for (AbstractCard c : cards) {
                CardModifierManager.addModifier(c, new WeightMod(magicNumber));
            }
        }));
        Wiz.applyToSelf(new WeakPower(p, 1, false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return BurningPact.ID;
    }
}