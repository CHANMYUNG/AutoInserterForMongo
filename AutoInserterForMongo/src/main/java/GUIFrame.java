import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NooHeat on 03/07/2017.
 */
public class GUIFrame {
    private static final String PATHHINT = "  Excel file path";
    private static final String PORTHINT = "  Port Number (0 ~ 65535, Usually 27017)";
    private static final String DBHINT = "  Database Name";
    private static final String COLHINT = "  Collection Name";

    private static JFrame frame;
    private static JButton loadButton;
    private static FileDialog load;
    private static JTextField pathField;
    private static JLabel portLabel;
    private static JTextField portField;
    private static JLabel dbLabel;
    private static JTextField dbField;
    private static JLabel colLabel;
    private static JTextField colField;
    private static JButton launch;

    private static JTextArea notice;
    private static JScrollPane noticePane;

    private static MongoManager mongoManager;
    private static ExcelManager excelManager;


    public GUIFrame() {
        frame = new JFrame();
        frame.setTitle("AutoInserterForMongo");
        frame.setSize(600, 500);
        frame.setLayout(null);
        load = new FileDialog(frame, "Choose");

        pathField = new JTextField(PATHHINT);
        // pathField.setEditable(false);
        pathField.setLocation(50, 30);
        pathField.setSize(400, 50);
        pathField.setBackground(Color.WHITE);
        pathField.setEnabled(false);

        loadButton = new JButton("Choose");
        loadButton.setSize(70, 50);
        loadButton.setLocation(460, 30);
        loadButton.setFont(new Font("Serif", Font.BOLD, 15));
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                load.setVisible(true);
                if (load.getDirectory() != null && load.getFile() != null) {
                    System.out.println(load.getDirectory() + load.getFile());
                    pathField.setText("  " + load.getDirectory() + load.getFile());
                }
            }
        });

        portLabel = new JLabel("Port Number : ");
        portLabel.setLocation(50, 100);
        portLabel.setSize(200, 50);
        portLabel.setFont(new Font("Serif", Font.BOLD, 15));

        portField = new JTextField(PORTHINT);
        portField.setLocation(150, 100);
        portField.setSize(300, 50);
        portField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (portField.getText().equals(PORTHINT)) portField.setText("  ");
            }

            public void focusLost(FocusEvent e) {
                if (portField.getText().trim().equals("")) portField.setText(PORTHINT);
            }
        });

        dbLabel = new JLabel("Database Name : ");
        dbLabel.setLocation(50, 170);
        dbLabel.setSize(200, 50);
        dbLabel.setFont(new Font("Serif", Font.BOLD, 15));

        dbField = new JTextField(DBHINT);
        dbField.setLocation(170, 170);
        dbField.setSize(280, 50);
        dbField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (dbField.getText().equals(DBHINT)) dbField.setText("  ");
            }

            public void focusLost(FocusEvent e) {
                if (dbField.getText().trim().equals("")) dbField.setText(DBHINT);
            }
        });

        colLabel = new JLabel("Collection Name : ");
        colLabel.setLocation(50, 240);
        colLabel.setSize(200, 50);
        colLabel.setFont(new Font("Serif", Font.BOLD, 15));

        colField = new JTextField(COLHINT);
        colField.setLocation(170, 240);
        colField.setSize(280, 50);
        colField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (colField.getText().equals(COLHINT)) colField.setText("  ");
            }

            public void focusLost(FocusEvent e) {
                if (colField.getText().trim().equals("")) colField.setText(COLHINT);
            }
        });

        launch = new JButton("GO!");
        launch.setSize(100, 50);
        launch.setFont(new Font("Godic", Font.BOLD, 25));
        launch.setLocation(250, 300);

        launch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    launch();
                } catch (Exception e1) {
                    final JDialog dialog = new JDialog();
                    JLabel notice = new JLabel(" Error occurred! please check your input");
                    JButton okButton = new JButton("OK");

                    notice.setFont(new Font("Godic", Font.BOLD, 14));
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });

                    dialog.setSize(300, 300);
                    dialog.add(notice, "Center");
                    dialog.add(okButton, "South");
                    dialog.setLocation(frame.getLocation().x + 150, frame.getLocation().y + 100);
                    dialog.setResizable(false);

                    frame.setEnabled(false);
                    dialog.setVisible(true);
                    dialog.setAlwaysOnTop(true);
                    frame.setEnabled(true);

                }
            }
        });

        notice = new JTextArea();
        noticePane = new JScrollPane(notice);
        notice.setLineWrap(true);
        notice.append("WARNING\n");
        notice.append("The first column should contain the name of each attribute,\n");
        notice.append("and the value from the second column.\n");
        notice.append("Also, it should fit in the upper left corner.");
        notice.setEnabled(false);
        notice.setFont(new Font("Godic", Font.BOLD, 15));
        notice.setDisabledTextColor(Color.RED);
        noticePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        noticePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        noticePane.setSize(500, 100);
        noticePane.setLocation(50, 370);


        frame.add(pathField);
        frame.add(loadButton);
        frame.add(portLabel);
        frame.add(portField);
        frame.add(dbLabel);
        frame.add(dbField);
        frame.add(colLabel);
        frame.add(colField);
        frame.add(launch);
        frame.add(noticePane);
    }


    public void start() {
        Dimension frameSize = frame.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void launch() throws Exception {
        String path = pathField.getText().trim();
        String port = portField.getText().trim();
        String db = dbField.getText().trim();
        String col = colField.getText().trim();

        System.out.println(pathField.getText().trim() + portField.getText().trim() + dbField.getText().trim() + colField.getText().trim());
        if (paramCheck(path, port, db, col)) {
            notice.setText("");
            notice.setDisabledTextColor(Color.BLACK);
            log("----------log----------");
            mongoManager = new MongoManager(Integer.parseInt(port), db, col);
            excelManager = new ExcelManager(path, mongoManager);

            excelManager.defineAttributeTypes();

        } else {
            final JDialog dialog = new JDialog();
            JLabel notice = new JLabel(" Enter the values that match your criteria");
            JButton okButton = new JButton("OK");

            notice.setFont(new Font("Godic", Font.BOLD, 14));
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            dialog.setSize(300, 300);
            dialog.add(notice, "Center");
            dialog.add(okButton, "South");
            dialog.setLocation(frame.getLocation().x + 150, frame.getLocation().y + 100);
            dialog.setResizable(false);

            frame.setEnabled(false);
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
            frame.setEnabled(true);

        }

    }

    public static void log(String log) {
        notice.append(log + "\n");
    }

    private boolean paramCheck(String path, String port_s, String db, String col) {

        int port = 0;

        try {
            port = Integer.parseInt(portField.getText().trim());
        } catch (Exception e) {
            return false;
        }

        if (path.equals(PATHHINT) || ((!path.endsWith(".xlsx")) && (!path.endsWith(".xls")))) return false;
        if (port < 0 || port > 65535) return false;
        if (db.equals(DBHINT) || db.contains(" ")) return false;
        if (col.equals(COLHINT) || col.contains(" ")) return false;
        return true;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static Dimension getFrameSize() {
        return frame.getSize();
    }

    public static void errorOccurred() {
        final JDialog dialog = new JDialog();
        JLabel notice = new JLabel(" ERROR OCCURRED :: please check your input");
        JButton okButton = new JButton("OK");

        notice.setFont(new Font("Godic", Font.BOLD, 14));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setSize(300, 300);
        dialog.add(notice, "Center");
        dialog.add(okButton, "South");
        dialog.setLocation(frame.getLocation().x + 150, frame.getLocation().y + 100);
        dialog.setResizable(false);

        frame.setEnabled(false);
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        frame.setEnabled(true);
    }
}
