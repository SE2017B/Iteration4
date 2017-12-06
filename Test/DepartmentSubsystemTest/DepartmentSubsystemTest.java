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
        Assert.assertEquals(this.DSS.getServices().size(), 4);
    }

    @Test
    public void testExport(){

    }

    @Test
    public void testServices(){

    }
}
