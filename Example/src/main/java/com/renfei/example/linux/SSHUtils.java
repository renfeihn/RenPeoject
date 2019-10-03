package com.renfei.example.linux;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

public class SSHUtils {
	private String charset = null;
	private String ip = null;
	private String user = null;
	private String password = null;
	private Connection conn = null;
	private Session session = null;

	public SSHUtils(String ipaddr, String user, String password) {
		this.ip = ipaddr;
		this.user = user;
		this.password = password;
		this.charset = Charset.defaultCharset().toString();
	}

	public void SSHexec(String[] cmd) throws IOException {
		if (this.StartConn()) {
			for (int i = 0; i < cmd.length; i++) {
				this.exeCmd(cmd[i]);
			}
			this.connClose();
		}
	}

	public String exeCmd(String cmd) throws IOException {
		String rs = null;
		if (this.StartConn()){
	        InputStream input = null;
	        session = conn.openSession();
	        session.execCommand(cmd);
	        input = session.getStdout();
	        rs = processStdout(input, charset);
	        session.close();
		}
        return rs;
    }

	private String processStdout(InputStream input, String charset) {
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (input.read(buf) != -1) {
                sb.append(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

	private boolean StartConn() {
		boolean succeed = false;
		try {
			conn = new Connection(ip);
			conn.connect();
			succeed = conn.authenticateWithPassword(user, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return succeed;
	}

	private void connClose() {
		conn.close();
	}
	
	/**
	 * 功能说明：从本地复制文件到远程目录 
	 * @param localFile
	 * @param remoteDirectory
	 */
	public void put(String localFile, String remoteDirectory){
		try {
			if (this.StartConn()){
				SCPClient scpClient = conn.createSCPClient();
		        scpClient.put(localFile, remoteDirectory); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
	
	/**
	 * 功能说明：从远程获取文件 
	 * @param remoteFile
	 * @param localDirectory
	 */
	public void get(String remoteFile, String localDirectory){
		try {
			if (this.StartConn()){
				SCPClient scpClient = conn.createSCPClient();
		        scpClient.get(remoteFile, localDirectory); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
	
	public static void main(String[] args) throws IOException {
		SSHUtils ssh = new SSHUtils("10.9.3.1", "aim","aim");
		ssh.get("","");
	}
}
