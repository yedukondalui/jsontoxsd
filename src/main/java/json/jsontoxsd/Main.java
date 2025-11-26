package json.jsontoxsd;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        try {
            // Base directory for JSON files
            String basePath = "C:/Users/yedukondalu/Downloads/jsontoxsd/jsontoxsd/src/main/resources/";
            File jsonFolder = new File(basePath);

            // Validate JSON folder
            if (!jsonFolder.exists() || !jsonFolder.isDirectory()) {
                throw new RuntimeException("❌ JSON folder not found at: " + basePath);
            }

            // Get .json files only
            File[] jsonFiles = jsonFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

            if (jsonFiles == null || jsonFiles.length == 0) {
                throw new RuntimeException("⚠️ No JSON files found in: " + basePath);
            }

            // Process each JSON file
            for (File jsonFile : jsonFiles) {

                String jsonFileName = jsonFile.getName();
                String baseFileName = jsonFileName.substring(0, jsonFileName.lastIndexOf('.'));

                String xmlOutputPath = basePath + baseFileName + ".xml";
                String xsdOutputPath = basePath + baseFileName + ".xsd";

                System.out.println("\n📄 Processing file: " + jsonFileName);

                // Step 1 — Convert JSON → XML
                JsonToXmlConverter.convertJsonToXml(jsonFile.getAbsolutePath(), xmlOutputPath);

                // Step 2 — Convert XML → XSD
                XmlToXsdGenerator.generateXsd(xmlOutputPath, xsdOutputPath);

                System.out.println("✅ Completed processing: " + jsonFileName);
            }

            System.out.println("\n🎉 All JSON files converted successfully!");

        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
