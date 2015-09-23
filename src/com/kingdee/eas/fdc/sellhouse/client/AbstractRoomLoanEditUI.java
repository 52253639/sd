/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractRoomLoanEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomLoanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomLoanInfo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProcessLoanDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualLoanAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanFixedYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmmType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAfmortgaged;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPromiseDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRemoveData;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddData;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkProcessLoanDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualLoanAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanFixedYear;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtMoneyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7LoanBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Afmortgaged;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAppLoanDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPromiseFinishDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpCreateDate;
    protected com.kingdee.eas.fdc.sellhouse.RoomLoanInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomLoanEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomLoanEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRoomLoanInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProcessLoanDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualLoanAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanFixedYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDCheckBox1 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contmmType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAfmortgaged = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPromiseDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRemoveData = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnAddData = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkProcessLoanDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtActualLoanAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLoanFixedYear = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7LoanBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Afmortgaged = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAppLoanDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPromiseFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.pnlRoomLoanInfo.setName("pnlRoomLoanInfo");
        this.sclPanel.setName("sclPanel");
        this.contDescription.setName("contDescription");
        this.contProcessLoanDate.setName("contProcessLoanDate");
        this.contActualLoanAmt.setName("contActualLoanAmt");
        this.contLoanFixedYear.setName("contLoanFixedYear");
        this.contNumber.setName("contNumber");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDCheckBox1.setName("kDCheckBox1");
        this.contmmType.setName("contmmType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contAfmortgaged.setName("contAfmortgaged");
        this.contLoanDate.setName("contLoanDate");
        this.contPromiseDate.setName("contPromiseDate");
        this.contCreator.setName("contCreator");
        this.contCreateDate.setName("contCreateDate");
        this.btnRemoveData.setName("btnRemoveData");
        this.btnAddData.setName("btnAddData");
        this.kDTextArea1.setName("kDTextArea1");
        this.pkProcessLoanDate.setName("pkProcessLoanDate");
        this.txtActualLoanAmt.setName("txtActualLoanAmt");
        this.txtLoanFixedYear.setName("txtLoanFixedYear");
        this.txtNumber.setName("txtNumber");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDTable1.setName("kDTable1");
        this.kDTable2.setName("kDTable2");
        this.txtMoneyType.setName("txtMoneyType");
        this.f7LoanBank.setName("f7LoanBank");
        this.f7Afmortgaged.setName("f7Afmortgaged");
        this.pkAppLoanDate.setName("pkAppLoanDate");
        this.pkPromiseFinishDate.setName("pkPromiseFinishDate");
        this.f7Creator.setName("f7Creator");
        this.kdpCreateDate.setName("kdpCreateDate");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));
        // pnlRoomLoanInfo		
        this.pnlRoomLoanInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomLoanInfo.border.title")));		
        this.pnlRoomLoanInfo.setEnabled(false);
        // sclPanel		
        this.sclPanel.setAutoscrolls(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contProcessLoanDate		
        this.contProcessLoanDate.setBoundLabelText(resHelper.getString("contProcessLoanDate.boundLabelText"));		
        this.contProcessLoanDate.setBoundLabelLength(70);		
        this.contProcessLoanDate.setBoundLabelUnderline(true);
        // contActualLoanAmt		
        this.contActualLoanAmt.setBoundLabelText(resHelper.getString("contActualLoanAmt.boundLabelText"));		
        this.contActualLoanAmt.setBoundLabelLength(100);		
        this.contActualLoanAmt.setBoundLabelUnderline(true);
        // contLoanFixedYear		
        this.contLoanFixedYear.setBoundLabelText(resHelper.getString("contLoanFixedYear.boundLabelText"));		
        this.contLoanFixedYear.setBoundLabelLength(100);		
        this.contLoanFixedYear.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // kDTabbedPane1
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDCheckBox1		
        this.kDCheckBox1.setText(resHelper.getString("kDCheckBox1.text"));		
        this.kDCheckBox1.setEnabled(false);
        this.kDCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDCheckBox1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contmmType		
        this.contmmType.setBoundLabelText(resHelper.getString("contmmType.boundLabelText"));		
        this.contmmType.setBoundLabelLength(100);		
        this.contmmType.setBoundLabelUnderline(true);		
        this.contmmType.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contAfmortgaged		
        this.contAfmortgaged.setBoundLabelText(resHelper.getString("contAfmortgaged.boundLabelText"));		
        this.contAfmortgaged.setBoundLabelLength(100);		
        this.contAfmortgaged.setBoundLabelUnderline(true);
        // contLoanDate		
        this.contLoanDate.setBoundLabelText(resHelper.getString("contLoanDate.boundLabelText"));		
        this.contLoanDate.setBoundLabelLength(100);		
        this.contLoanDate.setBoundLabelUnderline(true);
        // contPromiseDate		
        this.contPromiseDate.setBoundLabelText(resHelper.getString("contPromiseDate.boundLabelText"));		
        this.contPromiseDate.setBoundLabelLength(100);		
        this.contPromiseDate.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateDate		
        this.contCreateDate.setBoundLabelText(resHelper.getString("contCreateDate.boundLabelText"));		
        this.contCreateDate.setEnabled(false);		
        this.contCreateDate.setBoundLabelLength(100);		
        this.contCreateDate.setBoundLabelUnderline(true);
        // btnRemoveData		
        this.btnRemoveData.setText(resHelper.getString("btnRemoveData.text"));
        this.btnRemoveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveData_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddData		
        this.btnAddData.setText(resHelper.getString("btnAddData.text"));
        this.btnAddData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddData_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDTextArea1
        // pkProcessLoanDate		
        this.pkProcessLoanDate.setEnabled(false);		
        this.pkProcessLoanDate.setEditable(false);
        // txtActualLoanAmt		
        this.txtActualLoanAmt.setDataType(1);		
        this.txtActualLoanAmt.setSupportedEmpty(true);		
        this.txtActualLoanAmt.setEnabled(false);		
        this.txtActualLoanAmt.setEditable(false);
        // txtLoanFixedYear		
        this.txtLoanFixedYear.setSupportedEmpty(true);		
        this.txtLoanFixedYear.setRequired(true);
        // txtNumber		
        this.txtNumber.setMaxLength(30);		
        this.txtNumber.setRequired(true);
        // kDPanel1		
        this.kDPanel1.setBorder(null);
        // kDPanel2
        this.kDPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    kDPanel2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"aApproach\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"aDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"proFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"aFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"transactor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"aRemark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isFinishFlag\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"aOrb\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{aApproach}</t:Cell><t:Cell>$Resource{aDate}</t:Cell><t:Cell>$Resource{proFinishDate}</t:Cell><t:Cell>$Resource{aFinish}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{transactor}</t:Cell><t:Cell>$Resource{aRemark}</t:Cell><t:Cell>$Resource{isFinishFlag}</t:Cell><t:Cell>$Resource{aOrb}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDTable2
		String kDTable2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bApproach\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"bFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"bDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"bRemark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aOrb\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bApproach}</t:Cell><t:Cell>$Resource{bFinish}</t:Cell><t:Cell>$Resource{bDate}</t:Cell><t:Cell>$Resource{bRemark}</t:Cell><t:Cell>$Resource{aOrb}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable2.setFormatXml(resHelper.translateString("kDTable2",kDTable2StrXML));
        this.kDTable2.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDTable2_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTable2.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable2_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // txtMoneyType		
        this.txtMoneyType.setEnabled(false);
        // f7LoanBank		
        this.f7LoanBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");		
        this.f7LoanBank.setRequired(true);
        this.f7LoanBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7LoanBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Afmortgaged		
        this.f7Afmortgaged.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AFMortgagedQuery");		
        this.f7Afmortgaged.setRequired(true);
        this.f7Afmortgaged.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Afmortgaged_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkAppLoanDate		
        this.pkAppLoanDate.setRequired(true);
        // pkPromiseFinishDate
        // f7Creator		
        this.f7Creator.setEnabled(false);
        // kdpCreateDate		
        this.kdpCreateDate.setEnabled(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 750));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 750));
        pnlRoomInfo.setBounds(new Rectangle(4, 5, 1005, 136));
        this.add(pnlRoomInfo, new KDLayout.Constraints(4, 5, 1005, 136, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlRoomLoanInfo.setBounds(new Rectangle(4, 147, 1005, 593));
        this.add(pnlRoomLoanInfo, new KDLayout.Constraints(4, 147, 1005, 593, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlRoomInfo
pnlRoomInfo.setLayout(new BorderLayout(0, 0));        pnlRoomInfo.add(sclPanel, BorderLayout.CENTER);
        //pnlRoomLoanInfo
        pnlRoomLoanInfo.setLayout(new KDLayout());
        pnlRoomLoanInfo.putClientProperty("OriginalBounds", new Rectangle(4, 147, 1005, 593));        contDescription.setBounds(new Rectangle(19, 81, 965, 48));
        pnlRoomLoanInfo.add(contDescription, new KDLayout.Constraints(19, 81, 965, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contProcessLoanDate.setBounds(new Rectangle(799, 59, 185, 19));
        pnlRoomLoanInfo.add(contProcessLoanDate, new KDLayout.Constraints(799, 59, 185, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contActualLoanAmt.setBounds(new Rectangle(714, 15, 270, 19));
        pnlRoomLoanInfo.add(contActualLoanAmt, new KDLayout.Constraints(714, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLoanFixedYear.setBounds(new Rectangle(366, 59, 270, 19));
        pnlRoomLoanInfo.add(contLoanFixedYear, new KDLayout.Constraints(366, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(19, 15, 270, 19));
        pnlRoomLoanInfo.add(contNumber, new KDLayout.Constraints(19, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(19, 181, 965, 348));
        pnlRoomLoanInfo.add(kDTabbedPane1, new KDLayout.Constraints(19, 181, 965, 348, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDCheckBox1.setBounds(new Rectangle(714, 59, 78, 19));
        pnlRoomLoanInfo.add(kDCheckBox1, new KDLayout.Constraints(714, 59, 78, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmmType.setBounds(new Rectangle(366, 15, 270, 19));
        pnlRoomLoanInfo.add(contmmType, new KDLayout.Constraints(366, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(19, 59, 270, 19));
        pnlRoomLoanInfo.add(kDLabelContainer1, new KDLayout.Constraints(19, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAfmortgaged.setBounds(new Rectangle(714, 37, 270, 19));
        pnlRoomLoanInfo.add(contAfmortgaged, new KDLayout.Constraints(714, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLoanDate.setBounds(new Rectangle(19, 37, 270, 19));
        pnlRoomLoanInfo.add(contLoanDate, new KDLayout.Constraints(19, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPromiseDate.setBounds(new Rectangle(366, 37, 270, 19));
        pnlRoomLoanInfo.add(contPromiseDate, new KDLayout.Constraints(366, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(16, 548, 270, 19));
        pnlRoomLoanInfo.add(contCreator, new KDLayout.Constraints(16, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateDate.setBounds(new Rectangle(363, 548, 270, 19));
        pnlRoomLoanInfo.add(contCreateDate, new KDLayout.Constraints(363, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveData.setBounds(new Rectangle(897, 147, 87, 21));
        pnlRoomLoanInfo.add(btnRemoveData, new KDLayout.Constraints(897, 147, 87, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddData.setBounds(new Rectangle(798, 147, 87, 21));
        pnlRoomLoanInfo.add(btnAddData, new KDLayout.Constraints(798, 147, 87, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contDescription
        contDescription.setBoundEditor(kDTextArea1);
        //contProcessLoanDate
        contProcessLoanDate.setBoundEditor(pkProcessLoanDate);
        //contActualLoanAmt
        contActualLoanAmt.setBoundEditor(txtActualLoanAmt);
        //contLoanFixedYear
        contLoanFixedYear.setBoundEditor(txtLoanFixedYear);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDTable1, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(kDTable2, BorderLayout.CENTER);
        //contmmType
        contmmType.setBoundEditor(txtMoneyType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(f7LoanBank);
        //contAfmortgaged
        contAfmortgaged.setBoundEditor(f7Afmortgaged);
        //contLoanDate
        contLoanDate.setBoundEditor(pkAppLoanDate);
        //contPromiseDate
        contPromiseDate.setBoundEditor(pkPromiseFinishDate);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateDate
        contCreateDate.setBoundEditor(kdpCreateDate);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("description", String.class, this.kDTextArea1, "text");
		dataBinder.registerBinding("actualFinishDate", java.util.Date.class, this.pkProcessLoanDate, "value");
		dataBinder.registerBinding("actualLoanAmt", java.math.BigDecimal.class, this.txtActualLoanAmt, "value");
		dataBinder.registerBinding("loanFixedYear", int.class, this.txtLoanFixedYear, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("mmType", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.txtMoneyType, "data");
		dataBinder.registerBinding("loanBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.f7LoanBank, "data");
		dataBinder.registerBinding("oRSOMortgaged", com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo.class, this.f7Afmortgaged, "data");
		dataBinder.registerBinding("applicationDate", java.util.Date.class, this.pkAppLoanDate, "value");
		dataBinder.registerBinding("promiseDate", java.util.Date.class, this.pkPromiseFinishDate, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kdpCreateDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomLoanEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomLoanInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualLoanAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanFixedYear", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mmType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oRSOMortgaged", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applicationDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("promiseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kDCheckBox1_stateChanged method
     */
    protected void kDCheckBox1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output btnRemoveData_actionPerformed method
     */
    protected void btnRemoveData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddData_actionPerformed method
     */
    protected void btnAddData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDPanel2_mouseClicked method
     */
    protected void kDPanel2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kDTable2_editStopped method
     */
    protected void kDTable2_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kDTable2_tableClicked method
     */
    protected void kDTable2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output f7LoanBank_dataChanged method
     */
    protected void f7LoanBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Afmortgaged_dataChanged method
     */
    protected void f7Afmortgaged_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("actualFinishDate"));
        sic.add(new SelectorItemInfo("actualLoanAmt"));
        sic.add(new SelectorItemInfo("loanFixedYear"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("mmType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("mmType.id"));
        	sic.add(new SelectorItemInfo("mmType.number"));
        	sic.add(new SelectorItemInfo("mmType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("loanBank.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("loanBank.id"));
        	sic.add(new SelectorItemInfo("loanBank.number"));
        	sic.add(new SelectorItemInfo("loanBank.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("oRSOMortgaged.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("oRSOMortgaged.id"));
        	sic.add(new SelectorItemInfo("oRSOMortgaged.number"));
        	sic.add(new SelectorItemInfo("oRSOMortgaged.name"));
		}
        sic.add(new SelectorItemInfo("applicationDate"));
        sic.add(new SelectorItemInfo("promiseDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomLoanEditUI");
    }




}