package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.PowerOrbitPatches;
import ArmorKnight.powers.interfaces.AuraTriggerPower;
import ArmorKnight.relics.HolyPlatemail;
import ArmorKnight.relics.Platemail;
import ArmorKnight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LightChargePower extends AbstractPower implements PowerOrbitPatches.OrbitPower, AuraTriggerPower {
    public static final String POWER_ID = MainModfile.makeID(LightChargePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final int relicAmount;

    public LightChargePower(AbstractCreature owner, int amount) {
        this(owner, amount, 0);
    }

    public LightChargePower(AbstractCreature owner, int amount, int relicAmount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.relicAmount = relicAmount;
        this.type = PowerType.BUFF;
        this.loadRegion("curiosity");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public int orbAmount() {
        return amount;
    }

    @Override
    public Color orbColor() {
        return Color.YELLOW;
    }

    @Override
    public void onActivateAura() {
        flash();
        addToBot(new GainEnergyAction(amount));
    }
}