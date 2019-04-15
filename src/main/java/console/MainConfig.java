package console;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "dao", "mapper", "service", "console.controller" })
public class MainConfig {
}
