package cn.slibs.test;

import cn.slibs.base.map.SOHashMap;
import cn.slibs.base.map.SOMap;
import cn.slibs.base.IStatusCode;
import cn.slibs.base.RS;
import cn.slibs.base.StatusCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iofairy.except.ConditionsNotMetException;
import com.iofairy.falcon.time.DateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

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

    @SneakyThrows
    @Test
    void test1() {
        System.out.println("=============================test1===========================");
        RS<?> ok = RS.ok();
        System.out.println(MAPPER.writeValueAsString(ok));      // {"code":"0","msg":"成功！","error":null,"data":null}
        System.out.println(ok);                                 // RS{code='0', msg='成功！', error=null, data=null}

        assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"success\":true,\"data\":null,\"error\":null}");
        assertEquals(ok.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=null}");

        System.out.println("==================================");
        ok = RS.ok(DateTime.parseDate("2024-01-01 00:51:44").get());
        System.out.println(MAPPER.writeValueAsString(ok));      // {"code":"0","msg":"成功！","error":null,"data":"2022-10-09 16:27:07"}
        System.out.println(ok);                                 // RS{code='0', msg='成功！', error=null, data=Sun Oct 09 16:27:07 CST 2022}
        System.out.println("是否成功：" + ok.success);          // 是否成功：true

        assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"success\":true,\"data\":\"2024-01-01 00:51:44\",\"error\":null}");
        assertEquals(ok.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=Mon Jan 01 00:51:44 CST 2024}");
        assertTrue(ok.success);

        System.out.println("==================================");
        RS.setDefaultSuccessStatusCode(StatusCodeExt.OK);

        System.out.println("是否成功：" + ok.success);          // 是否成功：false
        RS<?> newOk = RS.ok();
        System.out.println(MAPPER.writeValueAsString(newOk));   // {"code":"200","msg":"成功！","error":null,"data":null}
        System.out.println(newOk);                              // RS{code='200', msg='成功！', error=null, data=null}
        System.out.println("是否成功：" + newOk.success);       // 是否成功：true

        assertEquals(MAPPER.writeValueAsString(newOk), "{\"code\":\"200\",\"msg\":\"成功！\",\"success\":true,\"data\":null,\"error\":null}");
        assertEquals(newOk.toString(), "RS{code='200', msg='成功！', success=true, error=null, data=null}");
        assertTrue(newOk.success);

        RS.setDefaultSuccessStatusCode(StatusCode.OK);
    }

    @SneakyThrows
    @Test
    void test2() {
        System.out.println("=============================test2===========================");
        RS<?> error = RS.fail(StatusCode.INTERNAL_SERVER_ERROR);
        RS<?> ok = RS.ok();
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

        System.out.println(MAPPER.writeValueAsString(error));
        System.out.println(error);

        assertEquals(MAPPER.writeValueAsString(error), "{\"code\":\"500\",\"msg\":\"服务内部错误，请联系管理员\",\"success\":false,\"data\":null,\"error\":null}");
        assertEquals(error.toString(), "RS{code='500', msg='服务内部错误，请联系管理员', success=false, error=null, data=null}");

        System.out.println("==================================");
        ok = RS.ok(soHashMap);
        System.out.println(MAPPER.writeValueAsString(ok));
        System.out.println(ok);

        assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"success\":true,\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOHashMap_hellod\",\"createTime\":\"2024-01-01 01:09:12\",\"e\":\"SOHashMap_helloe\"},\"error\":null}");
        assertEquals(ok.toString(), "RS{code='0', msg='成功！', success=true, error=null, data={a=helloa, b=hellob, d=SOHashMap_hellod, createTime=Mon Jan 01 01:09:12 CST 2024, e=SOHashMap_helloe}}");

        System.out.println("==================================");
        ok = RS.ok(soMap);
        System.out.println(MAPPER.writeValueAsString(ok));
        System.out.println(ok);

        assertEquals(MAPPER.writeValueAsString(ok), "{\"code\":\"0\",\"msg\":\"成功！\",\"success\":true,\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOMap_hellod\",\"createTime\":\"2024-01-01 01:09:12\",\"e\":\"SOMap_helloe\",\"map\":{\"mapkey1\":\"value1\"}},\"error\":null}");
        assertEquals(ok.toString(), "RS{code='0', msg='成功！', success=true, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=Mon Jan 01 01:09:12 CST 2024, e=SOMap_helloe, map={mapkey1=value1}}}");

    }

    @SneakyThrows
    @Test
    void testJsonAndRSExchange1() {
        String json = "{\"code\":\"0\",\"msg\":\"success!\",\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOMap_hellod\"," +
                "\"createTime\":\"2022-08-03 09:47:52\",\"e\":\"SOMap_helloe\",\"map\":{\"mapkey1\":\"value1\"}}}";
        /*
         readValue过程：
         1、先创建一个 RS<SOMap> 的对象
         2、再利用反射对这个对象的字段进行赋值
         可能导致的问题：
         创建 RS<SOMap> 对象时，调用的是无参的构造函数，无参构造函数默认是设置“成功”的状态码。导致 success 字段为 true，如果 json中没有包含 success 字段，
         则 success 不会被改变，一直为 true。
         解决：
         第一种：调用 adjust() 方法进行校正
         第二种：json串中带上 success 字段
         */
        RS<SOMap> soMapRS = MAPPER.readValue(json, new TypeReference<RS<SOMap>>() {
        });
        System.out.println(soMapRS);
        assertEquals(soMapRS.toString(), "RS{code='0', msg='success!', success=true, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");
        RS<SOMap> newRs = soMapRS.adjust();
        assertEquals(newRs.toString(), "RS{code='0', msg='success!', success=true, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");

        json = "{\"code\":\"400\",\"msg\":\"失败!\",\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOMap_hellod\"," +
                "\"createTime\":\"2022-08-03 09:47:52\",\"e\":\"SOMap_helloe\",\"map\":{\"mapkey1\":\"value1\"}}}";
        soMapRS = MAPPER.readValue(json, new TypeReference<RS<SOMap>>() {
        });
        System.out.println(soMapRS);
        assertEquals(soMapRS.toString(), "RS{code='400', msg='失败!', success=true, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");
        newRs = soMapRS.adjust();
        assertEquals(newRs.toString(), "RS{code='400', msg='失败!', success=false, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");
        RS<SOMap> adjust = RS.adjust(soMapRS);
        assertEquals(adjust.toString(), "RS{code='400', msg='失败!', success=false, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");


        json = "{\"code\":\"400\",\"msg\":\"失败!\",\"success\":false,\"data\":{\"a\":\"helloa\",\"b\":\"hellob\",\"d\":\"SOMap_hellod\"," +
                "\"createTime\":\"2022-08-03 09:47:52\",\"e\":\"SOMap_helloe\",\"map\":{\"mapkey1\":\"value1\"}}}";
        soMapRS = MAPPER.readValue(json, new TypeReference<RS<SOMap>>() {
        });
        System.out.println(soMapRS);
        assertEquals(soMapRS.toString(), "RS{code='400', msg='失败!', success=false, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");

        System.out.println("code: " + soMapRS.getCode());
        System.out.println("msg: " + soMapRS.getMsg());
        System.out.println("data: " + soMapRS.getData());
        System.out.println("success: " + soMapRS.success);

        assertEquals(soMapRS.getCode(), "400");
        assertEquals(soMapRS.getMsg(), "失败!");
        assertEquals(soMapRS.getData().toString(), "{a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}");
        assertFalse(soMapRS.success);

        JsonNode jsonNode = MAPPER.readTree(json);
        RS<SOMap> soMapRS1 = MAPPER.convertValue(jsonNode, new TypeReference<RS<SOMap>>() {
        });
        System.out.println(soMapRS1);
        assertEquals(soMapRS1.toString(), "RS{code='400', msg='失败!', success=false, error=null, data={a=helloa, b=hellob, d=SOMap_hellod, createTime=2022-08-03 09:47:52, e=SOMap_helloe, map={mapkey1=value1}}}");

    }

    @SneakyThrows
    @Test
    void testJsonAndRSExchange2() {
        RS<User> rs = RS.error(StatusCode.INTERNAL_SERVER_ERROR, new User("John", 10));

        String json1 = MAPPER.writeValueAsString(rs);
        System.out.println("RS to Json: " + json1);

        assertEquals(json1, "{\"code\":\"500\",\"msg\":\"服务内部错误，请联系管理员\",\"success\":false,\"data\":{\"name\":\"John\",\"USER_AGE\":10},\"error\":null}");
        RS<User> userRS = MAPPER.readValue(json1, new TypeReference<RS<User>>() {
        });
        System.out.println("json to RS: " + userRS);
        System.out.println("code: " + userRS.getCode());
        System.out.println("msg: " + userRS.getMsg());
        User data = userRS.getData();
        System.out.println("data: " + data);

        assertEquals(userRS.toString(), "RS{code='500', msg='服务内部错误，请联系管理员', success=false, error=null, data=User{name='John', age=10}}");
        assertEquals(userRS.getCode(), "500");
        assertEquals(userRS.getMsg(), "服务内部错误，请联系管理员");
        assertEquals(data.toString(), "User{name='John', age=10}");
        assertFalse(userRS.success);

        RS<User> ok = RS.ok(new User("John", 10));
        json1 = MAPPER.writeValueAsString(ok);
        System.out.println("RS to Json: " + json1);

        assertEquals(json1, "{\"code\":\"0\",\"msg\":\"成功！\",\"success\":true,\"data\":{\"name\":\"John\",\"USER_AGE\":10},\"error\":null}");
        userRS = MAPPER.readValue(json1, new TypeReference<RS<User>>() {
        });
        System.out.println("json to RS: " + userRS);
        System.out.println("code: " + userRS.getCode());
        System.out.println("msg: " + userRS.getMsg());

        assertEquals(userRS.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=User{name='John', age=10}}");
        assertEquals(userRS.getCode(), "0");
        assertEquals(userRS.getMsg(), "成功！");
        assertEquals(data.toString(), "User{name='John', age=10}");
        assertTrue(userRS.success);
    }

    @SneakyThrows
    @Test
    void test5() {
        RS<?> ok = RS.ok();
        RS<?> ok1 = RS.ok();
        RS<?> error = RS.fail(StatusCode.DATASOURCE_CONNECTION_FAILURE, "完整的异常栈信息……");
        RS<?> error1 = RS.fail(StatusCode.REQUIRED_PARAMETER_IS_MISSING);

        System.out.println(ok);         // RS{code='0', msg='success!', error=null, data=null}
        System.out.println(ok1);        // RS{code='0', msg='success!', error=null, data=null}
        System.out.println(error);      // RS{code='60100', msg='数据源连接失败', error='完整的异常栈信息……', data=null}
        System.out.println(error1);     // RS{code='10410', msg='请求必填参数为空', error=null, data=null}

        assertEquals(ok.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=null}");
        assertEquals(ok1.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=null}");
        assertEquals(error.toString(), "RS{code='60100', msg='数据源连接失败', success=false, error=null, data=完整的异常栈信息……}");
        assertEquals(error1.toString(), "RS{code='10410', msg='请求必填参数为空', success=false, error=null, data=null}");
    }

    @SneakyThrows
    @Test
    void test6() {
        User user = new User();
        RS<User> userRS = RS.ok(user);
        System.out.println(userRS);

        assertEquals(userRS.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=User{name='null', age=0}}");

        RS.setDefaultEnglish(true);
        userRS = RS.ok(user);
        System.out.println(userRS);
        assertEquals(userRS.toString(), "RS{code='0', msg='OK!', success=true, error=null, data=User{name='null', age=0}}");

        RS<?> error = RS.fail(StatusCode.EMAIL_VERIFI_CODE_IS_INCORRECT);
        System.out.println(error);
        assertEquals(error.toString(), "RS{code='10132', msg='Email Verifi Code Is Incorrect', success=false, error=null, data=null}");

        System.out.println(RS.isDefaultEnglish());
        System.out.println(RS.getDefaultSuccessStatusCode());
        System.out.println(RS.getDefaultErrorStatusCode());
        assertTrue(RS.isDefaultEnglish());
        assertEquals("OK", RS.getDefaultSuccessStatusCode().toString());
        assertEquals("INTERNAL_SERVER_ERROR", RS.getDefaultErrorStatusCode().toString());

        RS.setDefaultEnglish(false);
    }

    @SneakyThrows
    @Test
    void test7() {
        User user = new User("张三", 20);
        RS<User> rs = RS.ok(user);
        assertEquals(rs.toString(), "RS{code='0', msg='成功！', success=true, error=null, data=User{name='张三', age=20}}");
        System.out.println(rs);
        RS<?> rs1 = RS.fail("请求错误！").setError("异常信息");
        assertEquals(rs1.toString(), "RS{code='500', msg='请求错误！', success=false, error='异常信息', data=null}");
        System.out.println(rs1);
        RS<User> rs2 = RS.error("没有找到该用户！", new User()).setError("异常信息");
        String rs2Str = MAPPER.writeValueAsString(rs2);
        assertEquals(rs2Str, "{\"code\":\"500\",\"msg\":\"没有找到该用户！\",\"success\":false,\"data\":{\"name\":null,\"USER_AGE\":0},\"error\":\"异常信息\"}");
        System.out.println(rs2Str);
    }

    @SneakyThrows
    @Test
    void test8() {
        RS<?> fail = RS.fail();
        System.out.println(MAPPER.writeValueAsString(fail));
    }

    @Test
    void testException() {
        try {
            RS<String> ok = RS.ok("", "");
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertSame(e.getClass(), ConditionsNotMetException.class);
            assertEquals(e.getMessage(), "None of these parameters [code, msg] can be blank! ");
        }

    }

    static class User {
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
