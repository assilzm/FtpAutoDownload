import ftp.FtpBase
import org.apache.log4j.Logger
import utils.LogUtils

/**
 *
 * @author: weiwei
 * create: 2016-9-14 18:25.
 * description:
 */
class RunUpdate {
    private static Logger logger = LogUtils.getLogger(RunUpdate)


    public final static void main(String[] args) {
        String host = System.getenv("ftpHost")
        String userName = System.getenv("ftpUserName")
        String password = System.getenv("ftpPassword")
        String dirPath = System.getenv("ftpDirPath")
        String localPath = System.getenv("localPath")
        System.out.println("host:$host")
        System.out.println("userName:$userName")
        System.out.println("password:$password")
        System.out.println("dirPath:$dirPath")
        System.out.println("localPath:$localPath")

        if (host && userName && password && dirPath && localPath) {
            FtpBase ftpBase = new FtpBase(host, userName, password)
            ftpBase.login()
            ftpBase.downLoadDirectory(localPath, dirPath)
        } else {
            throw new Exception("请确保所有参数已经设置。")
        }
    }
}
