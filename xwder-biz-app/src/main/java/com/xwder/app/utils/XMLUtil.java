package com.xwder.app.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class XMLUtil {
    private static Log logger = LogFactory.getLog(XMLUtil.class);

    /**
     * <p>Create XML Document by String.</p>
     *
     * @param xmlText
     * @return
     */
    public static Document createDoc(String xmlText) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    /**
     * <p>Create XML Document by Inputstream.</p>
     *
     * @param inputStream
     * @return
     */
    public static Document createDoc(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    /**
     * <p>Document to String.</p>
     *
     * @param doc
     * @param charsets
     * @return
     */
    public static String doc2String(Document doc, String... charsets) {
        String charsetName = StringUtils.UTF8;

        if (ArrayUtils.isNotEmpty(charsets)) {
            charsetName = charsets[0];
        }

        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(charsetName);
            StringWriter stringWriter = new StringWriter();
            XMLWriter writer = new XMLWriter(stringWriter, format);
            writer.write(doc);
            writer.close();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Document to XML file.</p>
     *
     * @param doc
     * @param xmlFilePath
     * @param charsets
     */
    public static void doc2File(Document doc, String xmlFilePath, String... charsets) {
        String charsetName = StringUtils.UTF8;

        if (ArrayUtils.isNotEmpty(charsets)) {
            charsetName = charsets[0];
        }

        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(charsetName);
            XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFilePath), format);
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
