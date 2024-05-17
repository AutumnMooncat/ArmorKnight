package ArmorKnight.actions;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.powers.interfaces.ThrowBoostPower;
import ArmorKnight.util.TextureSniper;
import ArmorKnight.util.Wiz;
import ArmorKnight.vfx.VFXContainer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FiftyTwoPickupAction extends AbstractGameAction {

    public FiftyTwoPickupAction(AbstractCreature target) {
        this.target = target;
        this.source = Wiz.adp();
    }

    @Override
    public void update() {
        for (AbstractCard c : Wiz.adp().drawPile.group) {
            int damage = WeightPatches.WeightField.weight.get(c);
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof ThrowBoostPower) {
                    damage += ((ThrowBoostPower) p).bonusDamage(c);
                }
            }
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.BLUNT_LIGHT, true));
        }
        for (AbstractCard c : Wiz.adp().drawPile.group) {
            AbstractDungeon.actionManager.addToTop(new VFXAction(VFXContainer.throwEffect(TextureSniper.snipeCard(c), 0.25f, target.hb, MainModfile.ARMORED_MAUVE_COLOR, true, true)));
        }
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                int size = Wiz.adp().drawPile.size();
                for (int i = 0 ; i < size ; i++) {
                    Wiz.adp().drawPile.moveToDiscardPile(Wiz.adp().drawPile.getTopCard());
                }
                this.isDone = true;
            }
        });
        this.isDone = true;
    }
}
