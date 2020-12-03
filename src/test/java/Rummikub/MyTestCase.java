package Rummikub;

import org.junit.rules.TestName;
import org.junit.Before;
import org.junit.Rule;
import org.junit.After;

public class MyTestCase {

    @Rule public TestName name = new TestName();

    // Helps make output easier to read
    @Before
    public void infoStart() {
        System.out.println("--------------");
        System.out.println(name.getMethodName());
    }
    @After
    public void infoEnd() {
        System.out.println(name.getMethodName());
        System.out.println("--------------");
    }

}
