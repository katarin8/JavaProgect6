import domain.Road;
import org.junit.Test;

public class DemoTest {
    @Test
    public void test1 () {
        Road worker = new Road();
        worker.setId(1);
        System.out.println(worker.getId());
    }
}
