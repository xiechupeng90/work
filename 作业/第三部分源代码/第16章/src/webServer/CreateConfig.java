package webServer;   

import java.io.File;   
import java.io.FileWriter;   
import java.io.IOException;   
import java.io.Writer;   
import java.util.Iterator;
import javax.swing.JOptionPane;   
import org.dom4j.Document;   
import org.dom4j.DocumentException;   
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   
import org.dom4j.io.SAXReader;   
import org.dom4j.io.XMLWriter;   

public class CreateConfig {

	public static String WEB_ROOT = System.getProperty("user.dir")
			+ File.separator + "webroot";
	public static String PORT;

	public static void main(String a[]) {
		PORT = new JOptionPane()
				.showInputDialog(null, "请输入Web服务器使用的端口号：", "80");
		boolean flag = true;
		char ch = PORT.charAt(0);

		if (ch > '0' && ch <= '9') {
			for (int i = 1; i < PORT.length(); i++) {
				char ch1 = PORT.charAt(i);
				if (ch1 < '0' && ch > '9')
					flag = false;
			}
		} else {
			flag = false;
		}
		if (flag) {
			createXml("web.xml");
			new JOptionPane().showMessageDialog(null, "已成功创建配置文件：web.xml", "",
					0);
		} else {
			new JOptionPane().showMessageDialog(null, "错误的端口号：" + PORT, "", 0);
		}
	}

	private static void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("root");
		Element webroot = root.addElement("webroot");
		Element port = root.addElement("port");
		webroot.setText(WEB_ROOT);
		port.setText(PORT);
		try {
			Writer fileWriter = new FileWriter(fileName);
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}

