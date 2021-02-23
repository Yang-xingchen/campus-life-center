import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;

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

    @Test
    public void sort() {
        IntStream.of(8,1,6,7,2,9,4,3).map(i -> i-5).sorted().forEach(System.out::println);
    }
}
