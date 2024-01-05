package cn.slibs.test;

import cn.slibs.base.utils.HttpUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/4 21:06
 */
public class HttpUtilsTest {
    @Test
    void testJsonContentType() {
        String jsonType01 = "application/json;charset=UTF-8";
        String jsonType02 = "application/json";
        String jsonType03 = "application/xxx+json";
        String jsonType04 = "application/xxxxxx+json;charset=UTF-8";
        String jsonType05 = null;
        String jsonType06 = "   ";
        String jsonType07 = "application/xxx-json";
        String jsonType08 = "application/xxxxxx-json;charset=UTF-8";
        String jsonType09 = "application/x-json-stream";
        String jsonType10 = "application/json-seq";
        String jsonType11 = "application/x-ndjson";
        String jsonType12 = "application/xxx+xml";
        String jsonType13 = "application/JSON";
        String jsonType14 = "model/gltf+Json";

        boolean jsonContentType01 = HttpUtils.isJsonContentType(jsonType01);
        boolean jsonContentType02 = HttpUtils.isJsonContentType(jsonType02);
        boolean jsonContentType03 = HttpUtils.isJsonContentType(jsonType03);
        boolean jsonContentType04 = HttpUtils.isJsonContentType(jsonType04);
        boolean jsonContentType05 = HttpUtils.isJsonContentType(jsonType05);
        boolean jsonContentType06 = HttpUtils.isJsonContentType(jsonType06);
        boolean jsonContentType07 = HttpUtils.isJsonContentType(jsonType07);
        boolean jsonContentType08 = HttpUtils.isJsonContentType(jsonType08);
        boolean jsonContentType09 = HttpUtils.isJsonContentType(jsonType09);
        boolean jsonContentType10 = HttpUtils.isJsonContentType(jsonType10);
        boolean jsonContentType11 = HttpUtils.isJsonContentType(jsonType11);
        boolean jsonContentType12 = HttpUtils.isJsonContentType(jsonType12);
        boolean jsonContentType13 = HttpUtils.isJsonContentType(jsonType13);
        boolean jsonContentType14 = HttpUtils.isJsonContentType(jsonType14);


        assertTrue(jsonContentType01);
        assertTrue(jsonContentType02);
        assertTrue(jsonContentType03);
        assertTrue(jsonContentType04);
        assertFalse(jsonContentType05);
        assertFalse(jsonContentType06);
        assertTrue(jsonContentType07);
        assertTrue(jsonContentType08);
        assertFalse(jsonContentType09);
        assertFalse(jsonContentType10);
        assertFalse(jsonContentType11);
        assertFalse(jsonContentType12);
        assertTrue(jsonContentType13);
        assertTrue(jsonContentType14);
    }


    @Test
    void testXmlContentType() {
        String xmlType01 = "application/xml;charset=UTF-8";
        String xmlType02 = "application/xml";
        String xmlType03 = "application/xxx+xml";
        String xmlType04 = "application/xxxxxx+xml;charset=UTF-8";
        String xmlType05 = null;
        String xmlType06 = "   ";
        String xmlType07 = "application/xxx-xml";
        String xmlType08 = "application/xxxxxx-xml;charset=UTF-8";
        String xmlType09 = "application/x-xml-stream";
        String xmlType10 = "application/xml-seq";
        String xmlType11 = "application/x-ndxml";
        String xmlType12 = "application/xxx+json";
        String xmlType13 = "application/XML";

        boolean xmlContentType01 = HttpUtils.isXmlContentType(xmlType01);
        boolean xmlContentType02 = HttpUtils.isXmlContentType(xmlType02);
        boolean xmlContentType03 = HttpUtils.isXmlContentType(xmlType03);
        boolean xmlContentType04 = HttpUtils.isXmlContentType(xmlType04);
        boolean xmlContentType05 = HttpUtils.isXmlContentType(xmlType05);
        boolean xmlContentType06 = HttpUtils.isXmlContentType(xmlType06);
        boolean xmlContentType07 = HttpUtils.isXmlContentType(xmlType07);
        boolean xmlContentType08 = HttpUtils.isXmlContentType(xmlType08);
        boolean xmlContentType09 = HttpUtils.isXmlContentType(xmlType09);
        boolean xmlContentType10 = HttpUtils.isXmlContentType(xmlType10);
        boolean xmlContentType11 = HttpUtils.isXmlContentType(xmlType11);
        boolean xmlContentType12 = HttpUtils.isXmlContentType(xmlType12);
        boolean xmlContentType13 = HttpUtils.isXmlContentType(xmlType13);

        assertTrue(xmlContentType01);
        assertTrue(xmlContentType02);
        assertTrue(xmlContentType03);
        assertTrue(xmlContentType04);
        assertFalse(xmlContentType05);
        assertFalse(xmlContentType06);
        assertFalse(xmlContentType07);
        assertFalse(xmlContentType08);
        assertFalse(xmlContentType09);
        assertFalse(xmlContentType10);
        assertFalse(xmlContentType11);
        assertFalse(xmlContentType12);
        assertTrue(xmlContentType13);

    }

