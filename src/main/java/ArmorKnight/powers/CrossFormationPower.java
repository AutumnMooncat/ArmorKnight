package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CrossFormationPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(CrossFormationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CrossFormationPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("unawakened");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        //int effect = (int) Wiz.adp().hand.group.stream().filter(c -> c instanceof Miracle).count();
        int effect = (int) Wiz.adp().powers.stream().filter(p -> p instanceof FireChargePower || p instanceof IceChargePower || p instanceof WindChargePower || p instanceof LightChargePower).count();
        if (effect > 0) {
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.owner, this.amount * effect));
        }
    }
}