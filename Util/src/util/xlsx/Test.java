/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package util.xlsx;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">���</a>
 * 2014��12��5������2:10:46
 * @version 1.0
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class Test {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // ��ѹBook1.xlsx
        ZipFile xlsxFile;
        try {
            xlsxFile = new ZipFile(new File("d:\\11.xlsx"));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 
            // �ȶ�ȡsharedStrings.xml����ļ�����
            ZipEntry sharedStringXML = xlsxFile.getEntry("xl/sharedStrings.xml");
            InputStream sharedStringXMLIS = xlsxFile
                    .getInputStream(sharedStringXML);
            Document sharedString;
            sharedString = dbf.newDocumentBuilder().parse(sharedStringXMLIS);
            NodeList str = sharedString.getElementsByTagName("t");
            String sharedStrings[] = new String[str.getLength()];
            for (int n = 0; n < str.getLength(); n++) {
                Element element = (Element) str.item(n);
                sharedStrings[n] = element.getTextContent();
            }
            // �ҵ���ѹ�ļ������workbook.xml,���ļ��а��������Ź��������м���sheet
            ZipEntry workbookXML = xlsxFile.getEntry("xl/workbook.xml");
            InputStream workbookXMLIS = xlsxFile.getInputStream(workbookXML);
            Document doc = dbf.newDocumentBuilder().parse(workbookXMLIS);
            // ��ȡһ���м���sheet
            NodeList nl = doc.getElementsByTagName("sheet");
 
            for (int i = 0; i < nl.getLength(); i++) {
                Element element = (Element) nl.item(i);// ��nodeת��Ϊelement�������õ�ÿ���ڵ������
                System.out.println(element.getAttribute("name"));// ���sheet�ڵ��name���Ե�ֵ
                // ���ž�Ҫ����ѹ�ļ������ҵ���Ӧ��nameֵ��xml�ļ���������workbook.xml����<sheet name="Sheet1"
                // sheetId="1" r:id="rId1" /> �ڵ�
                // ��ô�Ϳ����ڽ�ѹ�ļ������xl/worksheets���ҵ�sheet1.xml,���xml�ļ�������ǰ����ı�������
                ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/"
                        + element.getAttribute("name").toLowerCase() + ".xml");
                InputStream sheetXMLIS = xlsxFile.getInputStream(sheetXML);
                Document sheetdoc = dbf.newDocumentBuilder().parse(sheetXMLIS);
                NodeList rowdata = sheetdoc.getElementsByTagName("row");
                for (int j = 0; j < rowdata.getLength(); j++) {
                    // �õ�ÿ����
                    // �еĸ�ʽ��
                    /*
                     * <row r="1" spans="1:3">r��ʾ��һ��,spans��ʾ�м��� <c r="A1"
                     * t="s">//r��ʾ���е��б�
                     * ��t="s"������Ϊ�Ǳ�ʾ�����Ԫ������ݿ�����sharedStrings.xml����ļ����ҵ�����Ӧ�Ľڵ�
                     * �±����v�ڵ��ֵ����0����û��t���ԣ���v��ֵ���Ǹõ�Ԫ������� <v>0</v> </c> <c r="B1"
                     * t="s"> <v>1</v> </c> <c r="C1" t="s"> <v>2</v> </c> </row>
                     */
                    Element row = (Element) rowdata.item(j);
                    // �����еõ�ÿ�����е���
                    NodeList columndata = row.getElementsByTagName("c");
                    for (int k = 0; k < columndata.getLength(); k++) {
                        Element column = (Element) columndata.item(k);
                        NodeList values = column.getElementsByTagName("v");
                        Element value = (Element) values.item(0);
                        if (column.getAttribute("t") != null
                                & column.getAttribute("t").equals("s")) {
                            // ����ǹ����ַ�������sharedstring.xml����Ҹ��е�ֵ
 
                            System.out.print(sharedStrings[Integer.parseInt(value.getTextContent())] + " ");
                        } else {
                            if (value != null) {
                                System.out.print(value.getTextContent() + " ");
                            }else {
                                System.out.println("j : " + j + "   k : " + k + "  null");
                            }
                        }
                    }
                    System.out.println();
                }
            }
        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
    }
 
}
