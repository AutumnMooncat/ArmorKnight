package ArmorKnight.cards;

import ArmorKnight.actions.FormalComplaintAction;
import ArmorKnight.actions.ThrowCardsAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.cards.green.DaggerThrow;
import com.megacrit.cardcrawl.cards.red.Rage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static ArmorKnight.MainModfile.makeID;

public class FormalComplaint extends AbstractEasyCard {
    public final static String ID = makeID(FormalComplaint.class.getSimpleName());

    public FormalComplaint() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FormalComplaintAction(m));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return Rage.ID;
    }
}