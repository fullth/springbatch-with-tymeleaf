package com.wv.monitoring.service;

import com.wv.monitoring.repository.batch.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XmlParser {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** 기본 템플릿 */
    public void xmlParse(String filePath) {
        try {
            File file = new File(filePath);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            LOGGER.info("Root Element :" + document.getDocumentElement().getNodeName());

            NodeList beanNodeList = document.getElementsByTagName("bean");
            LOGGER.info("----------------------------");

        } catch(IOException | ParserConfigurationException | SAXException e) {
           LOGGER.error(e.toString());
        }
    }

    /** 각 노드의 값을 파싱해야 할 때 */
    private String getTagValue(String sTag, Element element) {
        try {
            String result = element.getElementsByTagName(sTag).item(0).getTextContent();
            return result;
        } catch (NullPointerException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /** 스케줄러 리스트 파싱 */
    public List batchSchedulerParse(String filePath) {

        List scheduleList = new ArrayList<>();

        try {
            File file = new File(filePath);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList beanNodeList = document.getElementsByTagName("bean");

            for (int i = 1; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);

                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) beanNode;

                    Map scheduleMap = new HashMap<>();
                    scheduleMap.put("triggerName", eElement.getAttribute("id"));

                    NodeList propertyNodeList = beanNode.getChildNodes();
                    Node property1 = propertyNodeList.item(3);
                    Node property2 = propertyNodeList.item(1);
                    try {
                        Element propertyElement1 = (Element) property1;
                        Element propertyElement2 = (Element) property2;

                        scheduleMap.put("idx", i);
                        scheduleMap.put("cronExpression", propertyElement1.getAttribute("value"));
                        scheduleMap.put("jobDetail", propertyElement2.getAttribute("ref"));

                        scheduleList.add(scheduleMap);
                    } catch (NullPointerException | ClassCastException e) {
                        LOGGER.error("ERROR getAttribute()::: " + e.getMessage());
                    }
                }
            }
        } catch(IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error(e.getMessage());
        }
        return scheduleList;
    }

    /**
     * @MethodName : updateSchedule
     * @Parameter: String triggerName, String updatedCronExpression
     *             변경할 트리거의 이름, 변경할 스케줄
     * */
    public void updateSchedule(String triggerName, String updatedCronExpression) throws Exception {

        String filePath = "C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml";

        // xml을 파싱하여 dom객체를 생성한다.
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(filePath));

        // 특정 노드를 찾기 위해 XPath를 이용한다.
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate("//bean[@id='" + triggerName + "']",
                document, XPathConstants.NODESET);

        // 전달받은 트리거의 스케줄을 변경한다.
        if(nodes.getLength() != 0) {
            try {
                Node value = nodes.item(0);

                NodeList propertyNodeList = value.getChildNodes();
                Node property1 = propertyNodeList.item(3);

                Element propertyElement1 = (Element) property1;

                String cronExpression = propertyElement1.getAttribute("value");
                LOGGER.info("업데이트 전 스케줄 ::: " + cronExpression);

                propertyElement1.setAttribute("value", updatedCronExpression);
                cronExpression = propertyElement1.getAttribute("value");
                LOGGER.info("업데이트 후 스케줄 ::: " + cronExpression);

                Transformer xformer = TransformerFactory.newInstance().newTransformer();
                xformer.transform
                        (new DOMSource(document), new StreamResult(new File(filePath)));
            } catch (Exception e) {
                LOGGER.error("Error occurred during updateSchedule() ::: " + e);
            }
        }
    }

    /** 트리거 리스트 파싱 */
    public List batchTriggerParse(String filePath) {

        List<Trigger> triggerList = new ArrayList<>();

        try {
            File file = new File(filePath);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            // 최상위 bean tag
            NodeList beanNodeList = document.getElementsByTagName("bean");
            Node beanNode = beanNodeList.item(0);
            // property tag
            NodeList propertyNodeList = beanNode.getChildNodes();
            Node propertyNode = propertyNodeList.item(1);
            // list tag
            NodeList listNodeList = propertyNode.getChildNodes();
            Node listNode = listNodeList.item(1);
            // ref tag list
            NodeList refNodeList = listNode.getChildNodes();

            int j = 1;
            for (int i = 0; i < refNodeList.getLength(); i++) {

                Node node = refNodeList.item(i);

                /**
                 * 공백을 텍스트로 인식하기 때문에 ELEMENT_NODE 해당 타입일 경우에만 속성을 가져옴
                 * 텍스트 타입은 Node.TEXT_NODE => #text
                 * */
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    Trigger trigger = new Trigger();

                    trigger.setIdx(j);
                    trigger.setTriggerName(element.getAttribute("bean"));

                    triggerList.add(trigger);
                    j++;
                }
            }

        } catch(IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error(e.getMessage());
        }
        return triggerList;
    }
}
