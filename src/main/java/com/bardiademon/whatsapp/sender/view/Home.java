package com.bardiademon.whatsapp.sender.view;

import com.bardiademon.whatsapp.sender.controller.ViewController.HomeController;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Font;

public abstract class Home extends View
{
    protected String[] orderOfSubmission;

    protected final DefaultListModel<String> lstLogModel = new DefaultListModel<>();
    protected final DefaultListModel<String> lstOrderModel = new DefaultListModel<>();

    protected JButton btnClearConnection;
    protected JButton btnClearLog;
    protected JButton btnConnect;
    protected JButton btnDocumentChooser;
    protected JButton btnFileChooser;
    protected JButton btnImageChooser;
    protected JButton btnImportPhone;
    protected JButton btnMoveBottomSelection;
    protected JButton btnMoveTopSelection;
    protected JButton btnSaveLog;
    protected JButton btnSendMessage;
    protected JButton btnText;
    protected JButton btnVideoChooser;
    protected JLabel lblNumberOfPhones;
    protected JLabel lblValPath;
    protected JLabel lblValSelected;
    protected JLabel lblValStatus;
    protected JList<String> lstLog;
    protected JList<String> lstOrderOfSubmission;
    protected JTextArea txtMessage;
    protected JTextField txtPhone;

    public Home()
    {
        final HomeController.Selection[] values = HomeController.Selection.values();
        orderOfSubmission = new String[values.length];
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) orderOfSubmission[i] = values[i].name();
    }

    @Override
    protected void setView(String title)
    {
        initComponents();

        SwingUtilities.invokeLater(() ->
        {
            final Font txtMessageFont = txtMessage.getFont();
            txtMessage.setFont(new Font(txtMessageFont.getName() , txtMessageFont.getStyle() , 13));
        });

        setOnClick();
        lstLog.setModel(lstLogModel);

        updateOrder();

        lstOrderOfSubmission.setModel(lstOrderModel);
        super.setView(title);
    }

    protected void updateOrder()
    {
        lstOrderModel.clear();
        for (final String item : orderOfSubmission) lstOrderModel.addElement(item);
    }

    private void initComponents()
    {

        txtPhone = new javax.swing.JTextField();
        javax.swing.JLabel lblPhone = new javax.swing.JLabel();
        btnConnect = new javax.swing.JButton();
        btnClearConnection = new javax.swing.JButton();
        javax.swing.JScrollPane scrollLstLog = new javax.swing.JScrollPane();
        lstLog = new javax.swing.JList<>();
        javax.swing.JLabel lblLog = new javax.swing.JLabel();
        btnImportPhone = new javax.swing.JButton();
        lblNumberOfPhones = new javax.swing.JLabel();
        lblValStatus = new javax.swing.JLabel();
        javax.swing.JLabel lblStatus = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollTxtMessage = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        btnText = new javax.swing.JButton();
        btnImageChooser = new javax.swing.JButton();
        btnVideoChooser = new javax.swing.JButton();
        btnDocumentChooser = new javax.swing.JButton();
        javax.swing.JLabel lblTxtMessage = new javax.swing.JLabel();
        javax.swing.JLabel lblPath = new javax.swing.JLabel();
        lblValPath = new javax.swing.JLabel();
        btnSendMessage = new javax.swing.JButton();
        btnClearLog = new javax.swing.JButton();
        btnSaveLog = new javax.swing.JButton();
        javax.swing.JLabel lblSelected = new javax.swing.JLabel();
        lblValSelected = new javax.swing.JLabel();
        btnFileChooser = new javax.swing.JButton();
        javax.swing.JScrollPane scrollOrderList = new javax.swing.JScrollPane();
        lstOrderOfSubmission = new javax.swing.JList<>();
        btnMoveTopSelection = new javax.swing.JButton();
        btnMoveBottomSelection = new javax.swing.JButton();

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

        scrollOrderList.setViewportView(lstOrderOfSubmission);

        btnMoveTopSelection.setText("T");

        btnMoveBottomSelection.setText("B");

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
                                                                .addComponent(btnFileChooser , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                                        .addComponent(btnSendMessage , javax.swing.GroupLayout.Alignment.LEADING , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING , layout.createSequentialGroup()
                                                                .addComponent(lblSelected)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblValSelected , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
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
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(scrollTxtMessage , javax.swing.GroupLayout.PREFERRED_SIZE , 414 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(scrollOrderList , javax.swing.GroupLayout.PREFERRED_SIZE , 0 , Short.MAX_VALUE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(btnMoveTopSelection , javax.swing.GroupLayout.PREFERRED_SIZE , 52 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(btnMoveBottomSelection , javax.swing.GroupLayout.DEFAULT_SIZE , 53 , Short.MAX_VALUE)))))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(btnMoveBottomSelection)
                                                                        .addComponent(btnMoveTopSelection))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollOrderList , javax.swing.GroupLayout.PREFERRED_SIZE , 111 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(scrollTxtMessage , javax.swing.GroupLayout.PREFERRED_SIZE , 145 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(15 , 15 , 15))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(scrollLstLog)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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

        btnMoveBottomSelection.addActionListener(e -> onClickBtnMoveBottomSelection());
        btnMoveTopSelection.addActionListener(e -> onClickBtnMoveTopSelection());
    }

    protected abstract void onClickBtnMoveTopSelection();

    protected abstract void onClickBtnMoveBottomSelection();

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
