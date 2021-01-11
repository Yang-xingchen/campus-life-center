package campuslifecenter.mbg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class CreateTableSql {

    public static void main(String[] args) throws Throwable {
        String destFile = "config/sql/schema.sql";
        try(FileOutputStream outputStream = new FileOutputStream(destFile)) {
            Stream.of(
                    "user-center",
                    "notice",
                    "todo",
                    "info"
            )
                    .map(s -> s + "/src/main/resources/schema.sql")
                    .forEach(s -> {
                        try (FileInputStream fileInputStream = new FileInputStream(s)) {
                            fileInputStream.transferTo(outputStream);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        destFile = "config/sql/data.sql";
        try(FileOutputStream outputStream = new FileOutputStream(destFile)) {
            Stream.of(
                    "user-center",
                    "notice",
                    "todo",
                    "info"
            )
                    .map(s -> s + "/src/main/resources/data.sql")
                    .forEach(s -> {
                        try (FileInputStream fileInputStream = new FileInputStream(s)) {
                            fileInputStream.transferTo(outputStream);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

}
