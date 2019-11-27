import com.renfei.example.spark.WordCountTask;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordCountTaskTest {
    @Test
    public void test() throws URISyntaxException {
        String inputFile = getClass().getResource("loremipsum.txt").toURI().toString();
        new WordCountTask().run(inputFile);
    }


    @Test
    public void StringTest() throws Exception {
        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(uuid);
        }

    }


    private void testReturn(int i) {
        if (i == 5) {
            return;
        }
    }

    private static String[] getExecs(String filePath, String fileSuffix,
                                     int fileDepth, String isClear) {

        List<String> cmdList = new ArrayList<String>();


        // tar
        StringBuffer tarCmdBuf = new StringBuffer("find ");
        tarCmdBuf.append(filePath);

        String tarCmd = "find " + filePath + " -maxdepth 3 -type f  -name \"*.txt\" -exec tar zcf oldboy.tar.gz {} \\;";

        cmdList.add(tarCmd);

        // scp
        String scpCmd = "";

        cmdList.add(scpCmd);
        // rm
        String rmCmd = "";

        if ("0".equals(isClear)) {
            cmdList.add(rmCmd);
        }

        String[] s = {""};
        return cmdList.toArray(s);
    }

}
