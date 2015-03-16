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
public abstract class AbstractConditionGatherFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractConditionGatherFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGatherType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comRevBillType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7MoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7SellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7SettlementType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7Building;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7Unit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7AccountBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comGatherType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizEndDate;
    /**
     * output class constructor
     */
    public AbstractConditionGatherFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractConditionGatherFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRevBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGatherType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comRevBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.F7MoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.F7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.F7SettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.F7Building = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.F7Unit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.F7AccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBankNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comGatherType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBizEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contRevBillType.setName("contRevBillType");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contSellProject.setName("contSellProject");
        this.contSettlementType.setName("contSettlementType");
        this.contBuilding.setName("contBuilding");
        this.contUnit.setName("contUnit");
        this.contAccountBank.setName("contAccountBank");
        this.contBankNumber.setName("contBankNumber");
        this.contGatherType.setName("contGatherType");
        this.kDPanel1.setName("kDPanel1");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.comRevBillType.setName("comRevBillType");
        this.F7MoneyDefine.setName("F7MoneyDefine");
        this.F7SellProject.setName("F7SellProject");
        this.F7SettlementType.setName("F7SettlementType");
        this.F7Building.setName("F7Building");
        this.F7Unit.setName("F7Unit");
        this.F7AccountBank.setName("F7AccountBank");
        this.txtBankNumber.setName("txtBankNumber");
        this.comGatherType.setName("comGatherType");
        this.contBizDate.setName("contBizDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.pkBizStartDate.setName("pkBizStartDate");
        this.pkBizEndDate.setName("pkBizEndDate");
        // CustomerQueryPanel		
        this.setBorder(null);
        // contRevBillType		
        this.contRevBillType.setBoundLabelText(resHelper.getString("contRevBillType.boundLabelText"));		
        this.contRevBillType.setBoundLabelLength(100);		
        this.contRevBillType.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contUnit		
        this.contUnit.setBoundLabelText(resHelper.getString("contUnit.boundLabelText"));		
        this.contUnit.setBoundLabelLength(100);		
        this.contUnit.setBoundLabelUnderline(true);
        // contAccountBank		
        this.contAccountBank.setBoundLabelText(resHelper.getString("contAccountBank.boundLabelText"));		
        this.contAccountBank.setBoundLabelLength(100);		
        this.contAccountBank.setBoundLabelUnderline(true);
        // contBankNumber		
        this.contBankNumber.setBoundLabelText(resHelper.getString("contBankNumber.boundLabelText"));		
        this.contBankNumber.setBoundLabelLength(100);		
        this.contBankNumber.setBoundLabelUnderline(true);
        // contGatherType		
        this.contGatherType.setBoundLabelText(resHelper.getString("contGatherType.boundLabelText"));		
        this.contGatherType.setBoundLabelLength(100);		
        this.contGatherType.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // btnConfirm		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        this.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        this.btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comRevBillType		
        this.comRevBillType.setActionCommand("comRevBillType");		
        this.comRevBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RevBillTypeEnum").toArray());
        this.comRevBillType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comRevBillType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.comRevBillType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comRevBillType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // F7MoneyDefine		
        this.F7MoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        // F7SellProject		
        this.F7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CRMSellProjectQuery");
        // F7SettlementType		
        this.F7SettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
        // F7Building		
        this.F7Building.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");
        this.F7Building.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    F7Building_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // F7Unit		
        this.F7Unit.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingUnitQuery");
        // F7AccountBank		
        this.F7AccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");
        // txtBankNumber
        // comGatherType		
        this.comGatherType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.GatherTypeEnum").toArray());
        this.comGatherType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comGatherType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(40);		
        this.contBizDate.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(40);
        // pkBizStartDate
        // pkBizEndDate
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
        this.setBounds(new Rectangle(10, 10, 650, 300));
        this.setLayout(null);
        contRevBillType.setBounds(new Rectangle(9, 9, 270, 19));
        this.add(contRevBillType, null);
        contMoneyDefine.setBounds(new Rectangle(9, 64, 270, 19));
        this.add(contMoneyDefine, null);
        contSellProject.setBounds(new Rectangle(349, 9, 270, 19));
        this.add(contSellProject, null);
        contSettlementType.setBounds(new Rectangle(349, 61, 270, 19));
        this.add(contSettlementType, null);
        contBuilding.setBounds(new Rectangle(9, 50, 270, 19));
        this.add(contBuilding, null);
        contUnit.setBounds(new Rectangle(349, 50, 270, 19));
        this.add(contUnit, null);
        contAccountBank.setBounds(new Rectangle(9, 114, 270, 19));
        this.add(contAccountBank, null);
        contBankNumber.setBounds(new Rectangle(349, 113, 270, 19));
        this.add(contBankNumber, null);
        contGatherType.setBounds(new Rectangle(9, 238, 270, 19));
        this.add(contGatherType, null);
        kDPanel1.setBounds(new Rectangle(9, 160, 611, 59));
        this.add(kDPanel1, null);
        btnConfirm.setBounds(new Rectangle(484, 237, 68, 19));
        this.add(btnConfirm, null);
        btnCancel.setBounds(new Rectangle(555, 237, 65, 19));
        this.add(btnCancel, null);
        //contRevBillType
        contRevBillType.setBoundEditor(comRevBillType);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(F7MoneyDefine);
        //contSellProject
        contSellProject.setBoundEditor(F7SellProject);
        //contSettlementType
        contSettlementType.setBoundEditor(F7SettlementType);
        //contBuilding
        contBuilding.setBoundEditor(F7Building);
        //contUnit
        contUnit.setBoundEditor(F7Unit);
        //contAccountBank
        contAccountBank.setBoundEditor(F7AccountBank);
        //contBankNumber
        contBankNumber.setBoundEditor(txtBankNumber);
        //contGatherType
        contGatherType.setBoundEditor(comGatherType);
        //kDPanel1
        kDPanel1.setLayout(null);        contBizDate.setBounds(new Rectangle(15, 20, 250, 19));
        kDPanel1.add(contBizDate, null);
        kDLabelContainer1.setBounds(new Rectangle(346, 19, 250, 19));
        kDPanel1.add(kDLabelContainer1, null);
        //contBizDate
        contBizDate.setBoundEditor(pkBizStartDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkBizEndDate);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ConditionGatherFilterUIHandler";
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
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comRevBillType_actionPerformed method
     */
    protected void comRevBillType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comRevBillType_itemStateChanged method
     */
    protected void comRevBillType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output F7Building_dataChanged method
     */
    protected void F7Building_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comGatherType_actionPerformed method
     */
    protected void comGatherType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ConditionGatherFilterUI");
    }




}