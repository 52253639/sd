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
public abstract class AbstractDefaultAmountQueryUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDefaultAmountQueryUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox prePurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox purchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox sign;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contargDayFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contargDayTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contArgAmountFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contArgAmountTo;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnArgQuery;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancels;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDateTo;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtArgDay;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtargDayTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtArgAmountFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtArgAmountTo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionArgQuery actionArgQuery = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionCancels actionCancels = null;
    /**
     * output class constructor
     */
    public AbstractDefaultAmountQueryUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDefaultAmountQueryUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionArgQuery
        this.actionArgQuery = new ActionArgQuery(this);
        getActionManager().registerAction("actionArgQuery", actionArgQuery);
         this.actionArgQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancels
        this.actionCancels = new ActionCancels(this);
        getActionManager().registerAction("actionCancels", actionCancels);
         this.actionCancels.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prePurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.purchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.sign = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contargDayFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contargDayTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contArgAmountFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contArgAmountTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnArgQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancels = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBizDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtArgDay = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtargDayTo = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtArgAmountFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtArgAmountTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabel1.setName("kDLabel1");
        this.prePurchase.setName("prePurchase");
        this.purchase.setName("purchase");
        this.sign.setName("sign");
        this.contProject.setName("contProject");
        this.contRoom.setName("contRoom");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contBizDateFrom.setName("contBizDateFrom");
        this.contBizDateTo.setName("contBizDateTo");
        this.contargDayFrom.setName("contargDayFrom");
        this.contargDayTo.setName("contargDayTo");
        this.contArgAmountFrom.setName("contArgAmountFrom");
        this.contArgAmountTo.setName("contArgAmountTo");
        this.kDContainer1.setName("kDContainer1");
        this.btnArgQuery.setName("btnArgQuery");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancels.setName("btnCancels");
        this.prmtProject.setName("prmtProject");
        this.prmtRoom.setName("prmtRoom");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.pkBizDateFrom.setName("pkBizDateFrom");
        this.pkBizDateTo.setName("pkBizDateTo");
        this.txtArgDay.setName("txtArgDay");
        this.txtargDayTo.setName("txtargDayTo");
        this.txtArgAmountFrom.setName("txtArgAmountFrom");
        this.txtArgAmountTo.setName("txtArgAmountTo");
        this.kDTable1.setName("kDTable1");
        // CoreUI		
        this.setPreferredSize(new Dimension(1000,600));		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // prePurchase		
        this.prePurchase.setText(resHelper.getString("prePurchase.text"));
        // purchase		
        this.purchase.setText(resHelper.getString("purchase.text"));
        // sign		
        this.sign.setText(resHelper.getString("sign.text"));
        this.sign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    sign_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // contBizDateFrom		
        this.contBizDateFrom.setBoundLabelText(resHelper.getString("contBizDateFrom.boundLabelText"));		
        this.contBizDateFrom.setBoundLabelLength(100);		
        this.contBizDateFrom.setBoundLabelUnderline(true);
        // contBizDateTo		
        this.contBizDateTo.setBoundLabelText(resHelper.getString("contBizDateTo.boundLabelText"));		
        this.contBizDateTo.setBoundLabelLength(100);		
        this.contBizDateTo.setBoundLabelUnderline(true);
        // contargDayFrom		
        this.contargDayFrom.setBoundLabelText(resHelper.getString("contargDayFrom.boundLabelText"));		
        this.contargDayFrom.setBoundLabelLength(100);		
        this.contargDayFrom.setBoundLabelUnderline(true);
        // contargDayTo		
        this.contargDayTo.setBoundLabelText(resHelper.getString("contargDayTo.boundLabelText"));		
        this.contargDayTo.setBoundLabelLength(100);		
        this.contargDayTo.setBoundLabelUnderline(true);
        // contArgAmountFrom		
        this.contArgAmountFrom.setBoundLabelText(resHelper.getString("contArgAmountFrom.boundLabelText"));		
        this.contArgAmountFrom.setBoundLabelLength(100);		
        this.contArgAmountFrom.setBoundLabelUnderline(true);
        // contArgAmountTo		
        this.contArgAmountTo.setBoundLabelText(resHelper.getString("contArgAmountTo.boundLabelText"));		
        this.contArgAmountTo.setBoundLabelLength(100);		
        this.contArgAmountTo.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnArgQuery
        this.btnArgQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionArgQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnArgQuery.setText(resHelper.getString("btnArgQuery.text"));
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // btnCancels
        this.btnCancels.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancels, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancels.setText(resHelper.getString("btnCancels.text"));
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        // prmtRoom		
        this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");		
        this.prmtRoom.setEnabledMultiSelection(true);		
        this.prmtRoom.setCommitFormat("$number$");		
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
        // prmtMoneyDefine		
        this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        // pkBizDateFrom
        this.pkBizDateFrom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDateFrom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkBizDateTo
        this.pkBizDateTo.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDateTo_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtArgDay		
        this.txtArgDay.setSupportedEmpty(true);
        this.txtArgDay.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtArgDay_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtargDayTo		
        this.txtargDayTo.setSupportedEmpty(true);
        this.txtargDayTo.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtargDayTo_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtArgAmountFrom		
        this.txtArgAmountFrom.setSupportedEmpty(true);
        this.txtArgAmountFrom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtArgAmountFrom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtArgAmountTo		
        this.txtArgAmountTo.setSupportedEmpty(true);
        this.txtArgAmountTo.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtArgAmountTo_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"telePhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"argAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"busType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleManNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customerNames}</t:Cell><t:Cell>$Resource{telePhone}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{argAmount}</t:Cell><t:Cell>$Resource{busType}</t:Cell><t:Cell>$Resource{saleManNames}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDTable1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

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
        this.setBounds(new Rectangle(10, 10, 1000, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 600));
        kDLabel1.setBounds(new Rectangle(21, 15, 78, 19));
        this.add(kDLabel1, new KDLayout.Constraints(21, 15, 78, 19, 0));
        prePurchase.setBounds(new Rectangle(104, 15, 58, 19));
        this.add(prePurchase, new KDLayout.Constraints(104, 15, 58, 19, 0));
        purchase.setBounds(new Rectangle(181, 15, 57, 19));
        this.add(purchase, new KDLayout.Constraints(181, 15, 57, 19, 0));
        sign.setBounds(new Rectangle(251, 15, 65, 19));
        this.add(sign, new KDLayout.Constraints(251, 15, 65, 19, 0));
        contProject.setBounds(new Rectangle(366, 15, 270, 19));
        this.add(contProject, new KDLayout.Constraints(366, 15, 270, 19, 0));
        contRoom.setBounds(new Rectangle(21, 43, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(21, 43, 270, 19, 0));
        contMoneyDefine.setBounds(new Rectangle(366, 43, 270, 19));
        this.add(contMoneyDefine, new KDLayout.Constraints(366, 43, 270, 19, 0));
        contBizDateFrom.setBounds(new Rectangle(21, 70, 270, 19));
        this.add(contBizDateFrom, new KDLayout.Constraints(21, 70, 270, 19, 0));
        contBizDateTo.setBounds(new Rectangle(366, 70, 270, 19));
        this.add(contBizDateTo, new KDLayout.Constraints(366, 70, 270, 19, 0));
        contargDayFrom.setBounds(new Rectangle(21, 97, 270, 19));
        this.add(contargDayFrom, new KDLayout.Constraints(21, 97, 270, 19, 0));
        contargDayTo.setBounds(new Rectangle(366, 97, 270, 19));
        this.add(contargDayTo, new KDLayout.Constraints(366, 97, 270, 19, 0));
        contArgAmountFrom.setBounds(new Rectangle(21, 126, 270, 19));
        this.add(contArgAmountFrom, new KDLayout.Constraints(21, 126, 270, 19, 0));
        contArgAmountTo.setBounds(new Rectangle(366, 126, 270, 19));
        this.add(contArgAmountTo, new KDLayout.Constraints(366, 126, 270, 19, 0));
        kDContainer1.setBounds(new Rectangle(18, 180, 969, 376));
        this.add(kDContainer1, new KDLayout.Constraints(18, 180, 969, 376, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnArgQuery.setBounds(new Rectangle(549, 154, 84, 19));
        this.add(btnArgQuery, new KDLayout.Constraints(549, 154, 84, 19, 0));
        btnConfirm.setBounds(new Rectangle(495, 568, 60, 19));
        this.add(btnConfirm, new KDLayout.Constraints(495, 568, 60, 19, 0));
        btnCancels.setBounds(new Rectangle(578, 568, 60, 19));
        this.add(btnCancels, new KDLayout.Constraints(578, 568, 60, 19, 0));
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);
        //contBizDateFrom
        contBizDateFrom.setBoundEditor(pkBizDateFrom);
        //contBizDateTo
        contBizDateTo.setBoundEditor(pkBizDateTo);
        //contargDayFrom
        contargDayFrom.setBoundEditor(txtArgDay);
        //contargDayTo
        contargDayTo.setBoundEditor(txtargDayTo);
        //contArgAmountFrom
        contArgAmountFrom.setBoundEditor(txtArgAmountFrom);
        //contArgAmountTo
        contArgAmountTo.setBoundEditor(txtArgAmountTo);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(18, 180, 969, 376));        kDTable1.setBounds(new Rectangle(4, 3, 946, 352));
        kDContainer1.getContentPane().add(kDTable1, new KDLayout.Constraints(4, 3, 946, 352, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.DefaultAmountQueryUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBillBaseInfo)ov;
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
     * output sign_actionPerformed method
     */
    protected void sign_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtRoom_dataChanged method
     */
    protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1111
    }

    /**
     * output pkBizDateFrom_dataChanged method
     */
    protected void pkBizDateFrom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkBizDateTo_dataChanged method
     */
    protected void pkBizDateTo_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtArgDay_dataChanged method
     */
    protected void txtArgDay_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtargDayTo_dataChanged method
     */
    protected void txtargDayTo_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtArgAmountFrom_dataChanged method
     */
    protected void txtArgAmountFrom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtArgAmountTo_dataChanged method
     */
    protected void txtArgAmountTo_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_tableClicked method
     */
    protected void kDTable1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionArgQuery_actionPerformed method
     */
    public void actionArgQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancels_actionPerformed method
     */
    public void actionCancels_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionArgQuery(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionArgQuery() {
    	return false;
    }
	public RequestContext prepareActionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirm() {
    	return false;
    }
	public RequestContext prepareActionCancels(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancels() {
    	return false;
    }

    /**
     * output ActionArgQuery class
     */     
    protected class ActionArgQuery extends ItemAction {     
    
        public ActionArgQuery()
        {
            this(null);
        }

        public ActionArgQuery(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionArgQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionArgQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionArgQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountQueryUI.this, "ActionArgQuery", "actionArgQuery_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirm class
     */     
    protected class ActionConfirm extends ItemAction {     
    
        public ActionConfirm()
        {
            this(null);
        }

        public ActionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountQueryUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionCancels class
     */     
    protected class ActionCancels extends ItemAction {     
    
        public ActionCancels()
        {
            this(null);
        }

        public ActionCancels(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancels.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancels.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancels.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDefaultAmountQueryUI.this, "ActionCancels", "actionCancels_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "DefaultAmountQueryUI");
    }




}