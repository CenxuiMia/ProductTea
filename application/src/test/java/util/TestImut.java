package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestImut {

    @Test
    public void testM() {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(5);
        list.add(2);
        list.add(1);
        list.add(4);

        List<Integer> list1 = Collections.unmodifiableList(list);
//        Collections.sort(list1);
        Collections.sort(list);
    }
}
