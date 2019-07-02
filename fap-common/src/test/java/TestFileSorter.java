import eon.hg.fap.common.util.tools.FileSorter;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class TestFileSorter {

    // for test
    public static void main(String[] args) {

        File[] list = new File("/usr").listFiles();
        Arrays.sort(list, new FileSorter(FileSorter.TYPE_SIZE_UP));
        printFileArray(list);
    }

    // for test
    private static void printFileArray(File[] list) {

        System.out.println("文件大小\t\t文件修改日期\t\t文件类型\t\t文件名称");

        for (File f : list) {
            System.out.println(f.length() + "\t\t"
                    + new Date(f.lastModified()).toString() + "\t\t"
                    + (f.isDirectory() ? "目录" : "文件") + "\t\t" + f.getName());
        }
    }

}
