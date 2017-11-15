//testing file for Node testing each method
import a_star.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {
    public NodeTest(){}

    private Node bathroom = new Node("Bathroom", "101", "Bath", 1, 0, 1);
    private Node cafe = new Node("Cafe", "111", "Food", 1, 1, 1);
    private Node nursesRoom = new Node("nurseRoom", "23", "nurseRoom", 1, 2, 1);

    @Before
    public void initialize(){
        bathroom.addConnection(cafe);
        cafe.addConnection(bathroom);

        nursesRoom.addConnection(cafe);
        cafe.addConnection(nursesRoom);
    }

    @Test
    public void testNodeParameters(){
        Assert.assertEquals(bathroom.getName(), "Bathroom");
        Assert.assertEquals(bathroom.getID(), "101");
        Assert.assertEquals(bathroom.getType(), "Bath");
    }

    @Test
    public void testCost(){

    }
}
