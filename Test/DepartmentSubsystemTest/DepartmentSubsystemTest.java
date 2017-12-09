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
        Assert.assertEquals("[Translation, Transport, Sanitation, Food Delivery]", this.DSS.getServices().toString());
    }

    @Test
    public void testServices(){
        Assert.assertEquals("Translation", this.DSS.getServices().get(0).toString());
        Assert.assertEquals("Transport", this.DSS.getServices().get(1).toString());
        Assert.assertEquals("Sanitation", this.DSS.getServices().get(2).toString());
        Assert.assertEquals("Food Delivery", this.DSS.getServices().get(3).toString());
    }
}
