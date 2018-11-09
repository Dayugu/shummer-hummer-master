package com.hummer.browser.util;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KillServer {
    private static Logger logger = LoggerFactory.getLogger(KillServer.class);

    private Set<Integer> ports = Sets.newHashSet();
    private boolean isLinux;

    public KillServer(Integer port) {
        this.ports.add(port);
        this.isLinux = isOSLinux();
    }

    public KillServer(Set<Integer> ports) {
        this.ports = ports;
        this.isLinux = isOSLinux();
    }

    /**
     * 杀死端口号对应的进程
     */
    public void kill() {
        Runtime runtime = Runtime.getRuntime();
        for (Integer port : this.ports) {
            logger.info("【KillServer执行器】:starting kill，port: " + port);
            if (null == port) continue;
            try {
                //查找进程号
                Process p;
                String exec_com;
                if (isLinux) exec_com = "lsof -i:" + port + "";
                else exec_com = "cmd /c netstat -ano | findstr \"" + port + "\"";
                logger.info("【KillServer执行器】:开始执行命令: " + exec_com);
                p = runtime.exec(exec_com);
                InputStream inputStream = p.getInputStream();
                List<String> read = read(inputStream, "UTF-8");
                if (read.size() == 0) {
                    logger.info("【KillServer执行器】:未找到端口号: " + port + " 对应的进程");
                    break;
                } else {
                    logger.info("【KillServer执行器】:命令执行返回数据:");
                    for (String string : read) {
                        logger.info(string);
                    }
                    logger.info("【KillServer执行器】:找到 " + read.size() + " 个进程，正在清理");
                    duplicateRemove(read, isLinux);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 验证此行是否为指定的端口，因为 findstr命令会是把包含的找出来，例如查找80端口，但是会把8099查找出来
     *
     * @param str
     * @return
     */
    private boolean validPort(String str) {
        Pattern pattern = Pattern.compile("^ *[a-zA-Z]+ +\\S+");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        String find = matcher.group();
        int spstart = find.lastIndexOf(":");
        find = find.substring(spstart + 1);
        int port;
        try {
            port = Integer.parseInt(find);
        } catch (NumberFormatException e) {
            logger.info("【KillServer执行器】:错误的端口号:" + find);
            return false;
        }
        return this.ports.contains(port);
    }

    /**
     * 更换为一个Set，去掉重复的pid值
     *
     * @param data
     */
    private void duplicateRemove(List<String> data, boolean isLinux) {
        Set<Integer> pids = new HashSet<>();
        for (String line : data) {
            String[] split = line.split(" ");
            String spid;
            if (isLinux) spid = split[1];
            else spid = split[split.length - 1];
            if (StringUtils.isBlank(spid)) continue;
            int pid;
            try {
                pid = Integer.parseInt(spid);
            } catch (NumberFormatException e) {
                logger.info("【KillServer执行器】:进程pid错误:" + spid);
                continue;
            }
            pids.add(pid);
        }
        killWithPid(pids, isLinux);
    }

    /**
     * 一次性杀除所有的端口
     *
     * @param pids
     */
    private void killWithPid(Set<Integer> pids, boolean isLinux) {
        for (Integer pid : pids) {
            try {
                Process process;
                String exe_com;
                if (isLinux) {
                    exe_com = "kill -9 " + pid + "";
                    process = Runtime.getRuntime().exec(exe_com);
                } else {
                    exe_com = "taskkill /F /pid " + pid + "";
                    process = Runtime.getRuntime().exec(exe_com);
                }
                logger.info("【KillServer执行器】:开始执行命令: " + exe_com);
                InputStream inputStream = process.getInputStream();
                //String txt = readTxt(inputStream, "GBK");
                logger.info("【KillServer执行器】:成功: 终止 PID 为 " + pid + " 的进程");
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                logger.info("【KillServer执行器】:失败: 终止 PID 为 " + pid + " 的进程");
            }
        }
    }


    private List<String> read(InputStream in, String charset) throws IOException {
        List<String> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("COMMAND")) continue;
            if (!isLinux) {
                boolean validPort = validPort(line);
                if (validPort) {
                    data.add(line);
                }
            } else {
                data.add(line);
            }
        }
        reader.close();
        return data;
    }

    private String readTxt(InputStream in, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    public static boolean isOSLinux() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1)
            return true;
        return false;
    }

    /*public static void main(String[] args) throws InterruptedException {

        PhantomJSDriver browser = new PhantomJSDriver();
        new KillServer(browser.getServerPort()).kill();
        Thread.sleep(5000);
        System.exit(0);

    }*/
}