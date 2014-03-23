import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ji JianHui
 * Time: 2014-03-21 14:30
 * Email: jhji@ir.hit.edu.cn
 */
public class PreProcess
{
    private String jjhDir;

    private ArrayList<String> jjhFiles;
    public LinkedHashMap<String, Integer[]> scores;

    public static String baseLineEnding     = ".baseline_overlap_3";
    public static String triplesWeightEnding = ".triplesWeight_3";


    public PreProcess()
    {
    }

    public void run(String jjhDir, String saveDataPath) throws IOException {

        this.jjhDir   = jjhDir;
        this.jjhFiles = new ArrayList<String>();
        this.scores   = new LinkedHashMap<String, Integer[]>();

        util.getFiles(this.jjhDir, this.jjhFiles, triplesWeightEnding);

        for(String jjhfPath:this.jjhFiles)
        {
            String zhangfPath = jjhfPath.replace("jhji", "myzhang");
            String zhengfPath = jjhfPath.replace("jhji", "mzheng");

            this.updateScore(jjhfPath);
            this.updateScore(zhangfPath);
            this.updateScore(zhengfPath);
        }

        this.saveData(saveDataPath);

    }

    public void updateScore(String fPath) throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();
        util.readFileToLines(fPath, lines);

        for(int index = 0; index < 20; index++)
        {
            String[] lists = lines.get(index).split("\t");

            int       score  = 0;
            Integer[] number = null;
            String    triple = lists[1];

            if( lists.length > 4 ) score = Integer.valueOf( lists[4] );

            if( this.scores.containsKey(triple) ) number = this.scores.get(triple);

            if( number == null) number = new Integer[]{0, 0};
            number[score] = number[score] + 1;

            this.scores.put( triple, number );
        }
    }

    private void saveData(String fPath) throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();

        //标注人数 + 行数 + 列数
        String infoLine = "3\t" + this.scores.size() + "\t2";
        lines.add(infoLine);

        for( Map.Entry<String, Integer[]> entry : this.scores.entrySet() )
        {
            String line = entry.getKey() + "\t" + entry.getValue()[0] + "\t" + entry.getValue()[1];
            lines.add(line);
        }

        util.writeLinesToFile(fPath, lines);
    }

    public  static void main(String[] args) throws IOException
    {
        PreProcess process = new PreProcess();

        //1: Nor_ie_wikikb 无初值和有初值
        String jjhDir    = "E:/Program/Java/Imagination/Lib Result/pingjia/pingjia_jhji/Nor_ie_wikikb/wuchuzhi";
        String savefPath = "E:/Program/Java/Imagination/Lib Result/pingjia/wiki_wuchuzhi.txt";
        process.run(jjhDir, savefPath);

        jjhDir    = "E:/Program/Java/Imagination/Lib Result/pingjia/pingjia_jhji/Nor_ie_wikikb/youchuzhi";
        savefPath = "E:/Program/Java/Imagination/Lib Result/pingjia/wiki_youchuzhi.txt";
        process.run(jjhDir, savefPath);


        //2: Nor_ie_yago 无初值和有初值
        jjhDir    = "E:/Program/Java/Imagination/Lib Result/pingjia/pingjia_jhji/Nor_ie_yago/wuchuzhi";
        savefPath = "E:/Program/Java/Imagination/Lib Result/pingjia/yago_wuchuzhi.txt";
        process.run(jjhDir, savefPath);

        jjhDir    = "E:/Program/Java/Imagination/Lib Result/pingjia/pingjia_jhji/Nor_ie_yago/youchuzhi";
        savefPath = "E:/Program/Java/Imagination/Lib Result/pingjia/yago_youchuzhi.txt";
        process.run(jjhDir, savefPath);

        //3: Nor_ie_Reverb_ie_wikikb 无初值和有初值
        jjhDir    = "E:/Program/Java/Imagination/Lib Result/pingjia/pingjia_jhji/Reverb_ie_wikikb/wuchuzhi";
        savefPath = "E:/Program/Java/Imagination/Lib Result/pingjia/reverb_wuchuzhi.txt";
        process.run(jjhDir, savefPath);

        jjhDir    = "E:/Program/Java/Imagination/Lib Result/pingjia/pingjia_jhji/Reverb_ie_wikikb/youchuzhi";
        savefPath = "E:/Program/Java/Imagination/Lib Result/pingjia/reverb_youchuzhi.txt";
        process.run(jjhDir, savefPath);
    }
}
