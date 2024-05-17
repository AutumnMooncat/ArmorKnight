package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.powers.interfaces.OnCreateCardPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HeartGuardPower extends AbstractPower implements OnCreateCardPower {
    public static final String POWER_ID = MainModfile.makeID(HeartGuardPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HeartGuardPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("forcefield");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


    @Override
    public void onCreateCard(AbstractCard card) {
        flash();
        addToBot(new GainBlockAction(owner, amount));
    }

    @Override
    public void onGenerateCardOption(AbstractCard card) {}
}