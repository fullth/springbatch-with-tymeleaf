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

    private static final String SCHEDULER_FILE_PATH = "C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml";

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

    /** 스케쥴러 리스트 파싱 */
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

    /** xml을 파싱하여 dom객체를 생성한다. */
    public Document createDocumentObject(String filePath) throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(filePath));

        return document;
    }

    /** XPath를 이용하여 특정 노드 탐색한다. */
    public Node searchNodeList(String expression, Document document) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(expression,
                document, XPathConstants.NODESET);
        LOGGER.info(">>> 리스트 사이즈 : " + nodes.getLength());
        Node node = nodes.item(0);

        return node;
    }

    /** xml 데이터 작성 */
    public void xmlDataTransform(Document document) throws TransformerException {
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform
                (new DOMSource(document), new StreamResult(new File(SCHEDULER_FILE_PATH)));
    }

    /**
     * 스케쥴 업데이트
     * */
    public void updateSchedule(String triggerName, String updatedCronExpression) throws Exception {

        Document document = createDocumentObject(SCHEDULER_FILE_PATH);

        // "//bean[@id='" + triggerName + "']"
        Node nodes = searchNodeList("//bean[@id='" + triggerName + "']", document);

        // 전달받은 트리거의 스케줄을 변경한다.
        if(nodes != null) {
            try {

                NodeList propertyNodeList = nodes.getChildNodes();
                Node property1 = propertyNodeList.item(3);

                Element propertyElement1 = (Element) property1;

                String cronExpression = propertyElement1.getAttribute("value");
                LOGGER.info("업데이트 전 스케줄 ::: " + cronExpression);

                propertyElement1.setAttribute("value", updatedCronExpression);
                cronExpression = propertyElement1.getAttribute("value");
                LOGGER.info("업데이트 후 스케줄 ::: " + cronExpression);

                xmlDataTransform(document);
            } catch (Exception e) {
                LOGGER.error("Error occurred during updateSchedule() ::: " + e);
            }
        }
    }

    /** 트리거 리스트 파싱 */
    public List batchTriggerParse(String filePath) throws Exception {

        List<Trigger> triggerList = new ArrayList<>();

        try {
            File file = new File(filePath);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            // 최상위 bean tag TODO 자식노드 탐색 메소드화
            Node beanNodeList = searchNodeList("//bean", document);
            
            // property tag
            NodeList propertyNodeList = beanNodeList.getChildNodes();
            Node propertyNode = propertyNodeList.item(1);
            // list tag
            NodeList listNodeList = propertyNode.getChildNodes();
            Node listNode = listNodeList.item(1);
            // ref tag list
            NodeList refNodeList = listNode.getChildNodes();

            int j = 1;
            for (int i = 0; i < refNodeList.getLength(); i++) {

                Node node = refNodeList.item(i);

                /** 공백을 텍스트로 인식하기 때문에 ELEMENT_NODE 해당 타입일 경우에만 속성을 가져옴
                 *  텍스트 타입은 Node.TEXT_NODE => #text */
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

    /** 트리거 추가 */
    public void addTrigger(Trigger trigger) throws Exception {
        String triggerName = trigger.getTriggerName();

        Document document = createDocumentObject(SCHEDULER_FILE_PATH);

        Node listNode = searchNodeList("//list", document);
        
        // 정렬을 위한 들여쓰기 및 개행
        Node indentNode = document.createTextNode("\t");
        Node indentNode2 = document.createTextNode("\t\t\t");
        Node newLineNode = document.createTextNode("\n");

        Node triggerNode = document.createElement("ref");

        Element element = (Element) triggerNode;
        element.setAttribute("bean", triggerName);

        listNode.appendChild(indentNode);
        listNode.appendChild(triggerNode);
        listNode.appendChild(newLineNode);
        listNode.appendChild(indentNode2);

        xmlDataTransform(document);
    }

    /** 트리거 업데이트 */
    public void updateTrigger(Trigger trigger) throws Exception {
        int beforeUpdateTriggerIdx = trigger.getIdx();
        String expectedUpdateTriggerName = trigger.getTriggerName();

        Document document = createDocumentObject(SCHEDULER_FILE_PATH);

        Node refNode = searchNodeList("//ref[" + beforeUpdateTriggerIdx + "]", document);

        Element element = (Element) refNode;
        element.setAttribute("bean", expectedUpdateTriggerName);

        xmlDataTransform(document);
    }

    /** 트리거 삭제 */
    public void deleteTrigger(Trigger trigger) throws Exception {
        int beforeUpdateTriggerIdx = trigger.getIdx();

        Document document = createDocumentObject(SCHEDULER_FILE_PATH);

        Node listNode = searchNodeList("//list", document);
        Node refNode = searchNodeList("//ref[" + beforeUpdateTriggerIdx + "]", document);

        listNode.removeChild(refNode);

        xmlDataTransform(document);
    }
}
