import com.mongodb.Mongo;
import com.mongodb.util.Hash;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by NooHeat on 03/07/2017.
 */
public class ExcelManager {
    private File file;
    private Workbook excelFile;
    private MongoManager mongoManager;
    private List<String> attributes;
    private List<String> values;
    private JFrame frame;
    private Dimension frameSize;
    private HashMap<String, String> definedAttributes;
    private int rowCount;

    public ExcelManager(String src, MongoManager mongoManager) {
        this.mongoManager = mongoManager;

        try {
            file = new File(src);
            if (src.endsWith(".xls")) excelFile = new HSSFWorkbook(new FileInputStream(file));
            else excelFile = new XSSFWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            GUIFrame.log("FILE NOT FOUND :: Please check xls, xlsx file path and retry");
        } catch (IOException e) {
            GUIFrame.log("ERROR HAS OCCURRED :: Please retry");
        } catch (Exception e) {
            e.printStackTrace();
        }


        GUIFrame.log("File src : " + src);
    }

    public void defineAttributeTypes() {
        frame = GUIFrame.getFrame();
        frameSize = GUIFrame.getFrameSize();
        // Get attributes

        Row attributeRow = excelFile.getSheetAt(0).getRow(0);
        attributes = new ArrayList<String>();
        Iterator<Cell> attriCellIterator = attributeRow.cellIterator();
        String log = "Attributes : ";
        while (attriCellIterator.hasNext()) {
            Cell cell = attriCellIterator.next();
            attributes.add(cell.toString());
            log += cell + " | ";
        }
        System.out.println(log.substring(0, log.length() - 2));
        GUIFrame.log(log.substring(0, log.length() - 2));
        mongoManager.setAttributes(attributes);
        definedAttributes = new HashMap<String, String>();
        final JDialog dialog = new JDialog();
        dialog.setLayout(null);
        dialog.setSize(attributes.size() * 200 + 100, 300);
        dialog.setLocation(frame.getLocation().x + (frameSize.width - dialog.getSize().width) / 2, frame.getLocation().y + (frameSize.height - dialog.getSize().height) / 2);

        JLabel notice = new JLabel();
        notice.setText("Define attribute types");
        notice.setFont(new Font("Godic", Font.BOLD, 25));
        notice.setHorizontalAlignment(JLabel.CENTER);
        notice.setSize(dialog.getSize().width - 60, 50);
        notice.setLocation(30, 30);

        final JComboBox[] cbBoxes = new JComboBox[attributes.size()];
        final JLabel[] labels = new JLabel[attributes.size()];

        for (int i = 0; i < attributes.size(); i++) {
            labels[i] = new JLabel();
            labels[i].setLocation(50 + 200 * i, 100);
            labels[i].setSize(200, 30);
            labels[i].setText(attributes.get(i));
            labels[i].setFont(new Font("Godic", Font.PLAIN, 20));

            cbBoxes[i] = new JComboBox();
            cbBoxes[i].addItem("String");
            cbBoxes[i].addItem("Number");
            cbBoxes[i].setLocation(50 + 200 * i, 150);
            cbBoxes[i].setSize(150, 30);

            dialog.add(labels[i]);
            dialog.add(cbBoxes[i]);
        }

        JButton applyButton = new JButton("Apply");
        applyButton.setSize(100, 50);
        applyButton.setLocation((dialog.getSize().width / 2) - 50, (dialog.getSize().height) - 80);
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                for (int i = 0; i < attributes.size(); i++) {
                    definedAttributes.put(attributes.get(i), cbBoxes[i].getSelectedItem().toString());
                }
                getValuesFromSheet();
            }
        });

        dialog.add(notice);
        dialog.add(applyButton);

        frame.setEnabled(false);
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        frame.setEnabled(true);

        System.out.println("definedAttribute.get(code) is :" + definedAttributes.get("code"));

    }

    private void getValuesFromSheet() {
        Iterator<Row> rowIterator = excelFile.getSheetAt(0).iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            values = new ArrayList<String>();

            String log_value = "Values : ";
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                System.out.println("::::::" + cell.toString());
                values.add(cell.toString());
                log_value += cell + " | ";
            }
            System.out.println(log_value.substring(0, log_value.length() - 2));
            GUIFrame.log(log_value.substring(0, log_value.length() - 2));


            // mongoManager :: save with values
            try {
                mongoManager.saveWithValues(definedAttributes, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
