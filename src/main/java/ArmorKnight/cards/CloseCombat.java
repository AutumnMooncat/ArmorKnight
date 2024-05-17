package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.AllOutAttack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static ArmorKnight.MainModfile.makeID;

public class CloseCombat extends AbstractEasyCard {
    public final static String ID = makeID(CloseCombat.class.getSimpleName());

    public CloseCombat() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 12;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_HEAVY);
        Wiz.applyToSelf(new WeakPower(p, 1, false));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public String cardArtCopy() {
        return AllOutAttack.ID;
    }
}