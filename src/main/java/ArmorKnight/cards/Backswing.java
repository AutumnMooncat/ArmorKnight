package ArmorKnight.cards;

import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.red.Clothesline;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArmorKnight.MainModfile.makeID;

public class Backswing extends AbstractEasyCard {
    public final static String ID = makeID(Backswing.class.getSimpleName());

    public Backswing() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new ArmamentsAction(upgraded));
    }

    @Override
    public void upp() {
        //upgradeDamage(2);
        //upgradeMagicNumber(1);
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Clothesline.ID;
    }
}