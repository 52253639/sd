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
public abstract class AbstractSincerityPurchaseChangeNameUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSincerityPurchaseChangeNameUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppoinmentPeople;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppoinmentPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvalidationDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsValid;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabel customerLabel;
    protected com.kingdee.bos.ctrl.swing.KDLabel cusLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel cusLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel cusLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelecCus;
    protected com.kingdee.bos.ctrl.swing.KDLabel cusLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel cusLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSalesman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAppoinmentPeople;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAppoinmentPhone;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInvalidationDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSincerPriceEntrys;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDDelLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDaddLine;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdRoomSinPurRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdChangeNameRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddNewCus;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeName;
    protected com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo editData = null;
    protected ActionChangeName actionChangeName = null;
    protected ActionAddNewCus actionAddNewCus = null;
    /**
     * output class constructor
     */
    public AbstractSincerityPurchaseChangeNameUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSincerityPurchaseChangeNameUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionChangeName
        this.actionChangeName = new ActionChangeName(this);
        getActionManager().registerAction("actionChangeName", actionChangeName);
         this.actionChangeName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNewCus
        this.actionAddNewCus = new ActionAddNewCus(this);
        getActionManager().registerAction("actionAddNewCus", actionAddNewCus);
         this.actionAddNewCus.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppoinmentPeople = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppoinmentPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvalidationDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsValid = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.customerLabel = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cusLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cusLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cusLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelecCus = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.cusLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cusLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSalesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAppoinmentPeople = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAppoinmentPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkInvalidationDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtSincerPriceEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDaddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSellProNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdRoomSinPurRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdChangeNameRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddNewCus = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contSalesman.setName("contSalesman");
        this.contSellProject.setName("contSellProject");
        this.contAppoinmentPeople.setName("contAppoinmentPeople");
        this.contAppoinmentPhone.setName("contAppoinmentPhone");
        this.contInvalidationDate.setName("contInvalidationDate");
        this.chkIsValid.setName("chkIsValid");
        this.kDPanel4.setName("kDPanel4");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contSellProNumber.setName("contSellProNumber");
        this.customerLabel.setName("customerLabel");
        this.cusLabel1.setName("cusLabel1");
        this.cusLabel2.setName("cusLabel2");
        this.cusLabel3.setName("cusLabel3");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.contRoom.setName("contRoom");
        this.btnSelecCus.setName("btnSelecCus");
        this.cusLabel4.setName("cusLabel4");
        this.cusLabel5.setName("cusLabel5");
        this.contCertificateNumber.setName("contCertificateNumber");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtSalesman.setName("prmtSalesman");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtAppoinmentPeople.setName("txtAppoinmentPeople");
        this.txtAppoinmentPhone.setName("txtAppoinmentPhone");
        this.pkInvalidationDate.setName("pkInvalidationDate");
        this.kdtSincerPriceEntrys.setName("kdtSincerPriceEntrys");
        this.kDDelLine.setName("kDDelLine");
        this.kDaddLine.setName("kDaddLine");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.txtSellProNumber.setName("txtSellProNumber");
        this.prmtRoom.setName("prmtRoom");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.kdRoomSinPurRecord.setName("kdRoomSinPurRecord");
        this.kdChangeNameRecord.setName("kdChangeNameRecord");
        this.btnAddNewCus.setName("btnAddNewCus");
        this.btnChangeName.setName("btnChangeName");
        // CoreUI		
        this.setPreferredSize(new Dimension(585,600));		
        this.btnSave.setVisible(false);		
        this.btnSave.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnAudit.setEnabled(false);		
        this.btnAudit.setVisible(false);
        // kDTabbedPane1		
        this.kDTabbedPane1.setPreferredSize(new Dimension(10,10));		
        this.kDTabbedPane1.setMinimumSize(new Dimension(10,10));
        // kDPanel1
        // kDPanel2
        // kDPanel3
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(75);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(75);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(75);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(75);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(75);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelLength(75);		
        this.contSalesman.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(75);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contAppoinmentPeople		
        this.contAppoinmentPeople.setBoundLabelText(resHelper.getString("contAppoinmentPeople.boundLabelText"));		
        this.contAppoinmentPeople.setBoundLabelLength(75);		
        this.contAppoinmentPeople.setBoundLabelUnderline(true);
        // contAppoinmentPhone		
        this.contAppoinmentPhone.setBoundLabelText(resHelper.getString("contAppoinmentPhone.boundLabelText"));		
        this.contAppoinmentPhone.setBoundLabelLength(75);		
        this.contAppoinmentPhone.setBoundLabelUnderline(true);
        // contInvalidationDate		
        this.contInvalidationDate.setBoundLabelText(resHelper.getString("contInvalidationDate.boundLabelText"));		
        this.contInvalidationDate.setBoundLabelLength(75);		
        this.contInvalidationDate.setBoundLabelUnderline(true);
        // chkIsValid		
        this.chkIsValid.setText(resHelper.getString("chkIsValid.text"));
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(128,128,128),1), resHelper.getString("kDPanel4.border.title")));
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(75);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contSellProNumber		
        this.contSellProNumber.setBoundLabelText(resHelper.getString("contSellProNumber.boundLabelText"));		
        this.contSellProNumber.setBoundLabelLength(75);		
        this.contSellProNumber.setBoundLabelUnderline(true);
        // customerLabel		
        this.customerLabel.setText(resHelper.getString("customerLabel.text"));
        // cusLabel1
        this.cusLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cusLabel1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // cusLabel2
        this.cusLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cusLabel2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // cusLabel3
        this.cusLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cusLabel3_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator8
        // kDSeparator9
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(75);		
        this.contRoom.setBoundLabelUnderline(true);
        // btnSelecCus		
        this.btnSelecCus.setText(resHelper.getString("btnSelecCus.text"));
        this.btnSelecCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelecCus_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cusLabel4
        this.cusLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cusLabel4_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // cusLabel5
        this.cusLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cusLabel5_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(75);		
        this.contCertificateNumber.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        this.pkBizDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtSalesman		
        this.prmtSalesman.setRequired(true);		
        this.prmtSalesman.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.prmtSalesman.setDisplayFormat("$user.name$");		
        this.prmtSalesman.setEditFormat("$user.name$");		
        this.prmtSalesman.setCommitFormat("$user.name$");
        this.prmtSalesman.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSalesman_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSellProject		
        this.prmtSellProject.setRequired(true);		
        this.prmtSellProject.setEnabled(false);		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");
        // txtAppoinmentPeople
        // txtAppoinmentPhone		
        this.txtAppoinmentPhone.setRequired(true);
        // pkInvalidationDate		
        this.pkInvalidationDate.setRequired(true);
        // kdtSincerPriceEntrys
		String kdtSincerPriceEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"revDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefName}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{revDate}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtSincerPriceEntrys.setFormatXml(resHelper.translateString("kdtSincerPriceEntrys",kdtSincerPriceEntrysStrXML));
        this.kdtSincerPriceEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSincerPriceEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDDelLine		
        this.kDDelLine.setText(resHelper.getString("kDDelLine.text"));
        this.kDDelLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDDelLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.kDDelLine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    kDDelLine_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDaddLine		
        this.kDaddLine.setText(resHelper.getString("kDaddLine.text"));
        this.kDaddLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDaddLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.kDaddLine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    kDaddLine_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setEnabled(false);
        // txtSellProNumber		
        this.txtSellProNumber.setRequired(true);
        // prmtRoom		
        this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery");		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setEditFormat("$number$");
        this.prmtRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtRoom.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtRoom_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtCertificateNumber		
        this.txtCertificateNumber.setEnabled(false);
        // kdRoomSinPurRecord
		String kdRoomSinPurRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"cusName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{cusName}</t:Cell><t:Cell>$Resource{sellProNum}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdRoomSinPurRecord.setFormatXml(resHelper.translateString("kdRoomSinPurRecord",kdRoomSinPurRecordStrXML));

        

        // kdChangeNameRecord
		String kdChangeNameRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"oldCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{oldCustomer}</t:Cell><t:Cell>$Resource{newCustomer}</t:Cell><t:Cell>$Resource{changeDate}</t:Cell><t:Cell>$Resource{operator}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdChangeNameRecord.setFormatXml(resHelper.translateString("kdChangeNameRecord",kdChangeNameRecordStrXML));

        

        // btnAddNewCus
        this.btnAddNewCus.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNewCus, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNewCus.setText(resHelper.getString("btnAddNewCus.text"));
        // btnChangeName
        this.btnChangeName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeName.setText(resHelper.getString("btnChangeName.text"));		
        this.btnChangeName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_seeevaluateobject"));
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
        this.setBounds(new Rectangle(10, 10, 585, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 585, 600));
        kDTabbedPane1.setBounds(new Rectangle(10, 10, 569, 581));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 10, 569, 581, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 568, 548));        contCreator.setBounds(new Rectangle(11, 492, 260, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(11, 492, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(11, 517, 260, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(11, 517, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(292, 492, 260, 19));
        kDPanel1.add(contLastUpdateUser, new KDLayout.Constraints(292, 492, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(294, 8, 260, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(294, 8, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(12, 125, 260, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(12, 125, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(12, 182, 546, 64));
        kDPanel1.add(contDescription, new KDLayout.Constraints(12, 182, 546, 64, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSalesman.setBounds(new Rectangle(294, 155, 260, 19));
        kDPanel1.add(contSalesman, new KDLayout.Constraints(294, 155, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(12, 8, 260, 19));
        kDPanel1.add(contSellProject, new KDLayout.Constraints(12, 8, 260, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAppoinmentPeople.setBounds(new Rectangle(12, 67, 260, 19));
        kDPanel1.add(contAppoinmentPeople, new KDLayout.Constraints(12, 67, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAppoinmentPhone.setBounds(new Rectangle(12, 96, 260, 19));
        kDPanel1.add(contAppoinmentPhone, new KDLayout.Constraints(12, 96, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvalidationDate.setBounds(new Rectangle(12, 155, 260, 19));
        kDPanel1.add(contInvalidationDate, new KDLayout.Constraints(12, 155, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsValid.setBounds(new Rectangle(10, 820, 140, 19));
        kDPanel1.add(chkIsValid, new KDLayout.Constraints(10, 820, 140, 19, 0));
        kDPanel4.setBounds(new Rectangle(5, 254, 553, 230));
        kDPanel1.add(kDPanel4, new KDLayout.Constraints(5, 254, 553, 230, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(292, 516, 260, 19));
        kDPanel1.add(contLastUpdateTime, new KDLayout.Constraints(292, 516, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProNumber.setBounds(new Rectangle(294, 125, 260, 19));
        kDPanel1.add(contSellProNumber, new KDLayout.Constraints(294, 125, 260, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        customerLabel.setBounds(new Rectangle(12, 38, 42, 19));
        kDPanel1.add(customerLabel, new KDLayout.Constraints(12, 38, 42, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cusLabel1.setBounds(new Rectangle(86, 37, 59, 19));
        kDPanel1.add(cusLabel1, new KDLayout.Constraints(86, 37, 59, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cusLabel2.setBounds(new Rectangle(151, 37, 58, 19));
        kDPanel1.add(cusLabel2, new KDLayout.Constraints(151, 37, 58, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cusLabel3.setBounds(new Rectangle(214, 37, 57, 19));
        kDPanel1.add(cusLabel3, new KDLayout.Constraints(214, 37, 57, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(84, 57, 319, 10));
        kDPanel1.add(kDSeparator8, new KDLayout.Constraints(84, 57, 319, 10, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator9.setBounds(new Rectangle(12, 58, 29, 8));
        kDPanel1.add(kDSeparator9, new KDLayout.Constraints(12, 58, 29, 8, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(294, 96, 260, 19));
        kDPanel1.add(contRoom, new KDLayout.Constraints(294, 96, 260, 19, 0));
        btnSelecCus.setBounds(new Rectangle(432, 38, 94, 19));
        kDPanel1.add(btnSelecCus, new KDLayout.Constraints(432, 38, 94, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cusLabel4.setBounds(new Rectangle(277, 37, 57, 19));
        kDPanel1.add(cusLabel4, new KDLayout.Constraints(277, 37, 57, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cusLabel5.setBounds(new Rectangle(343, 37, 57, 19));
        kDPanel1.add(cusLabel5, new KDLayout.Constraints(343, 37, 57, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCertificateNumber.setBounds(new Rectangle(294, 67, 260, 19));
        kDPanel1.add(contCertificateNumber, new KDLayout.Constraints(294, 67, 260, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contSalesman
        contSalesman.setBoundEditor(prmtSalesman);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contAppoinmentPeople
        contAppoinmentPeople.setBoundEditor(txtAppoinmentPeople);
        //contAppoinmentPhone
        contAppoinmentPhone.setBoundEditor(txtAppoinmentPhone);
        //contInvalidationDate
        contInvalidationDate.setBoundEditor(pkInvalidationDate);
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(5, 254, 553, 230));        kdtSincerPriceEntrys.setBounds(new Rectangle(10, 45, 527, 173));
        kDPanel4.add(kdtSincerPriceEntrys, new KDLayout.Constraints(10, 45, 527, 173, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDDelLine.setBounds(new Rectangle(446, 13, 90, 19));
        kDPanel4.add(kDDelLine, new KDLayout.Constraints(446, 13, 90, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDaddLine.setBounds(new Rectangle(341, 13, 90, 19));
        kDPanel4.add(kDaddLine, new KDLayout.Constraints(341, 13, 90, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contSellProNumber
        contSellProNumber.setBoundEditor(txtSellProNumber);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNumber);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 568, 548));        kdRoomSinPurRecord.setBounds(new Rectangle(0, 0, 684, 545));
        kDPanel2.add(kdRoomSinPurRecord, new KDLayout.Constraints(0, 0, 684, 545, 0));
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 568, 548));        kdChangeNameRecord.setBounds(new Rectangle(5, 3, 554, 543));
        kDPanel3.add(kdChangeNameRecord, new KDLayout.Constraints(5, 3, 554, 543, 0));

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnAddNewCus);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnChangeName);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isValid", boolean.class, this.chkIsValid, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("salesman", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSalesman, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("appointmentPeople", String.class, this.txtAppoinmentPeople, "text");
		dataBinder.registerBinding("appointmentPhone", String.class, this.txtAppoinmentPhone, "text");
		dataBinder.registerBinding("invalidationDate", java.util.Date.class, this.pkInvalidationDate, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("projectNum", int.class, this.txtSellProNumber, "text");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtRoom, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseChangeNameUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)ov;
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
		getValidateHelper().registerBindProperty("isValid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("salesman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("appointmentPeople", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("appointmentPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invalidationDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output cusLabel1_mouseClicked method
     */
    protected void cusLabel1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output cusLabel2_mouseClicked method
     */
    protected void cusLabel2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output cusLabel3_mouseClicked method
     */
    protected void cusLabel3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnSelecCus_actionPerformed method
     */
    protected void btnSelecCus_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cusLabel4_mouseClicked method
     */
    protected void cusLabel4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output cusLabel5_mouseClicked method
     */
    protected void cusLabel5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output pkBizDate_dataChanged method
     */
    protected void pkBizDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSalesman_dataChanged method
     */
    protected void prmtSalesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtSincerPriceEntrys_editStopped method
     */
    protected void kdtSincerPriceEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kDDelLine_actionPerformed method
     */
    protected void kDDelLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDDelLine_mouseClicked method
     */
    protected void kDDelLine_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kDaddLine_actionPerformed method
     */
    protected void kDaddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDaddLine_mouseClicked method
     */
    protected void kDaddLine_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output prmtRoom_dataChanged method
     */
    protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtRoom_willShow method
     */
    protected void prmtRoom_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isValid"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("salesman.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("appointmentPeople"));
        sic.add(new SelectorItemInfo("appointmentPhone"));
        sic.add(new SelectorItemInfo("invalidationDate"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("projectNum"));
        sic.add(new SelectorItemInfo("room.*"));
        return sic;
    }        
    	

    /**
     * output actionChangeName_actionPerformed method
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddNewCus_actionPerformed method
     */
    public void actionAddNewCus_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionChangeName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeName() {
    	return false;
    }
	public RequestContext prepareActionAddNewCus(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNewCus() {
    	return false;
    }

    /**
     * output ActionChangeName class
     */     
    protected class ActionChangeName extends ItemAction {     
    
        public ActionChangeName()
        {
            this(null);
        }

        public ActionChangeName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseChangeNameUI.this, "ActionChangeName", "actionChangeName_actionPerformed", e);
        }
    }

    /**
     * output ActionAddNewCus class
     */     
    protected class ActionAddNewCus extends ItemAction {     
    
        public ActionAddNewCus()
        {
            this(null);
        }

        public ActionAddNewCus(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddNewCus.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewCus.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewCus.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseChangeNameUI.this, "ActionAddNewCus", "actionAddNewCus_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SincerityPurchaseChangeNameUI");
    }




}