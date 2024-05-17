package ArmorKnight.cards;

import ArmorKnight.actions.ApplyPowerActionWithFollowup;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.green.SuckerPunch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static ArmorKnight.MainModfile.makeID;

public class DrainPunch extends AbstractEasyCard {
    public final static String ID = makeID(DrainPunch.class.getSimpleName());

    public DrainPunch() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 5;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new ApplyPowerActionWithFollowup(
                new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber)),
                new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber))
        ));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return SuckerPunch.ID;
    }
}