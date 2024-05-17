package ArmorKnight.actions;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.powers.interfaces.ThrowBoostPower;
import ArmorKnight.util.TextureSniper;
import ArmorKnight.util.Wiz;
import ArmorKnight.vfx.VFXContainer;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GatherAndThrowAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private final boolean upgradedCards;
    private final boolean freeThisTurn;
    private final Predicate<AbstractCard> filter;

    public GatherAndThrowAction(AbstractCreature target, Predicate<AbstractCard> filter) {
        this(target, 3, filter, false, false);
    }

    public GatherAndThrowAction(AbstractCreature target, int amount, Predicate<AbstractCard> filter, boolean freeThisTurn, boolean upgradedCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgradedCards = upgradedCards;
        this.freeThisTurn = freeThisTurn;
        this.filter = filter;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard chosenCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (this.freeThisTurn) {
                        chosenCard.setCostForTurn(0);
                    }

                    if (target != null) {
                        int damage = WeightPatches.WeightField.weight.get(chosenCard);
                        for (AbstractPower p : Wiz.adp().powers) {
                            if (p instanceof ThrowBoostPower) {
                                damage += ((ThrowBoostPower) p).bonusDamage(chosenCard);
                            }
                        }
                        AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.BLUNT_LIGHT));
                        AbstractDungeon.actionManager.addToTop(new VFXAction(VFXContainer.throwEffect(TextureSniper.snipeCard(chosenCard), 0.25f, target.hb, MainModfile.ARMORED_MAUVE_COLOR, true, true)));
                        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(chosenCard));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(chosenCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

        }
        this.tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        validCards.addAll(AbstractDungeon.srcCommonCardPool.group.stream().filter(filter).collect(Collectors.toList()));
        validCards.addAll(AbstractDungeon.srcUncommonCardPool.group.stream().filter(filter).collect(Collectors.toList()));
        validCards.addAll(AbstractDungeon.srcRareCardPool.group.stream().filter(filter).collect(Collectors.toList()));
        if (validCards.isEmpty()) {
            //We probably got the card off character without prismatic shard, just check all cards instead
            validCards.addAll(CardLibrary.getAllCards().stream().filter(c -> filter.test(c) && (c.rarity == AbstractCard.CardRarity.COMMON || c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE)).collect(Collectors.toList()));
        }
        ArrayList<AbstractCard> ret = new ArrayList<>();
        for (int i = 0 ; (i < amount && !validCards.isEmpty()) ; i++) {
            ret.add(validCards.remove(AbstractDungeon.cardRandomRng.random(validCards.size() - 1)).makeCopy());
        }
        if (upgradedCards) {
            for (AbstractCard c : ret) {
                if (c.canUpgrade()) {
                    c.upgrade();
                }
            }
        }
        return ret;
    }
}