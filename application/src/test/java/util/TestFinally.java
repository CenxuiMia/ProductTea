package util;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestFinally {

    @Test
    public void testFinal() {
        Executor executor = Executors.newFixedThreadPool(4);
        executor.execute(() -> {
            System.out.println(foo());
        });


    }

    private String foo() {
        try {
            System.out.println("start foo");
            return "Foo";
        }finally {
            System.out.println("finally");
        }
    }
}
