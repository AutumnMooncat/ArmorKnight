package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.powers.interfaces.OnPurityActivatePower;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PurityPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(PurityPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PurityPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("flight");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        checkStacks();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkStacks();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        checkStacks();
    }

    private void checkStacks(){
        while (this.amount >= 10) {
            for (AbstractPower pow : owner.powers) {
                if (pow instanceof OnPurityActivatePower) {
                    ((OnPurityActivatePower) pow).onPurity();
                }
            }
            //Wiz.att(new ApplyPowerAction(owner, owner, new DivineForcePower(owner, 1)));
            Wiz.att(new ApplyPowerAction(owner, owner, new GuidingLightPower(owner, 3)));
            //Wiz.att(new RemoveDebuffsAction(owner));
            this.amount -= 10;
            if (this.amount == 0) {
                Wiz.att(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }
}