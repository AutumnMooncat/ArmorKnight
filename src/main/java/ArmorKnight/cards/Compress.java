package ArmorKnight.cards;

import ArmorKnight.actions.EasyXCostAction;
import ArmorKnight.cardmods.WeightMod;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Consume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Compress extends AbstractEasyCard {
    public final static String ID = makeID(Compress.class.getSimpleName());

    public Compress() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (base, vals) -> {
            int effect = base;
            for (int i : vals) {
                effect += i;
            }
            if (effect > 0) {
                for (AbstractCard c : Wiz.getAllCardsInCardGroups(true, true)) {
                    CardModifierManager.addModifier(c, new WeightMod(effect));
                }
            }
            return true;
        }, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Consume.ID;
    }
}