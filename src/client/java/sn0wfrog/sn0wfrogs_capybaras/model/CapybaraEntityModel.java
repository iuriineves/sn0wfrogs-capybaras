package sn0wfrog.sn0wfrogs_capybaras.model;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.entity.CapybaraEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
//import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.GeoModel;
//import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.animation.AnimationState;

import software.bernie.geckolib.model.data.EntityModelData;

public class CapybaraEntityModel extends GeoModel<CapybaraEntity> {
    private static final Identifier MODEL = Identifier.of(Sn0wfrogsCapybaras.MOD_ID, "geo/entity/capybara.geo.json");
    private static final Identifier ANIMATION = Identifier.of(Sn0wfrogsCapybaras.MOD_ID, "animations/entity/capybara.animation.json");

    @Override
    public Identifier getModelResource(CapybaraEntity animatable) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(CapybaraEntity animatable) {
        return animatable.getCapybaraTexture();
    }

    @Override
    public Identifier getAnimationResource(CapybaraEntity animatable) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(CapybaraEntity animatable, long instanceId, AnimationState<CapybaraEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
