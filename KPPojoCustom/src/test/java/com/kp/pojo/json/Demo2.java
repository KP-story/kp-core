package com.kp.pojo.json;

 import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
 import com.kp.pojo.ProxyBuilderException;

 import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@KPJsonProxy
public class Demo2 {
    int a;
    String b;

    public static Object from(com.fasterxml.jackson.core.JsonParser jsonParser) throws Exception {
        try (JsonParser p = jsonParser) {

             Demo2 bean = new  Demo2();
            while (p.nextToken() != com.fasterxml.jackson.core.JsonToken.END_OBJECT) {
                String name = jsonParser.getCurrentName();
                if (name.equals("b")) {
                    p.nextToken();
                    bean.setB(p.getText());
                } else if (name.equals("a")) {
                    p.nextToken();
                    bean.setA(p.getIntValue());
                }
            }
            return bean;
        }
    }

    public static void main(String[] args) throws Exception {
        String json = "{     \"a\":1,\"b\":\"khanhlv\"   }";
        ObjectMapper objectMapper = new ObjectMapper();
        Demo2 demo = objectMapper.readValue(json, Demo2.class);
        System.out.println(demo);

        JsonProxyRegister jsonProxyRegister = new JsonProxyRegister();
        jsonProxyRegister.register("com");
        for (int j = 10; j < 1000000000; j = j * 10) {
            List<String> result = new LinkedList<>();

            for (int i = 0; i < 10; i++) {
                long function2 = function3(json, j);
                long function1 = function1(json, j);
                result.add("test " + i + " | function1:" + function1 + " , function2:" + function2 + " ,rate: " + (function2 - function1));
            }
            System.out.println("--------------------------");
            System.out.println("result test with loop :" + j);
            for (String r : result) {
                System.out.println(r);


            }
        }

    }

    public static long function1(String json, int loop) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        long timstart = System.currentTimeMillis();

        for (int i = 0; i < loop; i++) {
            Demo2 demo = objectMapper.readValue(json, Demo2.class);
        }
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timstart;
    }

    public static long function2(String json, int loop) throws IOException, IllegalAccessException, ProxyBuilderException, InstantiationException {

        JsonFactory jfactory = new JsonFactory();
        KPDeserializer kpDeserialize = JsonProxyRegister.getDeserialzer(Demo2.class.getName());

        long timstart = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            JsonParser jsonParser = jfactory.createParser(json);
            JsonToken t = jsonParser.getCurrentToken();
            if (t == null) {
                t = jsonParser.nextToken();

            }
            kpDeserialize.from(jsonParser);
        }
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timstart;
    }

    public static long function3(String json, int loop) throws Exception {

        JsonFactory jfactory = new JsonFactory();

        long timstart = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            JsonParser jsonParser = jfactory.createParser(json);
            JsonToken t = jsonParser.getCurrentToken();
            if (t == null) {
                t = jsonParser.nextToken();

            }
            from(jsonParser);
        }
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timstart;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }


}
