package semicolon.umjavaws.info;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import semicolon.umjavaws.commons.utils.StringUtility;

@ConfigurationProperties("info")
@Component
@Data
public class Info {
    private String version;
    private Map<String, Object> date;
    
    public Map<String, Object> getDate() {
        return StringUtility.toSnakeCase(date);
    }
}