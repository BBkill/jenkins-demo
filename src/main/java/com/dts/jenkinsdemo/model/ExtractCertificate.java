package com.dts.jenkinsdemo.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ExtractCertificate {
    public static String xmlFile = "E:\\jenkins\\demo\\src\\main\\java\\com\\dts\\jenkinsdemo\\model\\2023_HS1017086749.xml";

    public static void main(String[] args) {
        try {


            List<String> certFiles = extractCertificates(xmlFile);
            System.out.println("Extracted " + certFiles.size() + " certificates");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> extractCertificates(String xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        List<String> certFiles = new ArrayList<>();

        // Create document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse XML file
        Document doc = builder.parse(new File(xmlFile));

        // Find all X509Certificate elements
        NodeList certList = doc.getElementsByTagNameNS(
                "http://www.w3.org/2000/09/xmldsig#", "X509Certificate");

        if (certList.getLength() == 0) {
            System.out.println("No certificates found in the XML file");
            return certFiles;
        }

        // Extract each certificate
        for (int i = 0; i < certList.getLength(); i++) {
            Element certElement = (Element) certList.item(i);
            String certContent = certElement.getTextContent().trim();

            // Generate output filename
            String outputFile = "certificate_" + (i + 1) + ".pem";

            // Decode base64 and write to PEM file
            byte[] certData = Base64.getDecoder().decode(certContent);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write("-----BEGIN CERTIFICATE-----\n".getBytes());
                fos.write(Base64.getEncoder().encode(certData));
                fos.write("\n-----END CERTIFICATE-----\n".getBytes());
            }

            certFiles.add(outputFile);
            System.out.println("Certificate " + (i + 1) + " extracted and saved to " + outputFile);
        }

        return certFiles;
    }
}
