package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.cards.interfaces.OnReceiveDebuffCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class CursedBlade extends AbstractEasyCard implements OnReceiveDebuffCard {
    public final static String ID = makeID(CursedBlade.class.getSimpleName());

    public CursedBlade() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return InfernalBlade.ID;
    }

    @Override
    public void onDebuffed() {
        superFlash();
        baseDamage += magicNumber;
    }
}