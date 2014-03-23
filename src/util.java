import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ji JianHui
 * Time: 2014-03-21 14:40
 * Email: jhji@ir.hit.edu.cn
 */
public class util
{
    /**
     * 获取指定文件夹下的所有文件，并将结果添加到files列表中。
     * @param dir：标注文件路径
     * @param files: 需要更新的文件列表，是在原有的基础上修改。这样递归就起作用啦.
     * @param ending:指定的文件后缀。必须指定，若设置为null,则用来获取所有的文件。
     * @return
     */
    public static void getFiles(String dir, ArrayList<String> files, String ending)
    {
        File temp   = null;
        File   parent = new File(dir);
        File[] lists  = parent.listFiles();

        for(int i = 0; i < lists.length; i++)
        {
            temp = lists[i];

            if( temp.isFile() )
            {
                if( temp.getName().endsWith(ending) )
                {
                    files.add( temp.getAbsolutePath() );
                }
            }
            else if( temp.isDirectory() )
            {
                util.getFiles( temp.getAbsolutePath(), files, ending );
            }
        }
    }

    /**
     * 将一个文件读成按行存储的vector
     * @param lines
     * @throws java.io.IOException
     */
    public static void readFileToLines(String fPath, ArrayList<String> lines) throws IOException
    {
        BufferedReader br = new BufferedReader( new FileReader(fPath) );

        String line = null;

        while( ( line = br.readLine() ) != null )
        {
            line = line.trim();
            lines.add(line);
        }

        br.close();
    }

    public static void writeLinesToFile(String fPath, ArrayList<String> lines) throws IOException
    {
        FileWriter fw = new FileWriter( new File(fPath) );

        for(String line : lines)
        {
            fw.write(line + "\r\n");
        }

        fw.close();
    }
}
