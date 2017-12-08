package DepartmentSubsystemTest;

import DepartmentSubsystem.DepartmentSubsystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DepartmentSubsystemTest {
    private DepartmentSubsystem DSS;
    @Before
    public void prepDeptSubSystem(){
        this.DSS = DepartmentSubsystem.getSubsystem();
    }

    @Test
    public void testInit(){
        Assert.assertEquals("[Translation Department, Transportation Department, Facilities, Food]", this.DSS.getDepartments().toString());
    }

    @Test
    public void testServices(){
        Assert.assertEquals("[translation service]", this.DSS.getDepartments().get(0).getServices().toString());
        Assert.assertEquals("[Transport service]", this.DSS.getDepartments().get(1).getServices().toString());
        Assert.assertEquals("[Sanitation]", this.DSS.getDepartments().get(2).getServices().toString());
        Assert.assertEquals("[Food Delivery Service]", this.DSS.getDepartments().get(3).getServices().toString());
    }
}
