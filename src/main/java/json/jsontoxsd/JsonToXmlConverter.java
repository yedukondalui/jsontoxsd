package json.jsontoxsd;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
public class JsonToXmlConverter {

    public static void convertJsonToXml(String jsonPath, String xmlPath) {
        try {
            File jsonFile = new File(jsonPath);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode;

            // ✅ Check if JSON file exists
            if (!jsonFile.exists()) {
                throw new IllegalArgumentException("❌ JSON file not found: " + jsonPath);
            }

            // ✅ Validate JSON format
            try {
                jsonNode = objectMapper.readTree(jsonFile);
            } catch (IOException e) {
                throw new IllegalArgumentException("❌ Invalid JSON format in file: " + jsonFile.getName(), e);
            }

            // ✅ Write XML if JSON is valid
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(new File(xmlPath), jsonNode);
            System.out.println("✅ XML generated successfully at: " + xmlPath);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to convert JSON to XML for: " + jsonPath, e);
        }
    }
}
