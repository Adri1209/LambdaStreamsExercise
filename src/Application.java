import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

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

        Predicate<Record> filter = s -> s.getSeverity().equals("minor") && (s.getAttackType().equals("a") || s.getAttackType().equals("b")) && s.getSource() ==1 && s.getShift() == 4 && s.getDowntimeInMinutes() >= 195;
        Comparator<Record> descending = (Record record01, Record record02) -> (int) (record02.getDowntimeInMinutes() - record01.getDowntimeInMinutes());
        List<Record> record= records.stream().filter(filter).collect(Collectors.toList());
        Collections.sort(record,descending);
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
        Collections.sort(record,comparator);
        List<Integer> ids = record.stream().map(x -> x.getId()).collect(Collectors.toList());
        ids.forEach(System.out::println);
        System.out.println();

    }

    // count, group by
    public void executeSQL07() {
    }

    // count, where, group by
    public void executeSQL08() {
    }

    // count, where, in, group by
    public void executeSQL09() {
    }

    // count, where, not in, group by
    public void executeSQL10() {
    }

    // sum, where, not in, in, group by
    public void executeSQL11() {
    }

    // avg, where, in, in, group by
    public void executeSQL12() {
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