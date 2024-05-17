package ArmorKnight.cards;

import ArmorKnight.actions.DoAction;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.red.SwordBoomerang;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static ArmorKnight.MainModfile.makeID;

public class HeadHunter extends AbstractEasyCard {
    public final static String ID = makeID(HeadHunter.class.getSimpleName());
    private static int oldDead;

    public HeadHunter() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 9;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DoAction(() -> {
            oldDead = 0;
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                if (mon.isDeadOrEscaped()) {
                    oldDead++;
                }
            }
        }));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        addToBot(new DoAction(() -> {
            int currentDead = 0;
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                if (mon.isDeadOrEscaped()) {
                    currentDead++;
                }
            }
            currentDead -= oldDead;
            if (currentDead > 0) {
                addToTop(new DrawCardAction(currentDead));
                addToTop(new GainEnergyAction(currentDead));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return SwordBoomerang.ID;
    }
}