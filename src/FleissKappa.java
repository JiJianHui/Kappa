import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ji JianHui
 * Time: 2014-03-21 14:28
 * Email: jhji@ir.hit.edu.cn
 */
public class FleissKappa
{

    /**
     *第一行加载的是标注人数，行数和列数，可以参考Fleiss' kappa 的定义
     *人数代表了标注人员，行数代表了多少个标注文档，列数代表了多少个分类,
     * 详细请看 http://en.wikipedia.org/wiki/Fleiss%27_kappa
     */
    public double calculateFleissKappa(String fPath) throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();

        util.readFileToLines(fPath, lines);

        String[] infoLine = lines.get(0).split("\t");

        int raterNum = Integer.valueOf( infoLine[0] );
        int rowNum   = Integer.valueOf( infoLine[1] );
        int colNum   = Integer.valueOf( infoLine[2] );

        int[][] cells = new int[rowNum][colNum];

        //1: 初始化标注网格，里面存放了每个文档被标注为各个类别的个数
        for(int i = 1; i < rowNum + 1; i++)
        {
            String line    = lines.get(i);
            String[] lists = line.split("\t");

            //计算数据里面暂时保留第一列为标签或者内容
            for(int j = 1; j < lists.length; j++)
            {
                cells[i-1][j-1] = Integer.valueOf( lists[j] );
            }
        }
 
        //2：开始计算pi和pj

        double[] pi = new double[rowNum];
        double[] pj = new double[colNum];

        //2.1: 计算pi
        for(int i = 0; i < rowNum; i++)
        {
            double result = 0.0;

            for(int j = 0; j < colNum; j++)
            {
                int number = cells[i][j];
                result += number * (number - 1);
            }

            pi[i]  = result /( raterNum * (raterNum - 1) );
        }

        //2.2: 计算pj
        for(int j = 0; j < colNum; j++)
        {
            double result = 0.0;

            for( int i = 0; i < rowNum; i++ ) result = result + cells[i][j];

            pj[j] = result / (rowNum * raterNum);
        }

        //3: 计算观察到的一致性概率
        double pa = 0.0;
        double pe = 0.0;

        for( int i = 0; i < rowNum; i++ ) pa = pa + pi[i];
        pa = pa / rowNum;

        //4: 计算巧合一致性概率pe
        for( int j = 0; j < colNum; j++ ) pe = pe + pj[j]*pj[j];

        //5: 计算Kappa值
        double kappa = (pa - pe) / (1 - pe);

        return kappa;

    }

    public static void main(String[] args) throws IOException
    {
        //String dataPath = "E:\\Program\\Java\\Imagination\\Lib Result\\pingjia\\test.txt";

        FleissKappa fleissKappa = new FleissKappa();

        //1: Nor_ie_wikikb 无初值和有初值
        String dataPath = "E:/Program/Java/Imagination/Lib Result/pingjia/wiki_wuchuzhi.txt";
        double kappa    = fleissKappa.calculateFleissKappa(dataPath);
        System.out.println(dataPath + "\r\n" + kappa);


        dataPath = "E:/Program/Java/Imagination/Lib Result/pingjia/wiki_youchuzhi.txt";
        kappa    = fleissKappa.calculateFleissKappa(dataPath);
        System.out.println(dataPath + "\r\n" + kappa);

        //2: Nor_ie_yago 无初值和有初值
        dataPath = "E:/Program/Java/Imagination/Lib Result/pingjia/yago_wuchuzhi.txt";
        kappa    = fleissKappa.calculateFleissKappa(dataPath);
        System.out.println(dataPath + "\r\n" + kappa);


        dataPath = "E:/Program/Java/Imagination/Lib Result/pingjia/yago_youchuzhi.txt";
        kappa    = fleissKappa.calculateFleissKappa(dataPath);
        System.out.println(dataPath + "\r\n" + kappa);

        //3: Nor_ie_Reverb_ie_wikikb 无初值和有初值
        dataPath = "E:/Program/Java/Imagination/Lib Result/pingjia/reverb_wuchuzhi.txt";
        kappa    = fleissKappa.calculateFleissKappa(dataPath);
        System.out.println(dataPath + "\r\n" + kappa);


        dataPath = "E:/Program/Java/Imagination/Lib Result/pingjia/reverb_youchuzhi.txt";
        kappa    = fleissKappa.calculateFleissKappa(dataPath);
        System.out.println(dataPath + "\r\n" + kappa);

    }
}
