package test;

import main.Application;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class LambdaTests {

    Application application = new Application();

    @Before
    public void init() {
        application.loadRecords();
    }

    @Test
    public void checkSQL01() {
        assertEquals(1000000l, application.executeSQL01());

    }

    @Test
    public void checkSQL02() {
        assertEquals(5213l, application.executeSQL02());

    }

    @Test
    public void checkSQL03() {
        assertEquals(10345l, application.executeSQL03());

    }

    @Test
    public void checkSQL04() {
        assertEquals(52046l, application.executeSQL04());

    }

    @Test
    public void checkSQL05() {
        List<Integer> list = new ArrayList<>();             //Andere Werte wie in PDF
        list.add(5886);
        list.add(18218);
        list.add(148562);
        assertEquals(list, application.executeSQL05());

    }

    @Test
    public void checkSQL06() {
        List<Integer> list = new ArrayList<>();
        list.add(114);
        list.add(484);
        list.add(423);
        list.add(74);
        list.add(465);
        list.add(404);
        assertEquals(list, application.executeSQL06());

    }

    @Test
    public void checkSQL07() {
        Map<String, Long> map = new TreeMap<>();
        map.put("minor", 333431l);
        map.put("major", 333762l);
        map.put("critical", 332807l);
        assertEquals(map, application.executeSQL07());
    }

    @Test
    public void checkSQL08() {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(3, 10625l);
        map.put(1, 10390l);
        map.put(2, 10609l);
        map.put(4, 10464l);
        assertEquals(map, application.executeSQL08());
    }

    @Test
    public void checkSQL09() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("a", 31295l);
        map.put("c", 31061l);
        map.put("b", 31319l);
        assertEquals(map, application.executeSQL09());
    }

    @Test
    public void checkSQL10() {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(1, 30322l);
        map.put(2, 30433l);
        map.put(4, 30458l);
        map.put(3, 30306l);
        assertEquals(map, application.executeSQL10());

    }

    @Test
    public void checkSQL11() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("h", 1898305);
        map.put("c", 1928173);
        map.put("f", 1903616);
        map.put("a", 1918444);
        map.put("g", 1914268);
        assertEquals(map, application.executeSQL11());
    }

    @Test
    public void checkSQL12() {
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("c", 122);
        expected.put("b", 123);
        expected.put("a", 122);
        HashMap<String, Integer> actual = castAverageToInteger(application.executeSQL12());
        assertEquals(expected,actual);

    }

    private HashMap<String, Integer> castAverageToInteger(Map<String, Double> map) {

        HashMap<String, Integer> newMap = new HashMap<>();
        newMap.put("c", map.get("c").intValue());
        newMap.put("b", map.get("b").intValue());
        newMap.put("a", map.get("a").intValue());
        return newMap;
}


}
