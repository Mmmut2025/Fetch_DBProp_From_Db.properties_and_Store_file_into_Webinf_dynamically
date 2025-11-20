package com.doubts.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;

public class GetDBProp extends SelectorComposer<Component>{
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		File dpFile = new File(Sessions.getCurrent().getWebApp().getResource("/WEB-INF/resources/db.properties").getFile());
		
		
		//frist way
		FileReader fr = new FileReader(dpFile);
		char[] data = new char[100];
		fr.read(data);
		System.out.println(new String(data));
		
		//second way
		FileInputStream is = new FileInputStream(dpFile);
		Properties prop = new Properties();
		prop.load(is);
		System.out.println(prop.get("dbname"));
		System.out.println(prop.get("dbpass"));

	}
}
