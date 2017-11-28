package DepartmentSubsystem.Services.Controllers;

import controllers.ControllableScreen;
import controllers.ScreenController;

public class FoodDeliveryController implements ControllableScreen {
    ScreenController parent = new ScreenController();

    @Override
    public void init() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }
}
