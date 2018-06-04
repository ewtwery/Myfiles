package com.xm.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import android.content.Context;

public class MyXmlOperator {
	Context context;
    public MyXmlOperator(Context context) {
        this.context = context;
    }

    //将字符串保存为apk的私有文件
    public boolean writeToXml(String filename, String content) {
        try {

            OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);//,MODE_PRIVATE
            OutputStreamWriter outWriter = new OutputStreamWriter(out);
            try {
                outWriter.write(content);
                outWriter.flush();
                outWriter.close();
                out.close();
                return true;
            } catch (IOException e) {
                // TODO: handle exception
                System.out.print(e);
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.print(e);
            return false;
        }
    }


    public NodeList ReadFromXml(String fileName, String tagName)
    {
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document;
        NodeList nodeList;
        try {
            documentBuilderFactory=DocumentBuilderFactory.newInstance();
            documentBuilder=documentBuilderFactory.newDocumentBuilder();
            FileInputStream inputStream=context.openFileInput(fileName);
            document=documentBuilder.parse(inputStream);
            org.w3c.dom.Element root= document.getDocumentElement();
            nodeList = root.getElementsByTagName(tagName);
        } catch (Exception e) {
            return null;
        }finally{
            document=null;
            documentBuilder=null;
            documentBuilderFactory=null;
        }
        return nodeList;
    }
}
