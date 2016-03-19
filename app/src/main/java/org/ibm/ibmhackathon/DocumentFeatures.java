package org.ibm.ibmhackathon;

import java.util.Vector;

/**
 * Created by manthan on 19/3/16.
 */
public class DocumentFeatures {

    String status;
    String language;
    String text;
    Vector<Keywords> keywords;

    public class Keywords{
        String text;
        String relevance;
    }

}
