package xmlfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XSDValidator {

    static public void validate(InputStream xsdStream, InputStream xmlStream) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Source xsdSource = new StreamSource(xsdStream);
        Schema schema = schemaFactory.newSchema(xsdSource);

        Source xmlSource = new StreamSource(xmlStream);

        Validator validator = schema.newValidator();
        validator.validate(xmlSource);
    }

    public static void main(String args[]){
        String xsdPath = "other/books.xsd";
        String xmlPath = "other/books.xml";
        InputStream xsdStream = null;
        InputStream xmlStream = null;

        try {
            xsdStream = new FileInputStream(xsdPath);
            xmlStream = new FileInputStream(xmlPath);
            XSDValidator.validate(xsdStream, xmlStream);
            System.out.println("Validate successfully.");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (xsdStream != null) {
                try {
                    xsdStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (xmlStream != null) {
                try {
                    xmlStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}