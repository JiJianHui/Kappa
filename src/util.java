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
     * ��ȡָ���ļ����µ������ļ������������ӵ�files�б��С�
     * @param dir����ע�ļ�·��
     * @param files: ��Ҫ���µ��ļ��б�����ԭ�еĻ������޸ġ������ݹ����������.
     * @param ending:ָ�����ļ���׺������ָ����������Ϊnull,��������ȡ���е��ļ���
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
     * ��һ���ļ����ɰ��д洢��vector
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
