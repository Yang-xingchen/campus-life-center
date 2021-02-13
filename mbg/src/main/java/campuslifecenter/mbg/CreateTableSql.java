package campuslifecenter.mbg;

import java.io.*;
import java.util.stream.Stream;

public class CreateTableSql {

    public static void main(String[] args) throws Throwable {
        Stream.of("schema.sql", "data.sql", "test.sql").forEach(fn -> {
            String destFile = "docs/sql/" + fn;
            try(FileOutputStream outputStream = new FileOutputStream(destFile)) {
                Stream.of(
                        "user-center",
                        "notice",
                        "todo",
                        "info",
                        "comment"
                )
                        .map(s -> s + "/src/main/resources/" + fn)
                        .map(File::new)
                        .filter(File::exists)
                        .forEach(file -> {
                            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                                fileInputStream.transferTo(outputStream);
                                outputStream.write('\n');
                                outputStream.write('\n');
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
