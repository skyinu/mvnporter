package com.skyinu.porter.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;

/**
 * Created by chen on 2018/11/17.
 */
public class XmlUtils {
    public static <T> T toBean(File xmlFile, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlFile);
        return obj;

    }
}
