import com.renfei.example.spark.WordCountTask;
import org.junit.Test;

import java.net.URISyntaxException;

public class WordCountTaskTest {
    @Test
    public void test() throws URISyntaxException {
        String inputFile = getClass().getResource("loremipsum.txt").toURI().toString();
        new WordCountTask().run(inputFile);
    }


    @Test
    public void StringTest() {
        String[] strings = {"0", "1"};
        try {

            String s = strings[2];
        } catch (IndexOutOfBoundsException ie) {
            System.out.print(" 1 IndexOutOfBoundsException");
        } catch (Exception ie) {
            System.out.print(" 2 Exception");
        } finally {
            System.out.print(" 3 Over");
        }


    }

}
