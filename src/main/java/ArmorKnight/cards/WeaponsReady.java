package ArmorKnight.cards;

import ArmorKnight.actions.BetterSelectCardsInHandAction;
import ArmorKnight.actions.EasyXCostAction;
import ArmorKnight.cardmods.WeightMod;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Consume;
import com.megacrit.cardcrawl.cards.red.DualWield;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;

import static ArmorKnight.MainModfile.makeID;

public class WeaponsReady extends AbstractEasyCard {
    public final static String ID = makeID(WeaponsReady.class.getSimpleName());

    public WeaponsReady() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (base, vals) -> {
            int effect = base + Arrays.stream(vals).sum();
            addToTop(new BetterSelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], false, false, c -> c.type == CardType.ATTACK && c.cost <= effect && c.cost >= 0, cards -> {
                for (AbstractCard c : cards) {
                    c.exhaust = true;
                    addToBot(new NewQueueCardAction(c, m, false, true));
                    AbstractCard tmp = c.makeStatEquivalentCopy();
                    tmp.purgeOnUse = true;
                    addToBot(new NewQueueCardAction(tmp, m, false, true));
                }
            }));
            return true;
        }));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return DualWield.ID;
    }
}