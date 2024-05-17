package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.PowerOrbitPatches;
import ArmorKnight.powers.interfaces.AuraTriggerPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IceChargePower extends AbstractPower implements PowerOrbitPatches.OrbitPower, AuraTriggerPower {
    public static final String POWER_ID = MainModfile.makeID(IceChargePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int EFFECT = 3;

    public IceChargePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("int");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
    }

    @Override
    public int orbAmount() {
        return amount;
    }

    @Override
    public Color orbColor() {
        return Color.CYAN;
    }

    @Override
    public void onActivateAura() {
        flash();
        for (int i = 0 ; i < amount ; i++) {
            addToBot(new GainBlockAction(owner, owner, EFFECT, true));
        }
    }
}