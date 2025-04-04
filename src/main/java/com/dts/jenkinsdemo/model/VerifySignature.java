package com.dts.jenkinsdemo.model;
import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
public class VerifySignature {
    static {
        Init.init();
    }

    public static void main(String[] args) {
        try {
            String xmlFile = ExtractCertificate.xmlFile;
            List<String> certFiles = ExtractCertificate.extractCertificates(xmlFile);
            verifyAllSignatures(xmlFile, certFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void verifyAllSignatures(String xmlFile, List<String> certFiles)
            throws Exception {
        // Create document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse XML file
        Document doc = builder.parse(new File(xmlFile));

        // Find all Signature elements
        NodeList signatureList = doc.getElementsByTagNameNS(
                Constants.SignatureSpecNS, "Signature");

        if (signatureList.getLength() == 0) {
            System.out.println("No signatures found in the XML file");
            return;
        }

        // Load all certificates
        List<X509Certificate> certificates = new ArrayList<>();
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        for (String certFile : certFiles) {
            try (FileInputStream fis = new FileInputStream(certFile)) {
                X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);
                certificates.add(cert);
                System.out.println("Loaded certificate from " + certFile);
            }
        }

        // Verify each signature with each certificate
        for (int i = 0; i < signatureList.getLength(); i++) {
            Element signatureElement = (Element) signatureList.item(i);
            String signatureId = signatureElement.getAttribute("Id");
            System.out.println("\nVerifying signature ID: " + signatureId);

            // Create XMLSignature with base URI
            XMLSignature signature = new XMLSignature(signatureElement, doc.getBaseURI());

            boolean signatureValid = false;

            for (int j = 0; j < certificates.size(); j++) {
                X509Certificate cert = certificates.get(j);
                try {
                    // Verify the signature
                    boolean isValid = signature.checkSignatureValue(cert);
                    System.out.println("  - With certificate " + (j + 1) + ": " +
                            (isValid ? "VALID" : "INVALID"));

                    if (isValid) {
                        signatureValid = true;
                        System.out.println("  - Certificate " + (j + 1) + " is the correct one for this signature");

                        // Print reference validation results
                        System.out.println("  - Reference validation results:");
                        for (int k = 0; k < signature.getSignedInfo().getLength(); k++) {
                            String refUri = signature.getSignedInfo().item(k).getURI();
                            boolean refValid = signature.getSignedInfo().item(k).verify();
                            System.out.println("    * Reference " + refUri + ": " +
                                    (refValid ? "VALID" : "INVALID"));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  - With certificate " + (j + 1) + ": ERROR - " + e.getMessage());
                    // Print more details about the error
                    if (e.getMessage().contains("XMLSignatureInput")) {
                        System.out.println("    * The referenced element with ID '" +
                                e.getMessage().split("#")[1].split(" ")[0] +
                                "' was not found in the document");
                    }
                }
            }

            System.out.println("Signature " + signatureId + " verification: " +
                    (signatureValid ? "SUCCESS" : "FAILED"));
        }
    }
}