    @Test
    void testNonTextContentType() {
        String contentType01 = "application/xml;charset=UTF-8";
        String contentType02 = "application/xml";
        String contentType03 = "application/xxx+xml";
        String contentType04 = "application/xxxxxx+json;charset=UTF-8";
        String contentType05 = null;
        String contentType06 = "   ";
        String contentType07 = "text/cql";
        String contentType08 = "application/xxxxxx-xml;charset=UTF-8";
        String contentType09 = "application/x-xml-stream";
        String contentType10 = "application/xml-seq";
        String contentType11 = "application/x-ndxml";
        String contentType12 = "application/xxx+json";
        String contentType13 = "application/xxx-json";
        String contentType14 = "application/vnd.ipld.dag-json";
        String contentType15 = "application/x-json-stream";
        String contentType16 = "application/octet-stream";
        String contentType17 = "audio/LPC";
        String contentType18 = "application/vnd.openxmlformats-officedocument.drawingml.chart+xml";
        String contentType19 = "model/gltf+json";
        String contentType20 = "application/vnd.3gpp.s1ap";
        String contentType21 = "application/vnd.xxx+xml";
        String contentType22 = "application/tlsrpt+gzip";
        String contentType23 = "multipart/form-data";
        String contentType24 = "application/pdf";
        String contentType25 = "model/prc";


        boolean nonTextType01 = HttpUtils.isNonTextContentType(contentType01);
        boolean nonTextType02 = HttpUtils.isNonTextContentType(contentType02);
        boolean nonTextType03 = HttpUtils.isNonTextContentType(contentType03);
        boolean nonTextType04 = HttpUtils.isNonTextContentType(contentType04);
        boolean nonTextType05 = HttpUtils.isNonTextContentType(contentType05);
        boolean nonTextType06 = HttpUtils.isNonTextContentType(contentType06);
        boolean nonTextType07 = HttpUtils.isNonTextContentType(contentType07);
        boolean nonTextType08 = HttpUtils.isNonTextContentType(contentType08);
        boolean nonTextType09 = HttpUtils.isNonTextContentType(contentType09);
        boolean nonTextType10 = HttpUtils.isNonTextContentType(contentType10);
        boolean nonTextType11 = HttpUtils.isNonTextContentType(contentType11);
        boolean nonTextType12 = HttpUtils.isNonTextContentType(contentType12);
        boolean nonTextType13 = HttpUtils.isNonTextContentType(contentType13);
        boolean nonTextType14 = HttpUtils.isNonTextContentType(contentType14);
        boolean nonTextType15 = HttpUtils.isNonTextContentType(contentType15);
        boolean nonTextType16 = HttpUtils.isNonTextContentType(contentType16);
        boolean nonTextType17 = HttpUtils.isNonTextContentType(contentType17);
        boolean nonTextType18 = HttpUtils.isNonTextContentType(contentType18);
        boolean nonTextType19 = HttpUtils.isNonTextContentType(contentType19);
        boolean nonTextType20 = HttpUtils.isNonTextContentType(contentType20);
        boolean nonTextType21 = HttpUtils.isNonTextContentType(contentType21);
        boolean nonTextType22 = HttpUtils.isNonTextContentType(contentType22);
        boolean nonTextType23 = HttpUtils.isNonTextContentType(contentType23);
        boolean nonTextType24 = HttpUtils.isNonTextContentType(contentType24);
        boolean nonTextType25 = HttpUtils.isNonTextContentType(contentType25);


        assertFalse(nonTextType01);
        assertFalse(nonTextType02);
        assertFalse(nonTextType03);
        assertFalse(nonTextType04);
        assertTrue(nonTextType05);
        assertTrue(nonTextType06);
        assertFalse(nonTextType07);
        assertFalse(nonTextType08);
        assertTrue(nonTextType09);
        assertFalse(nonTextType10);
        assertFalse(nonTextType11);
        assertFalse(nonTextType12);
        assertFalse(nonTextType13);
        assertFalse(nonTextType14);
        assertTrue(nonTextType15);
        assertTrue(nonTextType16);
        assertTrue(nonTextType17);
        assertFalse(nonTextType18);
        assertFalse(nonTextType19);
        assertTrue(nonTextType20);
        assertFalse(nonTextType21);
        assertTrue(nonTextType22);
        assertTrue(nonTextType23);
        assertTrue(nonTextType24);
        assertTrue(nonTextType25);

    }
}
