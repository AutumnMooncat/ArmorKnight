package ArmorKnight.actions;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.powers.interfaces.ThrowBoostPower;
import ArmorKnight.util.TextureSniper;
import ArmorKnight.util.Wiz;
import ArmorKnight.vfx.VFXContainer;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ThrowCardsAction extends BetterSelectCardsInHandAction {
    public ThrowCardsAction(int amount, AbstractMonster m) {
        this(amount, m, false, c -> {});
    }

    public ThrowCardsAction(int amount, AbstractMonster m, Predicate<AbstractCard> filter) {
        this(amount, m, false, filter, c -> {});
    }

    public ThrowCardsAction(int amount, AbstractMonster m, boolean anyNumber) {
        this(amount, m, anyNumber, c -> {});
    }

    public ThrowCardsAction(int amount, AbstractMonster m, boolean anyNumber, Consumer<List<AbstractCard>> followup) {
        this(amount, m, anyNumber, c -> true, followup);
    }

    public ThrowCardsAction(int amount, AbstractMonster m, boolean anyNumber, Predicate<AbstractCard> filter, Consumer<List<AbstractCard>> followup) {
        super(amount, DiscardAction.TEXT[0], anyNumber, anyNumber, filter, cards -> {
            followup.accept(cards);
            for (AbstractCard c : cards) {
                if (m != null) {
                    int damage = WeightPatches.WeightField.weight.get(c);
                    for (AbstractPower p : Wiz.adp().powers) {
                        if (p instanceof ThrowBoostPower) {
                            damage += ((ThrowBoostPower) p).bonusDamage(c);
                        }
                    }
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(VFXContainer.throwEffect(TextureSniper.snipeCard(c), 0.25f, m.hb, MainModfile.ARMORED_MAUVE_COLOR, true, true)));
                    AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c));
                }
            }
        });
    }
}
