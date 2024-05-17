package ArmorKnight.cards.abstracts;

import ArmorKnight.actions.InstantExhaustSpecificCardAction;
import ArmorKnight.cardmods.PlatingMod;
import ArmorKnight.cardmods.WeightMod;
import ArmorKnight.cards.tokens.Plating;
import ArmorKnight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

public abstract class AbstractPlateableCard extends AbstractEasyCard {
    public AbstractPlateableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractPlateableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.adp().hand.group.stream().anyMatch(c -> c instanceof Plating)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void onRightClick() {
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c instanceof Plating) {
                addToTop(new InstantExhaustSpecificCardAction(c, Wiz.adp().hand));
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(AbstractPlateableCard.this, new PlatingMod(c.baseBlock));
                        CardModifierManager.addModifier(AbstractPlateableCard.this, new WeightMod(1));
                        superFlash();
                        AbstractDungeon.effectList.add(new SanctityEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY));
                        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.BLUE, true));
                        CardCrawlGame.sound.play("BLOCK_GAIN_1");
                        this.isDone = true;
                    }
                });
                return;
            }
        }
    }
}
