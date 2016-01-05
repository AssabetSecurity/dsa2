package com.assabetsecurity.core.tools.ebay;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.StringReader;

import java.io.InputStream;

import java.io.FileInputStream;






import java.io.IOException;

import java.util.logging.Logger;







/**
 * Created by george on 1/4/16.
 */
public class EBayDescriptionPage {

    private final String descriptionHTML = "http://vi.vipr.ebaydesc.com/ws/eBayISAPI.dll?item=";

    Logger log = Logger.getLogger(EBayDescriptionPage.class.getName());

    private String requestString;


    public EBayDescriptionPage ( String requestString) {
        this.requestString = requestString;
    }

    public String getAsString() throws IOException {
        String responseString ="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestString);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        try {
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity);

        } finally {
            response.close();
            log.info("Response is closed");
            log.info(" -----------------  String------");
            log.info(responseString);
        }

        return responseString;
    }


    public String getAsXML() throws IOException, ParserConfigurationException, SAXException  {

        String out ="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestString);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        Document document = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder        builder = factory.newDocumentBuilder();


        try {
            HttpEntity entity = response.getEntity();
            if (entity != null){
                InputStream inputStream = entity.getContent();
                document = builder.parse(inputStream);
                NodeList list = document.getElementsByTagName("item");
                log.info("nodes for this request : " + String.valueOf(list.getLength()));

            }
        } finally {
            response.close();
        }

        NodeList nl = document.getElementsByTagName("item");

        String finalString = "";

        int nlLength = nl.getLength();
        log.info(String.valueOf(nlLength));

        for (int i =0; i<nlLength;i++){

            Element el = (Element) nl.item(i);


            String itmId = el.getElementsByTagName("itemId").item(0).getTextContent();

            String name = el.getElementsByTagName("title").item(0).getTextContent();

            String desc =descriptionHTML + itmId;


            finalString+=createXML(itmId,name,desc);


        }
        log.info("---------------- Links---------------");
        log.info(finalString);



        return finalString;
    }



    // similar to the previous one
    // but instead of link returns the whole HTML content of description

    public String getAsXMLWithHTML() throws IOException, ParserConfigurationException, SAXException  {

        String out ="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestString);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        Document document = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder        builder = factory.newDocumentBuilder();


        try {
            HttpEntity entity = response.getEntity();
            if (entity != null){
                InputStream inputStream = entity.getContent();
                document = builder.parse(inputStream);
                NodeList list = document.getElementsByTagName("item");
                log.info("nodes for this request : " + String.valueOf(list.getLength()));

            }
        } finally {
            response.close();
        }

        NodeList nl = document.getElementsByTagName("item");

        String finalString = "";

        int nlLength = nl.getLength();
        log.info(String.valueOf(nlLength));

        for (int i =0; i<nlLength;i++){

            Element el = (Element) nl.item(i);


            String itmId = el.getElementsByTagName("itemId").item(0).getTextContent();

            String name = el.getElementsByTagName("title").item(0).getTextContent();

            String desc =getHTML(descriptionHTML + itmId);


            finalString+=createXML(itmId,name,desc);


        }
        log.info("----------- HTML----------------");
        log.info(finalString);


        return finalString;
    }







    private String createXML (String id, String title, String desc){

        String out = "<item>"
                + "<itemId>" + id    + "</itemId>"
                + "<title>"  + title + "</title>"
                + "<desc>"   + desc  + "</desc>"
                + "</item>";


        return out;
    }

    private String getHTML(String url) throws IOException {
        String out ="";
        InputStream iS = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);


        try {
            HttpEntity entity = response.getEntity();

          //  if (entity != null) iS = entity.getContent();

            out = EntityUtils.toString(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (iS != null) iS.close();
        }



        return out;
    }



}
