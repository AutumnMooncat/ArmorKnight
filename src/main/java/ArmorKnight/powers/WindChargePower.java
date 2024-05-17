package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.PowerOrbitPatches;
import ArmorKnight.powers.interfaces.AuraTriggerPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WindChargePower extends AbstractPower implements PowerOrbitPatches.OrbitPower, AuraTriggerPower {
    public static final String POWER_ID = MainModfile.makeID(WindChargePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int EFFECT = 1;

    public WindChargePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("fasting");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + EFFECT*amount + (EFFECT*amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public int orbAmount() {
        return amount;
    }

    @Override
    public Color orbColor() {
        return Color.CHARTREUSE;
    }

    @Override
    public void onActivateAura() {
        flash();
        addToBot(new DrawCardAction(EFFECT*amount));
    }
}