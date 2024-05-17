package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.cardmods.FlatDamageMod;
import ArmorKnight.patches.EnterCardGroupPatches;
import ArmorKnight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GamblerPower extends AbstractPower implements EnterCardGroupPatches.OnEnterCardGroupPower {
    public static final String POWER_ID = MainModfile.makeID(GamblerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GamblerPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("mental_fortress");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onEnter(CardGroup g, AbstractCard c) {
        if (g == Wiz.adp().hand && c.type == AbstractCard.CardType.ATTACK) {
            flash();
            c.superFlash(Color.GOLD.cpy());
            int inc = AbstractDungeon.cardRandomRng.random(amount);
            if (inc != 0) {
                CardModifierManager.addModifier(c, new FlatDamageMod(inc));
            }
        }
    }
}