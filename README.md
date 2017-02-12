#  commons-net的用法
### 这几天用到了一个第三方FTP的jar包 这里用的版本是commons-net-3.3

### 下面我们分为两部分来查看 上传和下载
##
## 先来看上传
### 上传的用法很简单 
### 第一步:先创建 FTPClient对象 并初始化

```
/**
  * FTP连接
  */
  private FTPClient ftpClient;
  
   /**
     * 构造函数.通过构造 在调用方传过来域名 用户名 和密码
     *
     * @param host hostName 服务器名
     * @param user userName 用户名
     * @param pass password 密码
     */
    public FTPManager(String host, String user, String pass) {
        this.hostName = host;
        this.userName = user;
        this.password = pass;
        this.ftpClient = new FTPClient();
        this.list = new ArrayList<FTPFile>();
    }
  ```
  ## 第二步 打开连接
  ```
  /**
     * 打开FTP服务.
     *
     * @throws IOException
     */
    public void openConnect() throws IOException {
        // 中文转码
        ftpClient.setControlEncoding("UTF-8");
        int reply; // 服务器响应值
        // 连接至服务器
        ftpClient.connect(hostName);
        // 获取响应值
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        }
        // 登录到服务器
        ftpClient.login(userName, password);
        // 获取响应值
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        } else {
            // 获取登录信息
            FTPClientConfig config = new FTPClientConfig(ftpClient.getSystemType().split(" ")[0]);
            config.setServerLanguageCode("zh");
            ftpClient.configure(config);
            // 使用被动模式设为默认
            ftpClient.enterLocalPassiveMode();
            // 二进制文件支持
            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            System.out.println("login");
        }
    }

  
```

## 第三步 上传
```
 /**
     * 上传.
     *
     * @param localFile  本地文件
     * @param remotePath FTP目录
     * @return ResultBean 返回实体类对象
     * @throws IOException
     */
    public ResultBean uploading(File localFile, String remotePath, String ext) throws IOException {
        this.ext = ext;
        boolean flag = true;
        ResultBean resultBean = null;
        // 初始化FTP当前目录
        currentPath = remotePath;
        // 初始化当前流量
        response = 0;
        // 二进制文件支持
        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        // 使用被动模式设为默认
        ftpClient.enterLocalPassiveMode();
        // 设置模式
        ftpClient.setFileTransferMode(org.apache.commons.net.ftp.FTP.STREAM_TRANSFER_MODE);
        // 改变FTP目录
        ftpClient.changeWorkingDirectory(REMOTE_PATH);
        // 获取上传前时间
        Date startTime = new Date();
        if (localFile.isDirectory()) {
            // 上传多个文件
            flag = uploadingMany(localFile);
        } else {
            // 上传单个文件
            flag = uploadingSingle(localFile);
        }
        // 获取上传后时间
        Date endTime = new Date();
        // 返回值
        resultBean = new ResultBean(flag, MyUtils.getFormatTime(endTime.getTime() - startTime.getTime()), MyUtils.getFormatSize(response));
        return resultBean;
    }

```

## 上传源码分析
```

    protected boolean _storeFile(String command, String remote, InputStream local) throws IOException {
        //底层其实就是用socket传递
        Socket socket = this._openDataConnection_(command, remote);
        if(socket == null) {
            return false;
        } else {
            //一会再强转过来
            Object output = this.getBufferedOutputStream(socket.getOutputStream());
            if(this.__fileType == 0) {
                //这里主要让其支持中文
                output = new ToNetASCIIOutputStream((OutputStream)output);
            }

            FTPClient.CSL csl = null;
            if(this.__controlKeepAliveTimeout > 0L) {
                csl = new FTPClient.CSL(this, this.__controlKeepAliveTimeout, this.__controlKeepAliveReplyTimeout);
            }

            try {
                //核心上传代码在此里面
                Util.copyStream(local, (OutputStream)output, this.getBufferSize(), -1L, this.__mergeListeners(csl), false);
            } catch (IOException var8) {
                Util.closeQuietly(socket);
                if(csl != null) {
                    csl.cleanUp();
                }

                throw var8;
            }

            ((OutputStream)output).close();
            socket.close();
            if(csl != null) {
                csl.cleanUp();
            }

            boolean ok = this.completePendingCommand();
            return ok;
        }
    }

```
### 里面的copyStream是上传的核心代码
```
public static final long copyStream(InputStream source, OutputStream dest, int bufferSize, long streamSize, CopyStreamListener listener, boolean flush) throws CopyStreamException {
        //文件的大小
        long total = 0L;
        //每次上传的长度
        byte[] buffer = new byte[bufferSize >= 0?bufferSize:1024];

        try {
            int bytes;
            while((bytes = source.read(buffer)) != -1) {
                if(bytes == 0) {//这里做了封装 一次读取完怎么办
                    bytes = source.read();
                    if(bytes < 0) {
                        break;
                    }
                    //写到服务器
                    dest.write(bytes);
                    if(flush) {
                        dest.flush();
                    }

                    ++total;
                    //这里有一个回调监听 所以开发者在使用的此回调时候可以得到上传的进度
                    if(listener != null) {
                        listener.bytesTransferred(total, 1, streamSize);
                    }
                } else {
                    //写到服务器
                    dest.write(buffer, 0, bytes);
                    if(flush) {
                        dest.flush();
                    }

                    total += (long)bytes;
                    if(listener != null) {
                        listener.bytesTransferred(total, bytes, streamSize);
                    }
                }
            }

            return total;
        } catch (IOException var12) {
            throw new CopyStreamException("IOException caught while copying.", total, var12);
        }
    }
```
## 但是不知道大家有没有发现一个问题 并没有线程同步 所以再使用的时候注意加上线程同步






