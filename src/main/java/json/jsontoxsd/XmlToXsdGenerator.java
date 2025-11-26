package json.jsontoxsd;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;

public class XmlToXsdGenerator {

    public static void generateXsd(String xmlPath, String xsdPath) {
        try {
            File xmlFile = new File(xmlPath);

            // ✅ Validate XML file exists
            if (!xmlFile.exists()) {
                throw new RuntimeException("❌ XML file not found: " + xmlPath);
            }

            // Parse XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Create XSD
            try (FileWriter writer = new FileWriter(new File(xsdPath))) {
                writer.write("<?xml version=\"1.0\"?>\n");
                writer.write("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n");

                String root = doc.getDocumentElement().getNodeName();
                writer.write("  <xs:element name=\"" + root + "\">\n");
                writer.write("    <xs:complexType>\n");
                writer.write("      <xs:sequence>\n");

                NodeList childNodes = doc.getDocumentElement().getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node node = childNodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        writer.write("        <xs:element name=\"" + node.getNodeName() + "\" type=\"xs:string\"/>\n");
                    }
                }

                writer.write("      </xs:sequence>\n");
                writer.write("    </xs:complexType>\n");
                writer.write("  </xs:element>\n");
                writer.write("</xs:schema>\n");
            }

            System.out.println("✅ XSD generated successfully at: " + xsdPath);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to generate XSD for: " + xmlPath, e);
        }
    }
}
