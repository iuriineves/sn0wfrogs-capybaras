package iuriineves.neves_capybaras.model;

import iuriineves.neves_capybaras.NevesCapybaras;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CapybaraEntityModel extends DefaultedEntityGeoModel {
    public CapybaraEntityModel() {
        super(new Identifier(NevesCapybaras.MOD_ID, "capybara"), true);
    }
}
