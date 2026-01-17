package cn.slibs.test;

import cn.slibs.base.utils.HttpUtils;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/4 21:06
 */
public class HttpUtilsTest {

    @Test
    void testGetMapFromQuery() {
        System.out.println("===============testGetMapFromQuery===============");
        String queryString = "color=red&user%20name=%E6%9D%8E%E5%9B%9B&color=green&size=large&color=蓝色&color=&user%20name=%E5%BC%A0%20%E4%B8%89";
        Map<String, String[]> mapMultiValue = HttpUtils.getMapMultiValueFromQuery(queryString);
        Map<String, String> map = HttpUtils.getMapFromQuery(queryString);
        System.out.println(G.toString(mapMultiValue));
        System.out.println(G.toString(map));
        assertEquals(G.toString(mapMultiValue), "{\"color\"=[\"red\", \"\", \"green\", \"蓝色\"], \"user name\"=[\"李四\", \"张 三\"], \"size\"=[\"large\"]}");
        assertEquals(G.toString(map), "{\"color\"=\"red\", \"user name\"=\"李四\", \"size\"=\"large\"}");

        mapMultiValue.put("size", new String[]{"large", null, null});
        String url1 = HttpUtils.concatUrl("http://127.0.0.1", mapMultiValue);
        String url2 = HttpUtils.concatUrl("http://127.0.0.1", map);
        System.out.println(url1);
        System.out.println(url2);
        System.out.println(HttpUtils.decodeUrlUTF8(url1));
        System.out.println(HttpUtils.decodeUrlUTF8(url2));
        assertEquals(HttpUtils.decodeUrlUTF8(url1), "http://127.0.0.1?color=green&color=&size=large&color=蓝色&user name=张 三&size=&user name=李四&color=red");
        assertEquals(HttpUtils.decodeUrlUTF8(url2), "http://127.0.0.1?size=large&user name=李四&color=red");
    }

    @Test
    void testConcatUrl() {
        System.out.println("===============testConcatUrl===============");
        String url1 = HttpUtils.concatUrl("http://127.0.0.1");
        // color=red&user%20name=%E6%9D%8E%E5%9B%9B&color=green&size=large&color=蓝色&color=&user%20name=%E5%BC%A0%20%E4%B8%89
        String url2 = HttpUtils.concatUrl("http://127.0.0.1",
                "color", "red", "user name", "李四", "color", "green", "size",
                "large", "color", "蓝色", "color", "", "user name", "张 三");
        System.out.println(url1);
        System.out.println(url2);
        System.out.println(HttpUtils.decodeUrlUTF8(url1));
        System.out.println(HttpUtils.decodeUrlUTF8(url2));
        assertEquals(HttpUtils.decodeUrlUTF8(url1), "http://127.0.0.1");
        assertEquals(HttpUtils.decodeUrlUTF8(url2), "http://127.0.0.1?color=green&size=large&color=&color=蓝色&user name=张 三&user name=李四&color=red");
    }

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

        boolean jsonContentType01 = HttpUtils.isJson(jsonType01);
        boolean jsonContentType02 = HttpUtils.isJson(jsonType02);
        boolean jsonContentType03 = HttpUtils.isJson(jsonType03);
        boolean jsonContentType04 = HttpUtils.isJson(jsonType04);
        boolean jsonContentType05 = HttpUtils.isJson(jsonType05);
        boolean jsonContentType06 = HttpUtils.isJson(jsonType06);
        boolean jsonContentType07 = HttpUtils.isJson(jsonType07);
        boolean jsonContentType08 = HttpUtils.isJson(jsonType08);
        boolean jsonContentType09 = HttpUtils.isJson(jsonType09);
        boolean jsonContentType10 = HttpUtils.isJson(jsonType10);
        boolean jsonContentType11 = HttpUtils.isJson(jsonType11);
        boolean jsonContentType12 = HttpUtils.isJson(jsonType12);
        boolean jsonContentType13 = HttpUtils.isJson(jsonType13);
        boolean jsonContentType14 = HttpUtils.isJson(jsonType14);


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

        boolean xmlContentType01 = HttpUtils.isXml(xmlType01);
        boolean xmlContentType02 = HttpUtils.isXml(xmlType02);
        boolean xmlContentType03 = HttpUtils.isXml(xmlType03);
        boolean xmlContentType04 = HttpUtils.isXml(xmlType04);
        boolean xmlContentType05 = HttpUtils.isXml(xmlType05);
        boolean xmlContentType06 = HttpUtils.isXml(xmlType06);
        boolean xmlContentType07 = HttpUtils.isXml(xmlType07);
        boolean xmlContentType08 = HttpUtils.isXml(xmlType08);
        boolean xmlContentType09 = HttpUtils.isXml(xmlType09);
        boolean xmlContentType10 = HttpUtils.isXml(xmlType10);
        boolean xmlContentType11 = HttpUtils.isXml(xmlType11);
        boolean xmlContentType12 = HttpUtils.isXml(xmlType12);
        boolean xmlContentType13 = HttpUtils.isXml(xmlType13);

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


        boolean nonTextType01 = HttpUtils.isNonText(contentType01);
        boolean nonTextType02 = HttpUtils.isNonText(contentType02);
        boolean nonTextType03 = HttpUtils.isNonText(contentType03);
        boolean nonTextType04 = HttpUtils.isNonText(contentType04);
        boolean nonTextType05 = HttpUtils.isNonText(contentType05);
        boolean nonTextType06 = HttpUtils.isNonText(contentType06);
        boolean nonTextType07 = HttpUtils.isNonText(contentType07);
        boolean nonTextType08 = HttpUtils.isNonText(contentType08);
        boolean nonTextType09 = HttpUtils.isNonText(contentType09);
        boolean nonTextType10 = HttpUtils.isNonText(contentType10);
        boolean nonTextType11 = HttpUtils.isNonText(contentType11);
        boolean nonTextType12 = HttpUtils.isNonText(contentType12);
        boolean nonTextType13 = HttpUtils.isNonText(contentType13);
        boolean nonTextType14 = HttpUtils.isNonText(contentType14);
        boolean nonTextType15 = HttpUtils.isNonText(contentType15);
        boolean nonTextType16 = HttpUtils.isNonText(contentType16);
        boolean nonTextType17 = HttpUtils.isNonText(contentType17);
        boolean nonTextType18 = HttpUtils.isNonText(contentType18);
        boolean nonTextType19 = HttpUtils.isNonText(contentType19);
        boolean nonTextType20 = HttpUtils.isNonText(contentType20);
        boolean nonTextType21 = HttpUtils.isNonText(contentType21);
        boolean nonTextType22 = HttpUtils.isNonText(contentType22);
        boolean nonTextType23 = HttpUtils.isNonText(contentType23);
        boolean nonTextType24 = HttpUtils.isNonText(contentType24);
        boolean nonTextType25 = HttpUtils.isNonText(contentType25);


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
