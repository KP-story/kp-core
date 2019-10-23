package com.kp.pojo.json;

 import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
 import com.kp.pojo.ProxyBuilderException;

 import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@KPJsonProxy
public class Demo {

    int a;
    String b;
    boolean c;
    DemoA demoA;
    private List<List<Integer>> m;
    private Map<String, String> jt;
    private Map<String, DemoA> jc;
    private List<List<List<DemoB>>> d;

    public Demo() {

    }

    public static void main(String[] args) throws IOException, IllegalAccessException, ProxyBuilderException, InstantiationException, InterruptedException {
        String json = "{ \"jc\":{\"khanh\": {\"t\":1},\"khanh2\":{\"t\":2}} ,       \"jt\":{\"khanh\":\"yen\",\"khanh2\":\"yen\"} ,   \"a\":1,\"b\":\"khanhlv\",\"c\":true,\"demoA\":{\"t\":12},\"m\":[[1,2],[2,3]],\"d\":[[[{\"b\":1},{\"b\":2}]]]}";
        ObjectMapper objectMapper = new ObjectMapper();
        Demo demo = objectMapper.readValue(json, Demo.class);
        System.out.println(demo);

        JsonProxyRegister jsonProxyRegister = new JsonProxyRegister();
        jsonProxyRegister.register("com");
        for (int j = 10; j < 1000000000; j = j * 10) {
            List<String> result = new LinkedList<>();

            for (int i = 0; i < 10; i++) {
                long function2 = function2(json, j);
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
            Demo demo = objectMapper.readValue(json, Demo.class);
        }
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timstart;
    }

    public static long function2(String json, int loop) throws IOException, IllegalAccessException, ProxyBuilderException, InstantiationException {

        JsonFactory jfactory = new JsonFactory();
        KPDeserializer kpDeserialize = JsonProxyRegister.getDeserialzer(Demo.class.getName());

        long timstart = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            JsonParser jsonParser = jfactory.createParser(json);
            try (JsonParser p = jsonParser) {

                JsonToken t = p.getCurrentToken();
                if (t == null) {
                    t = p.nextToken();

                }
                kpDeserialize.from(p);
            }
        }
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timstart;
    }

    public Map<String, DemoA> getJc() {
        return jc;
    }

    public void setJc(Map<String, DemoA> jc) {
        this.jc = jc;
    }

    public Map<String, String> getJt() {
        return jt;
    }

    public void setJt(Map<String, String> jt) {
        this.jt = jt;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "m=" + m +
                ", a=" + a +
                ", b='" + b + '\'' +
                ", c=" + c +
                ", jt=" + jt +
                ", jc=" + jc +
                ", demoA=" + demoA +
                ", d=" + d +
                '}';
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public DemoA getDemoA() {
        return demoA;
    }

    public void setDemoA(DemoA demoA) {
        this.demoA = demoA;
    }

    public List<List<List<DemoB>>> getD() {
        return d;
    }

    public void setD(List<List<List<DemoB>>> d) {
        this.d = d;
    }

    public List<List<Integer>> getM() {
        return m;
    }

    public void setM(List<List<Integer>> m) {
        this.m = m;
    }


}
