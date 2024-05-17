package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class EchoingHymnPower extends TwoAmountPower implements NonStackablePower {
    public static final String POWER_ID = MainModfile.makeID(EchoingHymnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EchoingHymnPower(AbstractCreature owner, int blockAmount, int turns) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = blockAmount;
        this.amount2 = turns;
        this.type = PowerType.BUFF;
        this.loadRegion("hymn");
        this.isTurnBased = true;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount2 == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3];
        }

    }

    public void atStartOfTurn() {
        flash();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(owner.hb.cX, owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        addToBot(new GainBlockAction(owner, owner, amount));
        amount2--;
        if (amount2 == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            updateDescription();
        }
    }
}