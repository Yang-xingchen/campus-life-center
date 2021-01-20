import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

public class NoticeTest {

    @Test
    public void file() {
        Arrays.stream(new File(".").list()).forEach(System.out::println);
    }

    @Test
    public void mkdir() {
        new File("mkdir").mkdir();
        new File("mkdirs").mkdirs();
    }
}
