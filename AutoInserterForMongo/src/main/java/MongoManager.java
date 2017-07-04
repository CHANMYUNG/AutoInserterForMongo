import com.mongodb.*;

import javax.swing.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NooHeat on 03/07/2017.
 */
public class MongoManager {

    private MongoClient mongoClient;
    private DB db;
    private DBCollection collection;

    private List<String> attributes;

    public MongoManager(int port, String dbName, String col) {

        GUIFrame.log("MongoDB port : " + port);
        GUIFrame.log("Database : " + dbName);
        GUIFrame.log("Collection : " + col);

        try {
            mongoClient = new MongoClient(new ServerAddress("localhost", port));
        } catch (UnknownHostException e) {
            GUIFrame.errorOccurred();
            return;
        }
        System.out.println(mongoClient.getAddress());
        db = mongoClient.getDB(dbName);
        collection = db.getCollection(col);
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void saveWithValues(HashMap<String, String> definedAttributes, List<String> values) {
        try {
            if (attributes.size() != values.size()) {
                GUIFrame.log("Why are there more values than attribute counts?");
                GUIFrame.log("It may not be inserted properly.");
            }

            BasicDBObject object = new BasicDBObject();
            for (int i = 0; i < attributes.size(); i++) {
                if (definedAttributes.get(attributes.get(i)).equals("Number"))
                    object.append(attributes.get(i), Integer.parseInt(values.get(i).substring(0, values.get(i).length() - 2)));
                else
                    object.append(attributes.get(i), values.get(i));
                collection.save(object);
            }
        } catch (Exception e) {
            GUIFrame.errorOccurred();
            return;
        }
    }
}
