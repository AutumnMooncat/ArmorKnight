package ArmorKnight.vfx;

import ArmorKnight.MainModfile;
import ArmorKnight.util.Wiz;
import basemod.ReflectionHacks;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireRawPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import javassist.*;

public class VFXContainer {
    public static AbstractGameEffect hitBounce(Texture tex, float scale, Hitbox target) {
        return new VfxBuilder(tex, target.cX, target.cY,1.5f)
                .setScale(scale)
                .gravity(50f)
                .velocity(MathUtils.random(45f, 135f), MathUtils.random(600f, 800f))
                .rotate(MathUtils.random(50f, 200f) * (MathUtils.randomBoolean() ? -1 : 1))
                .build();
    }

    public static AbstractGameEffect throwEffect(Texture tex, float scale, Hitbox target, Color color, boolean bounceOff, boolean sfx) {
        VfxBuilder builder = new VfxBuilder(tex, Wiz.adp().hb.cX, Wiz.adp().hb.cY, 0.25f)
                .moveX(Wiz.adp().hb.cX, target.cX, VfxBuilder.Interpolations.POW2OUT)
                .moveY(Wiz.adp().hb.cY, target.cY, VfxBuilder.Interpolations.POW2OUT)
                .rotate(MathUtils.random(100f, 300f) * (MathUtils.randomBoolean() ? -1 : 1))
                .setScale(scale)
                .emitEvery((x,y) -> new ParticleEffect(color.cpy(), x, y), 0.01f);
        if (sfx) {
            builder = builder.playSoundAt(0.0f, "ATTACK_WHIFF_2");
        }
        if (bounceOff) {
            builder = builder.triggerVfxAt(0.25f, 1, (x,y) -> hitBounce(tex, scale, target));
        }
        return builder.build();
    }

    public static AbstractGameEffect fireworkEffect() {
        VfxBuilder builder = new VfxBuilder(ImageMaster.GLOW_SPARK_2, MathUtils.random(0, Settings.WIDTH), MathUtils.random(Settings.HEIGHT/2f, 4*Settings.HEIGHT/5f), 0.1f)
                .playSoundAt(0.1f, MathUtils.random(0.2f, 0.4f), "GHOST_ORB_IGNITE_1")
                .triggerVfxAt(0.1f, 100, VFXContainer::fireworkSprayEffect);
        return builder.build();
    }

    public static AbstractGameEffect fireworkSprayEffect(float x, float y) {
        Color c = MainModfile.getRainbowColor();
        VfxBuilder builder = new VfxBuilder(ImageMaster.GLOW_SPARK_2, x, y, 0.5f)
                .velocity(MathUtils.random(0f, 360f), MathUtils.random(200f, 800f))
                .setColor(c)
                .fadeOut(0.5f);
        AbstractGameEffect effect = builder.build();
        try {
            ReflectionHacks.setPrivate(effect, Class.forName("basemod.helpers.VfxBuilder$BuiltEffect"), "lightsOutSupport", true);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return effect;
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = SpirePatch.CONSTRUCTOR)
    public static class LightsOutSupport {
        @SpireRawPatch
        public static void plz (CtBehavior ctBehavior) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().getCtClass("basemod.helpers.VfxBuilder$BuiltEffect");
            ctClass.setModifiers(Modifier.PUBLIC);
            ctClass.addField(new CtField(CtClass.booleanType, "lightsOutSupport", ctClass));
            ctClass.addMethod(CtNewMethod.make("public float[] _lightsOutGetXYRI() {if (!lightsOutSupport) {return new float[] {0f, 0f, 0f, 0f};} return new float[] {builder.x - (builder.img.originalWidth / 2.0F - builder.img.offsetX), builder.y - (builder.img.originalHeight / 2.0F - builder.img.offsetY), 150f, 0.05f};}", ctClass));
            ctClass.addMethod(CtNewMethod.make("public com.badlogic.gdx.graphics.Color[] _lightsOutGetColor() {return new com.badlogic.gdx.graphics.Color[] {builder.color};}", ctClass));
        }
    }
}
