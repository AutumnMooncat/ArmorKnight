package ArmorKnight.cards;

import ArmorKnight.actions.DoIfAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.SneakyStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static ArmorKnight.MainModfile.makeID;

public class Jab extends AbstractEasyCard {
    public final static String ID = makeID(Jab.class.getSimpleName());

    public Jab() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new DoIfAction(
                () -> m.powers.stream().anyMatch(pow -> pow.type == AbstractPower.PowerType.DEBUFF),
                () -> dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY)
        ));

    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.powers.stream().anyMatch(pow -> pow.type == AbstractPower.PowerType.DEBUFF)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return SneakyStrike.ID;
    }
}