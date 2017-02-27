package ftp

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPClientConfig
import org.apache.commons.net.ftp.FTPFile
import org.apache.commons.net.ftp.FTPReply

import org.apache.log4j.Logger
import utils.LogUtils

public class FtpBase {
    private FTPClient ftpClient
    private String strIp
    private int intPort
    private String user
    private String password

    private static Logger logger = LogUtils.getLogger(FtpBase)

    /* * 
     * Ftp构造函数 
     */

    public FtpBase(String strIp, int intPort = 21, String user, String Password) {
        this.strIp = strIp
        this.intPort = intPort
        this.user = user
        this.password = Password
        this.ftpClient = new FTPClient()
    }

    public boolean login() {
        config(FTPClientConfig.SYST_UNIX)
        if (this.ftpClient.getStatus().contains("Microsoft FTP Service")) {
            this.ftpClient.disconnect()
            config(FTPClientConfig.SYST_NT)
        }

    }

    /**
     * @return 判断是否登入成功
     * */
    public boolean config(String system) {

        boolean isLogin = false
        FTPClientConfig ftpClientConfig = new FTPClientConfig(system)
        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID())
        this.ftpClient.setControlEncoding("UTF-8")
        this.ftpClient.configure(ftpClientConfig)
        try {
            if (this.intPort > 0) {
                this.ftpClient.connect(this.strIp, this.intPort)
            } else {
                this.ftpClient.connect(this.strIp)
            }
            // FTP服务器连接回答  
            int reply = this.ftpClient.getReplyCode()
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.ftpClient.disconnect()
                logger.error("登录FTP服务失败！")
                return isLogin
            }
            this.ftpClient.enterLocalPassiveMode()
            this.ftpClient.login(this.user, this.password)
            // 设置传输协议  
            this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)
            logger.info(this.user + "成功登陆FTP服务器")
            logger.info("服务器信息：" + this.ftpClient.getStatus())
            isLogin = true
        } catch (Exception e) {
            e.printStackTrace()
            logger.error(this.user + "登录FTP服务失败！" + e.getMessage())
        }
        this.ftpClient.setBufferSize(1024 * 2)
        this.ftpClient.setDataTimeout(30 * 1000)
        return isLogin
    }

    /**
     * @退出关闭服务器链接
     * */
    public void logout() {
        if (null != this.ftpClient && this.ftpClient.isConnected()) {
            try {
                boolean reuslt = this.ftpClient.logout()// 退出FTP服务器  
                if (reuslt) {
                    logger.info("成功退出服务器")
                }
            } catch (IOException e) {
                e.printStackTrace()
                logger.warn("退出FTP服务器异常！" + e.getMessage())
            } finally {
                try {
                    this.ftpClient.disconnect()// 关闭FTP服务器的连接  
                } catch (IOException e) {
                    e.printStackTrace()
                    logger.warn("关闭FTP服务器的连接异常！")
                }
            }
        }
    }

    /*** 
     * 上传Ftp文件 
     * @param localFile 当地文件 
     * @param romotUpLoadePath上传服务器路径 - 应该以/结束 
     * */
    public boolean uploadFile(File localFile, String romotUpLoadePath) {
        BufferedInputStream inStream = null
        boolean success = false
        try {
            this.ftpClient.changeWorkingDirectory(romotUpLoadePath)// 改变工作路径  
            inStream = new BufferedInputStream(new FileInputStream(localFile))
            logger.info(localFile.getName() + "开始上传.....")
            success = this.ftpClient.storeFile(localFile.getName(), inStream)
            if (success) {
                logger.info(localFile.getName() + "上传成功")
                return success
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace()
            logger.error(localFile + "未找到")
        } catch (IOException e) {
            e.printStackTrace()
        } finally {
            if (inStream != null) {
                try {
                    inStream.close()
                } catch (IOException e) {
                    e.printStackTrace()
                }
            }
        }
        return success
    }

    /*** 
     * 下载文件 
     * @param remoteFileName 待下载文件名称 
     * @param localDirPath 下载到当地那个路径下
     * @param remoteDownLoadPath remoteFileName所在的路径 
     * */
    public boolean downloadFile(String remoteFileName, String localDirPath,
                                String remoteDownLoadPath) {
        if (!localDirPath)
            localDirPath = new File("").getAbsolutePath()
        File file = new File(localDirPath, remoteFileName)
        println file.getAbsolutePath()
        BufferedOutputStream outStream = null
        boolean success = false
        try {
            this.ftpClient.changeWorkingDirectory(remoteDownLoadPath)
            outStream = new BufferedOutputStream(new FileOutputStream(
                    file))
            logger.info(remoteFileName + "开始下载....")
            success = this.ftpClient.retrieveFile(remoteFileName, outStream)
            if (success) {
                logger.info(remoteFileName + "成功下载到" + file)
                return success
            }
        } catch (Exception e) {
            e.printStackTrace()
            logger.error(remoteFileName + "下载失败")
        } finally {
            if (null != outStream) {
                try {
                    outStream.flush()
                    outStream.close()
                } catch (IOException e) {
                    e.printStackTrace()
                }
            }
        }
        if (!success) {
            logger.error(remoteFileName + "下载失败!!!")
        }
        return success
    }

    /*** 
     * @上传文件夹
     * @param localDirectory
     *            当地文件夹 
     * @param remoteDirectoryPath
     *            Ftp 服务器路径 以目录"/"结束 
     * */
    public boolean uploadDirectory(String localDirectory,
                                   String remoteDirectoryPath) {
        File src = new File(localDirectory)
        try {
            remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/"
            this.ftpClient.makeDirectory(remoteDirectoryPath)
            // ftpClient.listDirectories()  
        } catch (IOException e) {
            e.printStackTrace()
            logger.info(remoteDirectoryPath + "目录创建失败")
        }
        File[] allFile = src.listFiles()
        for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
            if (!allFile[currentFile].isDirectory()) {
                String srcName = allFile[currentFile].getPath().toString()
                uploadFile(new File(srcName), remoteDirectoryPath)
            }
        }
        for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
            if (allFile[currentFile].isDirectory()) {
                // 递归  
                uploadDirectory(allFile[currentFile].getPath().toString(),
                        remoteDirectoryPath)
            }
        }
        return true
    }

    /*** 
     * @下载文件夹
     * @param localDirectoryPath本地地址
     * @param remoteDirectory 远程文件夹 
     * */

    public boolean downLoadDirectory(String localDirectoryPath, String remoteDirectory) {
        try {
            String fileName = new File(remoteDirectory).getName()
            localDirectoryPath = localDirectoryPath + "/" + fileName
            File localDirectory = new File(localDirectoryPath)
            localDirectory.mkdirs()
            logger.debug("准备更新目录${remoteDirectory}到${localDirectory.getAbsolutePath()}")
            ftpClient.changeWorkingDirectory(remoteDirectory)
            FTPFile[] allFile = this.ftpClient.listFiles()
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
                File localFile = new File(localDirectory, allFile[currentFile].getName())
                if (!allFile[currentFile].isDirectory()) {
                    if (localFile.exists()) {
//                        if (allFile[currentFile].size == localFile.size()) {
//                            logger.debug("文件${remoteDirectory + "/" + allFile[currentFile].getName()}没有变动，跳过。")
//                            continue
//                        } else
                            localFile.delete()
                    }
                    logger.debug("更新文件${remoteDirectory + "/" + allFile[currentFile].getName()}")
                    downloadFile(allFile[currentFile].getName(), localDirectoryPath, remoteDirectory)
                }
            }
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
                if (allFile[currentFile].isDirectory()) {
                    downLoadDirectory(localDirectoryPath, allFile[currentFile].getName())
                    ftpClient.changeToParentDirectory()


                }
            }

        } catch (IOException e) {
            e.printStackTrace()
            logger.info("下载文件夹失败")
            return false
        }
        return true
    }
    // FtpClient的Set 和 Get 函数  
    public FTPClient getFtpClient() {
        return ftpClient
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient
    }

}  