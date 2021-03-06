import domain.Worker;
import org.junit.Test;

public class DemoTest {
    @Test
    public void test1 () {
        Worker worker = new Worker();
        worker.setName("Ivanov");
        System.out.println(worker.getName());
    }
}
