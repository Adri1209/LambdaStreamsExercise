
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Application {
    private List<Record> records = new ArrayList<>();
    private List<Record> recordcopy;

    public List<Record> loadRecords() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Configuration.instance.records_archive));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(";");
                records.add(new Record(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2]), strings[3], strings[4], Integer.parseInt(strings[5]), Integer.parseInt(strings[6])));
            }
            recordcopy = new ArrayList<>(records);
            return records;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    @Test
    public void checkSizeOfList() {
        Application application = new Application();
        application.loadRecords();
        assertEquals(1000000, records.size());
    }


    // count
    public void executeSQL01() {

        long count = records.stream().count();
        System.out.println(count);
        System.out.println();
    }

    // count, where
    public void executeSQL02() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s -> s.getSeverity().equals("major") && s.getAttackType().equals("e") && s.getSource() <= 2 && s.getShift() == 4;
        long count = records.stream().filter(filter).count();
        System.out.println(count);
        System.out.println();

    }

    // count, where, in
    public void executeSQL03() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s -> (s.getSeverity().equals("major") || s.getSeverity().equals("critical")) && s.getAttackType().equals("b") && s.getSource() == 4 && s.getShift() >= 3;
        long count = records.stream().filter(filter).count();
        System.out.println(count);
        System.out.println();
    }

    // count, where, not in
    public void executeSQL04() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s -> s.getSeverity().equals("critical") && !(s.getAttackType().equals("b") || s.getAttackType().equals("c") || s.getAttackType().equals("g")) && s.getSource() > 2 && s.getShift() <= 2;
        long count = records.stream().filter(filter).count();
        System.out.println(count);
        System.out.println();
    }

    // id, where, in, order by desc limit
    public void executeSQL05() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s -> s.getSeverity().equals("minor") && (s.getAttackType().equals("a") || s.getAttackType().equals("b")) && s.getSource() == 1 && s.getShift() == 4 && s.getDowntimeInMinutes() >= 195;
        Comparator<Record> descending = (Record record01, Record record02) -> (int) (record02.getDowntimeInMinutes() - record01.getDowntimeInMinutes());
        List<Record> record = records.stream().filter(filter).collect(Collectors.toList());
        Collections.sort(record, descending);
        List<Integer> ids = record.stream().limit(3).map(x -> x.getId()).collect(Collectors.toList());
        ids.forEach(System.out::println);
        System.out.println();
    }

    // id, where, in, order by desc, order by asc
    public void executeSQL06() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s -> (s.getSeverity().equals("minor") || s.getSeverity().equals("major")) && s.getAttackType().equals("c") && s.getSource() == 2 && s.getShift() == 1 && s.getId() <= 500;
        Comparator<Record> comparator = (Record record01, Record record02) -> (int) (record02.getSeverity().compareTo(record01.getSeverity()));
        comparator = comparator.thenComparing(Comparator.comparing(record -> record.getDowntimeInMinutes()));
        List<Record> record = records.stream().filter(filter).collect(Collectors.toList());
        Collections.sort(record, comparator);
        List<Integer> ids = record.stream().map(x -> x.getId()).collect(Collectors.toList());
        ids.forEach(System.out::println);
        System.out.println();

    }

    // count, group by
    public void executeSQL07() {
        records = new ArrayList<>(recordcopy);

        Map<String, Long> result = records.stream().collect(Collectors.groupingBy(Record::getSeverity, Collectors.counting()));

        System.out.println(result);
        System.out.println();

    }

    // count, where, group by
    public void executeSQL08() {
        records = new ArrayList<>(recordcopy);

        Map<Integer, Long> result = records.stream().filter(s -> s.getAttackType().equals("d") && s.getSeverity().equals("major"))
                .collect(Collectors.groupingBy(Record::getShift, Collectors.counting()));

        System.out.println(result);
        System.out.println();
    }

    // count, where, in, group by
    public void executeSQL09() {
        records = new ArrayList<>(recordcopy);

        Map<String, Long> result = records.stream().filter(s -> (s.getAttackType().equals("a") || s.getAttackType().equals("b") || s.getAttackType().equals("c")) && s.getSource() == 3)
                .collect(Collectors.groupingBy(Record::getAttackType, Collectors.counting()));

        System.out.println(result);
        System.out.println();
    }

    // count, where, not in, group by
    public void executeSQL10() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s-> !(s.getAttackType().equals("b") || s.getAttackType().equals("d") || s.getAttackType().equals("e")) && s.getShift() >= 2 && s.getDowntimeInMinutes() >= 30 && s.getDowntimeInMinutes() <= 90;
        Map<Integer, Long> result = records.stream().filter(filter)
                .collect(Collectors.groupingBy(Record::getSource, Collectors.counting()));

        System.out.println(result);
        System.out.println();
    }

    // sum, where, not in, in, group by
    public void executeSQL11() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s-> !(s.getAttackType().equals("b") || s.getAttackType().equals("d") || s.getAttackType().equals("e")) && s.getShift() == 1 && (s.getSource() == 1 || s.getSource() == 3);
        Map<String, Integer> result = records.stream().filter(filter)
                .collect(Collectors.groupingBy(r -> r.getAttackType(), Collectors.summingInt(d -> d.getDowntimeInMinutes())));

        System.out.println(result);
        System.out.println();
    }

    // avg, where, in, in, group by
    public void executeSQL12() {
        records = new ArrayList<>(recordcopy);

        Predicate<Record> filter = s-> (s.getSeverity().equals("minor") || s.getSeverity().equals("major")) && (s.getAttackType().equals("a") || s.getAttackType().equals("b") || s.getAttackType().equals("c")) && s.getSource() == 1 && s.getShift() >= 3;
        Map<String, Double> result = records.stream().filter(filter)
                .collect(Collectors.groupingBy(r -> r.getAttackType(), Collectors.averagingInt(d -> d.getDowntimeInMinutes())));

        System.out.println(result);
        System.out.println();
    }

    public void execute() {
        loadRecords();
        executeSQL01();
        executeSQL02();
        executeSQL03();
        executeSQL04();
        executeSQL05();
        executeSQL06();
        executeSQL07();
        executeSQL08();
        executeSQL09();
        executeSQL10();
        executeSQL11();
        executeSQL12();
    }

    public static void main(String... args) {
        Application application = new Application();
        application.execute();

    }
}