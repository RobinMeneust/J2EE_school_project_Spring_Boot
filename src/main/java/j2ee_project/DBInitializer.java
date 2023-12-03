package j2ee_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class DBInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initializeDatabase() {
        String schemaSql = readSqlFile("schema.sql");
        jdbcTemplate.execute(schemaSql);

        Integer nbUsers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM \"User\"", Integer.class);
        if (nbUsers == null || nbUsers == 0) {
            // Load data.sql (insert data) only if the DB is not already populated
            String dataSql = readSqlFile("data.sql");
            jdbcTemplate.execute(dataSql);
        }
    }

    private String readSqlFile(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading " + filename, e);
        }
    }
}
