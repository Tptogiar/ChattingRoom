package client.model.util;






import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.HashMap;


/**
 * @author Tptogiar
 * @Descripiton: 字体管理
 * @creat 2021/05/15-12:37
 */


public class FontMgr {

    public static HashMap<String,String> tipFontMap=new HashMap();

    static {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("src\\resources\\language\\Chinese.xml");
            NodeList tip = document.getElementsByTagName("tip");
            for (int i = 0; i < tip.getLength(); i++) {
                if(tip.item(i).getNodeType()==Node.ELEMENT_NODE){
                    NodeList childNodes = tip.item(i).getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE){
                            Element item = (Element)childNodes.item(j);
                            String english_text = item.getAttributes().getNamedItem("English_Text").getTextContent();
                            String textContent = item.getTextContent();
                            tipFontMap.put(english_text,textContent);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
