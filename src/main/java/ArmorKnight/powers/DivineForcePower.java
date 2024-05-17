package ArmorKnight.powers;

import ArmorKnight.MainModfile;
import ArmorKnight.patches.BattleCleanupManager;
import ArmorKnight.util.Wiz;
import ArmorKnight.vfx.AscensionAuraEffect;
import ArmorKnight.vfx.AscensionEffect;
import basemod.Pair;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.UncommonPotionParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;

import java.util.ArrayList;
import java.util.Random;

public class DivineForcePower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(DivineForcePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float flashTimer;
    private float auraTimer;
    private final ArrayList<AbstractGameEffect> array;
    private static final Random rng = new Random();
    private float particleTimer;
    private float angle;
    private static final float PARTICLE_INTERVAL = 0.1f;
    private static Pair<String, Long> loopKey;
    private static BattleCleanupManager.CleanupLogic cleanup;
    private static final String LOOP_SFX = "STANCE_LOOP_WRATH";
    private static final String ENTER_SFX = "STANCE_ENTER_CALM";
    private static final boolean SPINNY = false;

    public DivineForcePower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.loadRegion("nirvana");
        updateDescription();
        this.priority = -5;
        array = ReflectionHacks.getPrivateInherited(this, DivineForcePower.class, "effect");
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (loopKey != null) {
            stopIdleSfx();
        }
        CardCrawlGame.sound.play(ENTER_SFX);
        loopKey = new Pair<>(LOOP_SFX, CardCrawlGame.sound.playAndLoop(LOOP_SFX));
        cleanup = BattleCleanupManager.addLogic(() -> {
            CardCrawlGame.sound.stop(loopKey.getKey(), loopKey.getValue());
            loopKey = null;
        });
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        AbstractDungeon.effectList.add(new AscensionEffect());
    }

    @Override
    public void onRemove() {
        super.onRemove();
        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (loopKey != null) {
            BattleCleanupManager.runCleanup(cleanup);
        }
    }

/*    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            damage /= 2f;
        }
        return damage;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && info.output > 0) {
            flash();
            addToTop(new DamageAction(info.owner, new DamageInfo(owner, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
        }
        return damageAmount;
    }*/

    @Override
    public void atEndOfRound() {
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        flashTimer += Gdx.graphics.getDeltaTime();
        if (flashTimer > 1f) {
            if (!SPINNY) {
                array.add(new SilentGainPowerEffect(this));
            }
            flashTimer = 0f;
        }
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getRawDeltaTime();
        this.auraTimer -= Gdx.graphics.getRawDeltaTime();
        if (SPINNY) {
            this.angle += Gdx.graphics.getRawDeltaTime() * 300f;
            angle %= 360;
        }
        if (this.auraTimer < 0.0F) {
            this.auraTimer = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new AscensionAuraEffect());
        }
        if (this.particleTimer < 0.0F) {
            float xOff = ((owner.hb_w) * (float) rng.nextGaussian())*0.25f;
            if(MathUtils.randomBoolean()) {
                xOff = -xOff;
            }
            float yOff = ((owner.hb_w) * (float) rng.nextGaussian())*0.25f;
            if(MathUtils.randomBoolean()) {
                yOff = -yOff;
            }
            //AbstractDungeon.effectList.add(new StraightFireParticle(owner.drawX + xOff, owner.drawY + MathUtils.random(owner.hb_h/2f), 75f));
            AbstractDungeon.effectList.add(new UncommonPotionParticleEffect(owner.hb.cX+xOff, owner.hb.cY+yOff));
            this.particleTimer = PARTICLE_INTERVAL;
        }
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        sb.setColor(c);
        sb.draw(this.region48, x - (float)this.region48.packedWidth / 2.0F, y - (float)this.region48.packedHeight / 2.0F, (float)this.region48.packedWidth / 2.0F, (float)this.region48.packedHeight / 2.0F, (float)this.region48.packedWidth, (float)this.region48.packedHeight, Settings.scale, Settings.scale, angle);
        for (AbstractGameEffect e : array) {
            e.render(sb, x, y);
        }
    }
}