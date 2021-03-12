import domain.Road;
import org.junit.Test;

public class DemoTest {
    @Test
    public void test1 () {
        Road rd = new Road();
        rd.setId(1);
        System.out.println(rd.getId());
    }
}
