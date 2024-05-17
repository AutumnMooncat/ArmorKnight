package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AggroAuraPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(AggroAuraPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AggroAuraPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("berserk");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount*50 + DESCRIPTIONS[1];
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (owner.powers.stream().anyMatch(p -> p.type == AbstractPower.PowerType.DEBUFF)) {
            damage *= (1 + 0.5f*amount);
        }
        return damage;
    }
}