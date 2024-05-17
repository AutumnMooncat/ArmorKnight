package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.EnterCardGroupPatches;
import ArmorKnight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FallenFormPower extends AbstractPower implements EnterCardGroupPatches.OnEnterCardGroupPower {
    public static final String POWER_ID = MainModfile.makeID(FallenFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FallenFormPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("corruption");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onEnter(CardGroup g, AbstractCard c) {
        if (g == Wiz.adp().hand && c instanceof VoidCard) {
            flash();
            //addToBot(new BetterTransformCardInHandAction(c, Wiz.returnTrulyRandomPrediCardInCombat(card -> true)));
            //addToBot(new GainEnergyAction(amount));
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
        }
    }

    @SpirePatch2(clz = VoidCard.class, method = "triggerWhenDrawn")
    public static class NoEnergyLoss {
        @SpirePrefixPatch
        public static SpireReturn<?> plz() {
            if (Wiz.adp().hasPower(POWER_ID)) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}