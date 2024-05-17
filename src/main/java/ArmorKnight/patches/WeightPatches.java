package ArmorKnight.patches;

import ArmorKnight.MainModfile;
import ArmorKnight.cards.abstracts.AbstractEasyCard;
import ArmorKnight.util.TexLoader;
import ArmorKnight.util.Wiz;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

public class WeightPatches {
    private static final Color WHITEISH = Color.WHITE.cpy();
    public static Texture weightIcon = TexLoader.getTexture(MainModfile.modID + "Resources/images/ui/anvil.png");
    public static Texture weightIconSCV = TexLoader.getTexture(MainModfile.modID + "Resources/images/ui/anvilSCV.png");
    private static final float spacing = 60f;
    private static final float spacingSCV = 120f;

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class WeightField {
        public static SpireField<Integer> weight = new SpireField<>(() -> -1);
    }

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {String.class, String.class, String.class, int.class, String.class, AbstractCard.CardType.class, AbstractCard.CardColor.class, AbstractCard.CardRarity.class, AbstractCard.CardTarget.class, DamageInfo.DamageType.class})
    public static class PutCardModOnEveryCard {
        @SpirePostfixPatch
        public static void plz(AbstractCard __instance) {
            //CardModifierManager.addModifier(__instance, new WeightMod(calcWeight2(__instance)));
            WeightField.weight.set(__instance, calcWeight2(__instance));
        }
    }

    private static int calcWeight(AbstractCard card) {
        try {
            CtClass ctc = Loader.getClassPool().get(card.getClass().getName());
            for (CtMethod ctm : ctc.getDeclaredMethods()) {
                if (ctm.getName().equals("use")) {
                    MethodInfo info = ctm.getMethodInfo2();
                    int start = info.getLineNumber(0);
                    int end = info.getLineNumber(info.getCodeAttribute().getCodeLength());
                    return end - start;
                }
            }
            return 0;
        } catch (NotFoundException e) {
            return 0;
        }
    }

    private static int calcWeight2(AbstractCard card) {
        int weight = 0;
        try {
            CtClass ctc = Loader.getClassPool().get(card.getClass().getName());
            for (CtMethod ctm : ctc.getMethods()) {
                if (ctm.getName().equals("upgrade") || ctm.getName().equals("upp")) {
                    CodeIterator ci = ctm.getMethodInfo2().getCodeAttribute().iterator();
                    while (ci.hasNext()) {
                        int index = ci.next();
                        int op = ci.byteAt(index);
                        //System.out.println(Mnemonic.OPCODE[op]);
                        if (op == Bytecode.ICONST_1 || op == Bytecode.ICONST_M1) {
                            weight += 1;
                        } else if (op == Bytecode.ICONST_2) {
                            weight += 2;
                        } else if (op == Bytecode.ICONST_3) {
                            weight += 3;
                        } else if (op == Bytecode.ICONST_4) {
                            weight += 4;
                        } else if (op == Bytecode.ICONST_5) {
                            weight += 5;
                        } else if (op == Bytecode.BIPUSH) {
                            weight += Math.abs(ci.byteAt(index + 1));
                        }
                    }
                }
            }
        } catch (NotFoundException | BadBytecode e) {
            return weight;
        }
        return weight;
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onRender")
    public static class RenderHook {
        @SpirePrefixPatch
        public static void preMods(AbstractCard card, SpriteBatch sb) {
            if (shouldRender(card)) {
                doElementRendering(card, sb);
            }
        }
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onSingleCardViewRender")
    public static class SCVRenderHook {
        @SpirePrefixPatch
        public static void preMods(SingleCardViewPopup screen, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(screen, SingleCardViewPopup.class, "card");
            if (!(card instanceof AbstractEasyCard) && !MainModfile.renderOffCharacter && !Wiz.isInKnightRun()) {
                return;
            }
            doElementRenderingSCV(card, sb);
        }
    }

    public static boolean shouldRender(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard) && !MainModfile.renderOffCharacter && !Wiz.isInKnightRun()) {
            return false;
        }
        return !card.isFlipped && (card.current_y >= -200.0F * Settings.scale && card.current_y <= Settings.HEIGHT + 200.0F * Settings.scale);
    }

    public static void doElementRendering(AbstractCard card, SpriteBatch sb) {
        WHITEISH.a = card.transparency;
        sb.setColor(WHITEISH);
        float dx = 0f; //-132
        float dy = -192f / MainModfile.elementIconSize; //192 //210
        Texture t = weightIcon;
        BitmapFont font = FontHelper.cardEnergyFont_L;
        font.getData().setScale(card.drawScale);
        sb.draw(t, card.current_x + dx - t.getWidth()/2f, card.current_y + dy - t.getHeight()/2f,
                t.getWidth()/2f - dx, t.getHeight()/2f - dy, t.getWidth(), t.getHeight(),
                card.drawScale * Settings.scale * MainModfile.elementIconSize, card.drawScale * Settings.scale * MainModfile.elementIconSize, card.angle,
                0, 0, t.getWidth(), t.getHeight(), false, false);
        FontHelper.renderRotatedText(sb, font, String.valueOf(WeightField.weight.get(card)), card.current_x, card.current_y, (dx + 4f) * card.drawScale * Settings.scale, (dy) * card.drawScale * Settings.scale, card.angle, false, WHITEISH);
        sb.setColor(Color.WHITE);
    }

    public static void doElementRenderingSCV(AbstractCard card, SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        float dx = 0f; //-292
        float dy = -384f / MainModfile.elementIconSize; //404
        Texture t = weightIconSCV;
        sb.draw(t, Settings.WIDTH/2f + dx - t.getWidth()/2f, Settings.HEIGHT/2f + dy - t.getHeight()/2f,
                t.getWidth()/2f - dx, t.getHeight()/2f - dy, t.getWidth(), t.getHeight(),
                Settings.scale * MainModfile.elementIconSize, Settings.scale * MainModfile.elementIconSize, card.angle,
                0, 0, t.getWidth(), t.getHeight(), false, false);
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(WeightField.weight.get(card)), (float)Settings.WIDTH / 2.0F - (dx - 8f) * Settings.scale, (float)Settings.HEIGHT / 2.0F + (dy) * Settings.scale, Settings.CREAM_COLOR);
    }
}
