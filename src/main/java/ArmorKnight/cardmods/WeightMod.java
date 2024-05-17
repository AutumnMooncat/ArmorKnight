package ArmorKnight.cardmods;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.WeightPatches;
import ArmorKnight.util.TexLoader;
import ArmorKnight.util.TextureScaler;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static ArmorKnight.MainModfile.makeID;


public class WeightMod extends AbstractCardModifier {
    public static String ID = makeID(WeightMod.class.getSimpleName());
    public static Texture modIcon = TexLoader.getTexture(MainModfile.modID + "Resources/images/ui/anvil.png");
    public int amount;

    public WeightMod(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, ID);
        if (!mods.isEmpty()) {
            WeightMod mod = (WeightMod) mods.get(0);
            mod.amount += amount;
            WeightPatches.WeightField.weight.set(card, WeightPatches.WeightField.weight.get(card) + amount);
            return false;
        }
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        WeightPatches.WeightField.weight.set(card, WeightPatches.WeightField.weight.get(card) + amount);
    }

    /*@Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(modIcon).text(String.valueOf(amount)).textOffsetX(3).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(modIcon).text(String.valueOf(amount)).textOffsetX(6).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
    }*/

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new WeightMod(amount);
    }
}
