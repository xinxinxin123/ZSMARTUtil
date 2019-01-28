package com.xin.filecopy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.ztesoft.zsmart.core.utils.FileUtil;
import com.ztesoft.zsmart.core.utils.StringUtil;
/**
 * 
 * @author liyuxin
 * 用来将工作空间的文件拷贝到过程库导出的路径
 * 说明：
 * cutString ： 除去"A,U,D"之外的多余的头
 * fromHead：工作空间路径
 * toHead：过程库导出路径
 *
 */
public class FileCopyUtil {
    /**eclipse 工作空间*/
    private static final String fromHead = "D:/idea-space/v8-space/public-cvbs-dev/";
//    private static final String fromHead = "D:/idea-space/v8-space/zimb-mi-new/";
    private static final String toHead = "F:/work-space/cmo-code/1502406/branches/main_branch/";
//    private static final String toHead = "F:/work-space/cmo-code/1494492/branches/main_branch/";
    private static final String cutString = "   branches/main_branch/";
    
    public static void main(String[] args) throws IOException{
        FileReader fr = null;
        BufferedReader br = null;
        
        try {
            //URL fileUrl = FileCopyUtil.class.getClassLoader().getResource("svnlist.txt");
            //1.读取文件
            File svnListFile = new File("F:\\space\\zsmart-util\\ZSMARTUtil\\src\\main\\java\\svnlist.txt");
            fr = new FileReader(svnListFile);
            br = new BufferedReader(fr);
            
            String line = br.readLine(); 
            while (StringUtil.isNotEmpty(line)) {
                if (line.endsWith("/") || line.endsWith(".properties")) {
                    line = br.readLine();
                    continue;
                }
                
                String withoutOpetaionString = line.substring(1, line.length());
                String cutResultString = withoutOpetaionString.replaceAll(cutString, "");
                
                String fromPath = fromHead + cutResultString;
                String toPath = toHead + cutResultString;
                
                File toFile = new File(toPath);
                
                //目标目录，过程库签出的代码不需要此项。应该都存在目录
                /*String todir = toPath.substring(0,toPath.lastIndexOf("/"));
                File dirFile = new File(todir); 
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }*/
                
                String operationCode = line.substring(0, 1);
                if("A".equals(operationCode) || "U".equals(operationCode)){
                    FileUtil.copyFile(new File(fromPath), toFile, true);
                }
                else if("D".equals(operationCode)){
                    boolean deleted = toFile.delete();
                    if(!deleted){
                        System.out.println("删除失败"+toFile.getPath());
                    }
                }
                else {
                    System.out.println("svnlist format error......");
                }
                
                line = br.readLine();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            fr.close();
            br.close();
        }
    }
}
