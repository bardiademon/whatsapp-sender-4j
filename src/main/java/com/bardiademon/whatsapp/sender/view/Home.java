package com.bardiademon.whatsapp.sender.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class Home extends View
{
    protected final DefaultListModel<String> lstLogModel = new DefaultListModel<>();

    protected JButton btnClearConnection;
    protected JButton btnClearLog;
    protected JButton btnConnect;
    protected JButton btnDocumentChooser;
    protected JButton btnFileChooser;
    protected JButton btnImageChooser;
    protected JButton btnImportPhone;
    protected JButton btnSaveLog;
    protected JButton btnSendMessage;
    protected JButton btnText;
    protected JButton btnVideoChooser;
    protected JLabel lblNumberOfPhones;
    protected JLabel lblValPath;
    protected JLabel lblValSelected;
    protected JLabel lblValStatus;
    protected JList<String> lstLog;
    protected JTextArea txtMessage;
    protected JTextField txtPhone;

    @Override
    protected void setView(String title)
    {
        initComponents();
        setOnClick();
        lstLog.setModel(lstLogModel);
        super.setView(title);
    }

    private void initComponents()
    {
        txtPhone = new JTextField();
        JLabel lblPhone = new JLabel();
        btnConnect = new JButton();
        btnClearConnection = new JButton();
        javax.swing.JScrollPane scrollLstLog = new javax.swing.JScrollPane();
        lstLog = new javax.swing.JList<>();
        JLabel lblLog = new JLabel();
        btnImportPhone = new JButton();
        lblNumberOfPhones = new JLabel();
        lblValStatus = new JLabel();
        JLabel lblStatus = new JLabel();
        javax.swing.JScrollPane scrollTxtMessage = new javax.swing.JScrollPane();
        txtMessage = new JTextArea();
        btnText = new JButton();
        btnImageChooser = new JButton();
        btnVideoChooser = new JButton();
        btnDocumentChooser = new JButton();
        JLabel lblTxtMessage = new JLabel();
        JLabel lblPath = new JLabel();
        lblValPath = new JLabel();
        btnSendMessage = new JButton();
        btnClearLog = new JButton();
        btnSaveLog = new JButton();
        JLabel lblSelected = new JLabel();
        lblValSelected = new JLabel();
        btnFileChooser = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblPhone.setText("Phone");

        btnConnect.setText("Connect");

        btnClearConnection.setText("Clear connection");

        scrollLstLog.setViewportView(lstLog);

        lblLog.setText("Log");

        btnImportPhone.setText("Import phones");

        lblNumberOfPhones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfPhones.setText("0 / 0");

        lblStatus.setText("Status");

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        scrollTxtMessage.setViewportView(txtMessage);

        btnText.setText("Text");

        btnImageChooser.setText("Image");

        btnVideoChooser.setText("Video");

        btnDocumentChooser.setText("Document");

        lblPath.setText("Path: ");

        btnSendMessage.setText("Send message");

        btnClearLog.setText("Clear log");

        btnSaveLog.setText("Save log");

        lblSelected.setText("Selected: ");

        btnFileChooser.setText("Choose");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblPhone)
                                                        .addComponent(lblStatus)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10 , 10 , 10)
                                                                .addComponent(txtPhone , javax.swing.GroupLayout.PREFERRED_SIZE , 464 , javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblNumberOfPhones , javax.swing.GroupLayout.PREFERRED_SIZE , 93 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnImportPhone))
                                                        .addComponent(lblLog , javax.swing.GroupLayout.PREFERRED_SIZE , 62 , javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                                .addGap(0 , 0 , Short.MAX_VALUE)
                                                .addComponent(lblValStatus , javax.swing.GroupLayout.PREFERRED_SIZE , 449 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18 , 18 , 18)
                                                .addComponent(btnClearConnection)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnConnect , javax.swing.GroupLayout.PREFERRED_SIZE , 107 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblPath)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblValPath , javax.swing.GroupLayout.PREFERRED_SIZE , 371 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnFileChooser , javax.swing.GroupLayout.DEFAULT_SIZE , 114 , Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblTxtMessage , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                                                .addGap(18 , 18 , 18)
                                                                .addComponent(btnText)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnImageChooser)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnVideoChooser)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDocumentChooser))
                                                        .addComponent(btnSendMessage , javax.swing.GroupLayout.Alignment.LEADING , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                                        .addComponent(scrollTxtMessage)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING , layout.createSequentialGroup()
                                                                .addComponent(lblSelected)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblValSelected , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false)
                                                        .addComponent(scrollLstLog , javax.swing.GroupLayout.PREFERRED_SIZE , 213 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnClearLog , javax.swing.GroupLayout.PREFERRED_SIZE , 121 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnSaveLog , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(11 , 11 , 11)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnConnect)
                                                        .addComponent(btnClearConnection))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnImportPhone)
                                                        .addComponent(lblNumberOfPhones))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblLog))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6 , 6 , 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblValStatus , javax.swing.GroupLayout.PREFERRED_SIZE , 18 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblStatus))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPhone)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtPhone , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnFileChooser)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(lblValPath , javax.swing.GroupLayout.PREFERRED_SIZE , 21 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblPath)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblSelected)
                                                        .addComponent(lblValSelected , javax.swing.GroupLayout.PREFERRED_SIZE , 20 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(31 , 31 , 31)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(btnText)
                                                                .addComponent(btnImageChooser)
                                                                .addComponent(btnVideoChooser)
                                                                .addComponent(btnDocumentChooser))
                                                        .addComponent(lblTxtMessage , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollTxtMessage , javax.swing.GroupLayout.PREFERRED_SIZE , 145 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(scrollLstLog))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSendMessage)
                                        .addComponent(btnClearLog)
                                        .addComponent(btnSaveLog))
                                .addContainerGap())
        );

        pack();
    }

    private void setOnClick()
    {
        btnConnect.addActionListener(e -> onClickBtnConnect());
        btnClearConnection.addActionListener(e -> onClickBtnClearConnection());
        btnClearLog.addActionListener(e -> onClickBtnClearLog());
        btnDocumentChooser.addActionListener(e -> onClickBtnDocumentChooser());
        btnFileChooser.addActionListener(e -> onClickBtnFileChooser());
        btnImageChooser.addActionListener(e -> onClickBtnImageChooser());
        btnImportPhone.addActionListener(e -> onClickBtnImportPhone());
        btnSaveLog.addActionListener(e -> onClickBtnSaveLog());
        btnSendMessage.addActionListener(e -> onClickBtnSendMessage());
        btnVideoChooser.addActionListener(e -> onClickBtnVideoChooser());
        btnText.addActionListener(e -> onClickBtnText());
        txtMessage.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                onChangeTxtMessage();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                onChangeTxtMessage();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                onChangeTxtMessage();
            }
        });
    }

    protected abstract void onChangeTxtMessage();

    protected abstract void onClickBtnText();

    protected abstract void onClickBtnConnect();

    protected abstract void onClickBtnClearConnection();

    protected abstract void onClickBtnClearLog();

    protected abstract void onClickBtnDocumentChooser();

    protected abstract void onClickBtnFileChooser();

    protected abstract void onClickBtnImageChooser();

    protected abstract void onClickBtnImportPhone();

    protected abstract void onClickBtnSaveLog();

    protected abstract void onClickBtnSendMessage();

    protected abstract void onClickBtnVideoChooser();
}
