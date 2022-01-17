package com.wv.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XmlParseService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void xmlParse(String filePath) {
        try {
            File file = new File("C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml");

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

    public List batchSchedulerParse(String filePath) {

        List scheduleList = new ArrayList<>();

        try {
            // C:\WORLDVISION\JAR\context-batch-scheduler.xml
            File file = new File("C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            LOGGER.info("Root Element :" + document.getDocumentElement().getNodeName());

            NodeList beanNodeList = document.getElementsByTagName("bean");
            LOGGER.info("----------------------------");

            // 트리거를 수정하는 부분은 별도로 작성
            for (int temp = 1; temp < beanNodeList.getLength(); temp++) {
                Node beanNode = beanNodeList.item(temp);
                LOGGER.info("Current Element : " + beanNode.getNodeName());

                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) beanNode;
                    LOGGER.info("id ::: " + eElement.getAttribute("id"));

                    Map scheduleMap = new HashMap<>();
                    scheduleMap.put("triggerName", eElement.getAttribute("id"));

                    NodeList propertyNodeList = beanNode.getChildNodes();
                    Node property1 = propertyNodeList.item(3);
                    Node property2 = propertyNodeList.item(1);
                    try {
                        Element propertyElement1 = (Element) property1;
                        LOGGER.info("name ::: " + propertyElement1.getAttribute("name"));
                        LOGGER.info("value ::: " + propertyElement1.getAttribute("value"));

                        Element propertyElement2 = (Element) property2;
                        LOGGER.info("name ::: " + propertyElement2.getAttribute("name"));
                        LOGGER.info("ref ::: " + propertyElement2.getAttribute("ref"));

                        scheduleMap.put("cronExpression", propertyElement1.getAttribute("value"));
                        scheduleMap.put("jobDetail", propertyElement2.getAttribute("ref"));

                        scheduleList.add(scheduleMap);
                    } catch (NullPointerException | ClassCastException exception) {
                    }
                    System.out.println();
                }
            }
        } catch(IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error(e.toString());
        }
        return scheduleList;
    }
}
