package campuslifecenter.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Generator {

    public static void main(String[] args) throws Exception {
        Stream.of(
                "/generatorConfig-user-center.xml",
                "/generatorConfig-notice.xml"
        ).forEach(Generator::generator);
    }

    private static void generator(String fileName){
        System.out.println(fileName);
        try {
            List<String> warnings = new ArrayList<>();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(Generator.class.getResourceAsStream(fileName));
            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            warnings.forEach(System.err::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
