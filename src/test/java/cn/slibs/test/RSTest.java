package cn.slibs.test;

import cn.slibs.base.map.SOHashMap;
import cn.slibs.base.map.SOMap;
import cn.slibs.base.rs.IStatusCode;
import cn.slibs.base.rs.RS;
import cn.slibs.base.rs.StatusCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iofairy.falcon.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GG
 * @version 1.0
 */
public class RSTest {
    private static ObjectMapper MAPPER = null;
    @BeforeAll
    static void beforeAll() {
        MAPPER = new ObjectMapper();
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Test
    void test1() {
        System.out.println("=============================test1===========================");
        RS<Date> ok = RS.ok();
        try {
            System.out.println(MAPPER.writeValueAsString(ok));      // {"code":"0","msg":"成功！","error":null,"data":null}
            System.out.println(ok);                                 // RS{code='0', msg='成功！', error=null, data=null}
            
            assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"error\":null,\"data\":null}");
            assertEquals(ok.toString(), "RS{code='0', msg='成功！', error=null, data=null}");

            System.out.println("==================================");
            ok.setData(DateTime.parseDate("2024-01-01 00:51:44").get());
            System.out.println(MAPPER.writeValueAsString(ok));      // {"code":"0","msg":"成功！","error":null,"data":"2022-10-09 16:27:07"}
            System.out.println(ok);                                 // RS{code='0', msg='成功！', error=null, data=Sun Oct 09 16:27:07 CST 2022}
            System.out.println("是否成功：" + ok.success());          // 是否成功：true

            assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"error\":null,\"data\":\"2024-01-01 00:51:44\"}");
            assertEquals(ok.toString(), "RS{code='0', msg='成功！', error=null, data=Mon Jan 01 00:51:44 CST 2024}");
            assertTrue(ok.success());

            System.out.println("==================================");
            RS.setDefaultSuccessCode(StatusCodeExt.OK);

            System.out.println("是否成功：" + ok.success());          // 是否成功：false
            RS<Object> newOk = RS.ok();
            System.out.println(MAPPER.writeValueAsString(newOk));   // {"code":"200","msg":"成功！","error":null,"data":null}
            System.out.println(newOk);                              // RS{code='200', msg='成功！', error=null, data=null}
            System.out.println("是否成功：" + newOk.success());       // 是否成功：true

            assertFalse(ok.success());
            assertEquals(MAPPER.writeValueAsString(newOk), "{\"code\":\"200\",\"msg\":\"成功！\",\"error\":null,\"data\":null}");
            assertEquals(newOk.toString(), "RS{code='200', msg='成功！', error=null, data=null}");
            assertTrue(newOk.success());

            RS.setDefaultSuccessCode(StatusCode.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test2() {
        System.out.println("=============================test2===========================");
        RS<Object> error = RS.error(StatusCode.INTERNAL_SERVER_ERROR);
        RS<SOMap> ok = RS.ok();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("a", "helloa");
        hashMap.put("b", "hellob");
        hashMap.put("createTime", DateTime.parseDate("2024-01-01 01:09:12").get());

        SOMap soMap = SOMap.of()
                .putData(hashMap)
                .putData("d", "SOMap_hellod")
                .putData("e", "SOMap_helloe")
                .putData("map", SOMap.of().putData("mapkey1", "value1"));
        SOHashMap soHashMap = SOHashMap.of()
                .putData(hashMap)
                .putData("d", "SOHashMap_hellod")
                .putData("e", "SOHashMap_helloe");

        try {
            System.out.println(MAPPER.writeValueAsString(error));
            System.out.println(error);

            assertEquals(MAPPER.writeValueAsString(error), "{\"code\":\"500\",\"msg\":\"服务内部错误，请联系管理员\",\"error\":null,\"data\":null}");
            assertEquals(error.toString(), "RS{code='500', msg='服务内部错误，请联系管理员', error=null, data=null}");

            System.out.println("==================================");
            ok.setData(soHashMap);
            System.out.println(MAPPER.writeValueAsString(ok));
            System.out.println(ok);

            assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"error\":null,\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOHashMap_hellod\",\"createTime\":\"2024-01-01 01:09:12\",\"e\":\"SOHashMap_helloe\"}}");
            assertEquals(ok.toString(), "RS{code='0', msg='成功！', error=null, data={a=helloa, b=hellob, d=SOHashMap_hellod, createTime=Mon Jan 01 01:09:12 CST 2024, e=SOHashMap_helloe}}");

            System.out.println("==================================");
            ok.setData(soMap);
            System.out.println(MAPPER.writeValueAsString(ok));
            System.out.println(ok);

            assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"error\":null,\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOMap_hellod\",\"createTime\":\"2024-01-01 01:09:12\",\"e\":\"SOMap_helloe\",\"map\":{\"mapkey1\":\"value1\"}}}");
            assertEquals(ok.toString(), "RS{code='0', msg='成功！', error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=Mon Jan 01 01:09:12 CST 2024, e=SOMap_helloe, map={mapkey1=value1}}}");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void test3() {

        String json = "{\"code\":\"0\",\"msg\":\"success!\",\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOMap_hellod\"," +
                "\"createTime\":\"2022-08-03 09:47:52\",\"e\":\"SOMap_helloe\",\"map\":{\"mapkey1\":\"value1\"}}}";
        try {
            RS<SOMap> soMapRS = MAPPER.readValue(json, new TypeReference<RS<SOMap>>() {
            });
            System.out.println(soMapRS);
            System.out.println("code: " + soMapRS.getCode());
            System.out.println("msg: " + soMapRS.getMsg());
            System.out.println("data: " + soMapRS.getData());

            assertEquals(soMapRS.toString(), "RS{code='0', msg='success!', error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");
            assertEquals(soMapRS.getCode(), "0");
            assertEquals(soMapRS.getMsg(), "success!");
            assertEquals(soMapRS.getData().toString(), "{a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}");
            assertTrue(soMapRS.success());

            JsonNode jsonNode = MAPPER.readTree(json);
            RS<SOMap> soMapRS1 = MAPPER.convertValue(jsonNode, new TypeReference<RS<SOMap>>() {
            });
            System.out.println(soMapRS1);
            assertEquals(soMapRS1.toString(), "RS{code='0', msg='success!', error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void test4() {
        RS<User> rs = RS.<User>error(StatusCode.INTERNAL_SERVER_ERROR).setData(new User("John", 10));
        String json1 = null;
        try {
            json1 = MAPPER.writeValueAsString(rs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("RS to Json: " + json1);  // RS to Json: {"code":"90500","msg":"未知错误","error":null,"data":{"name":"John","USER_AGE":10}}

        assertEquals(json1, "{\"code\":\"500\",\"msg\":\"服务内部错误，请联系管理员\",\"error\":null,\"data\":{\"name\":\"John\",\"USER_AGE\":10}}");
        try {
            RS<User> userRS = MAPPER.readValue(json1, new TypeReference<RS<User>>() {
            });
            System.out.println("json to RS: " + userRS);      // json to RS: RS{code='90500', msg='未知错误', error=null, data=User{name='John', age=10}}
            System.out.println("code: " + userRS.getCode());     // code: 90500
            System.out.println("msg: " + userRS.getMsg());       // msg: 未知错误
            User data = userRS.getData();
            System.out.println("data: " + data);            // data: User{name='John', age=10}

            assertEquals(userRS.toString(), "RS{code='500', msg='服务内部错误，请联系管理员', error=null, data=User{name='John', age=10}}");
            assertEquals(userRS.getCode(), "500");
            assertEquals(userRS.getMsg(), "服务内部错误，请联系管理员");
            assertEquals(data.toString(), "User{name='John', age=10}");
            assertFalse(userRS.success());
            assertTrue(userRS.failed());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test5() {
        RS<Object> ok = RS.ok();
        RS<User> ok1 = RS.<User>ok();
        RS<Object> error = RS.error(StatusCode.DATASOURCE_CONNECTION_FAILURE, "完整的异常栈信息……");
        RS<Object> error1 = RS.error(StatusCode.REQUIRED_PARAMETER_IS_MISSING);

        System.out.println(ok);         // RS{code='0', msg='success!', error=null, data=null}
        System.out.println(ok1);        // RS{code='0', msg='success!', error=null, data=null}
        System.out.println(error);      // RS{code='60100', msg='数据源连接失败', error='完整的异常栈信息……', data=null}
        System.out.println(error1);     // RS{code='10410', msg='请求必填参数为空', error=null, data=null}

        assertEquals(ok.toString(), "RS{code='0', msg='成功！', error=null, data=null}");
        assertEquals(ok1.toString(), "RS{code='0', msg='成功！', error=null, data=null}");
        assertEquals(error.toString(), "RS{code='60100', msg='数据源连接失败', error='完整的异常栈信息……', data=null}");
        assertEquals(error1.toString(), "RS{code='10410', msg='请求必填参数为空', error=null, data=null}");
    }

    @Test
    void test6() {
        User user = new User();
        RS<User> userRS = RS.ok(user);
        System.out.println(userRS);

        assertEquals(userRS.toString(), "RS{code='0', msg='成功！', error=null, data=User{name='null', age=0}}");
    }

    static class User{
        private String name;
        @JsonProperty("USER_AGE")
        private int age;

        public User() {
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    enum StatusCodeExt implements IStatusCode {
        OK("200", "成功！");

        public final String code;
        public final String msg;

        StatusCodeExt(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        @Override
        public String getMsgEn() {
            return "";
        }
    }

}
