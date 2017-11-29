package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.DepartmentSubsystem;
import controllers.ControllableScreen;
import controllers.ScreenController;
import map.Node;

public abstract class CurrentServiceController implements ControllableScreen{
    protected String inputData;
    protected Node inputnode;
    protected DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();
    @Override
    public void init(){

    }

    @Override
    public void setParentController(ScreenController parent) {

    }


    public abstract String getInputData();
}
