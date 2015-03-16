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
public abstract class AbstractSaleBlanceSettingUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSaleBlanceSettingUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSaleBlanceCheck;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoAuditPur;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoApplyPrePur;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstAmountPur;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoAuditQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCanRefundmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoAuditPurChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoAuditChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanNoRev;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccFundNoRev;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSincer;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNoAuditPur;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNoApplyPrePur;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFirstAmountPur;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNoAuditQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCanRefundmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNoAuditPurChange;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNoAuditChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboLoanNoRev;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAccFundNoRev;
    /**
     * output class constructor
     */
    public AbstractSaleBlanceSettingUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSaleBlanceSettingUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pnlSaleBlanceCheck = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSincer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoAuditPur = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoApplyPrePur = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstAmountPur = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoAuditQuitRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCanRefundmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoAuditPurChange = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoAuditChangeRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanNoRev = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccFundNoRev = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSincer = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboNoAuditPur = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboNoApplyPrePur = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboFirstAmountPur = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboNoAuditQuitRoom = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboCanRefundmentAmount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboNoAuditPurChange = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboNoAuditChangeRoom = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboLoanNoRev = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboAccFundNoRev = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.pnlSaleBlanceCheck.setName("pnlSaleBlanceCheck");
        this.contSincer.setName("contSincer");
        this.contNoAuditPur.setName("contNoAuditPur");
        this.contNoApplyPrePur.setName("contNoApplyPrePur");
        this.contFirstAmountPur.setName("contFirstAmountPur");
        this.contNoAuditQuitRoom.setName("contNoAuditQuitRoom");
        this.contCanRefundmentAmount.setName("contCanRefundmentAmount");
        this.contNoAuditPurChange.setName("contNoAuditPurChange");
        this.contNoAuditChangeRoom.setName("contNoAuditChangeRoom");
        this.contLoanNoRev.setName("contLoanNoRev");
        this.contAccFundNoRev.setName("contAccFundNoRev");
        this.comboSincer.setName("comboSincer");
        this.comboNoAuditPur.setName("comboNoAuditPur");
        this.comboNoApplyPrePur.setName("comboNoApplyPrePur");
        this.comboFirstAmountPur.setName("comboFirstAmountPur");
        this.comboNoAuditQuitRoom.setName("comboNoAuditQuitRoom");
        this.comboCanRefundmentAmount.setName("comboCanRefundmentAmount");
        this.comboNoAuditPurChange.setName("comboNoAuditPurChange");
        this.comboNoAuditChangeRoom.setName("comboNoAuditChangeRoom");
        this.comboLoanNoRev.setName("comboLoanNoRev");
        this.comboAccFundNoRev.setName("comboAccFundNoRev");
        // CoreUI
        // kDTabbedPane1
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
        // pnlSaleBlanceCheck
        // contSincer		
        this.contSincer.setBoundLabelText(resHelper.getString("contSincer.boundLabelText"));		
        this.contSincer.setBoundLabelLength(120);		
        this.contSincer.setBoundLabelUnderline(true);
        // contNoAuditPur		
        this.contNoAuditPur.setBoundLabelText(resHelper.getString("contNoAuditPur.boundLabelText"));		
        this.contNoAuditPur.setBoundLabelLength(120);		
        this.contNoAuditPur.setBoundLabelUnderline(true);
        // contNoApplyPrePur		
        this.contNoApplyPrePur.setBoundLabelText(resHelper.getString("contNoApplyPrePur.boundLabelText"));		
        this.contNoApplyPrePur.setBoundLabelLength(120);		
        this.contNoApplyPrePur.setBoundLabelUnderline(true);
        // contFirstAmountPur		
        this.contFirstAmountPur.setBoundLabelText(resHelper.getString("contFirstAmountPur.boundLabelText"));		
        this.contFirstAmountPur.setBoundLabelLength(120);		
        this.contFirstAmountPur.setBoundLabelUnderline(true);
        // contNoAuditQuitRoom		
        this.contNoAuditQuitRoom.setBoundLabelText(resHelper.getString("contNoAuditQuitRoom.boundLabelText"));		
        this.contNoAuditQuitRoom.setBoundLabelLength(120);		
        this.contNoAuditQuitRoom.setBoundLabelUnderline(true);
        // contCanRefundmentAmount		
        this.contCanRefundmentAmount.setBoundLabelText(resHelper.getString("contCanRefundmentAmount.boundLabelText"));		
        this.contCanRefundmentAmount.setBoundLabelLength(120);		
        this.contCanRefundmentAmount.setBoundLabelUnderline(true);
        // contNoAuditPurChange		
        this.contNoAuditPurChange.setBoundLabelText(resHelper.getString("contNoAuditPurChange.boundLabelText"));		
        this.contNoAuditPurChange.setBoundLabelLength(120);		
        this.contNoAuditPurChange.setBoundLabelUnderline(true);
        // contNoAuditChangeRoom		
        this.contNoAuditChangeRoom.setBoundLabelText(resHelper.getString("contNoAuditChangeRoom.boundLabelText"));		
        this.contNoAuditChangeRoom.setBoundLabelLength(120);		
        this.contNoAuditChangeRoom.setBoundLabelUnderline(true);
        // contLoanNoRev		
        this.contLoanNoRev.setBoundLabelText(resHelper.getString("contLoanNoRev.boundLabelText"));		
        this.contLoanNoRev.setBoundLabelLength(220);		
        this.contLoanNoRev.setBoundLabelUnderline(true);
        // contAccFundNoRev		
        this.contAccFundNoRev.setBoundLabelText(resHelper.getString("contAccFundNoRev.boundLabelText"));		
        this.contAccFundNoRev.setBoundLabelLength(230);		
        this.contAccFundNoRev.setBoundLabelUnderline(true);
        // comboSincer		
        this.comboSincer.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboNoAuditPur		
        this.comboNoAuditPur.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboNoApplyPrePur		
        this.comboNoApplyPrePur.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboFirstAmountPur		
        this.comboFirstAmountPur.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboNoAuditQuitRoom		
        this.comboNoAuditQuitRoom.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboCanRefundmentAmount		
        this.comboCanRefundmentAmount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboNoAuditPurChange		
        this.comboNoAuditPurChange.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboNoAuditChangeRoom		
        this.comboNoAuditChangeRoom.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboLoanNoRev		
        this.comboLoanNoRev.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
        // comboAccFundNoRev		
        this.comboAccFundNoRev.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum").toArray());
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
        this.setBounds(new Rectangle(10, 10, 850, 350));
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(10, 10, 820, 279));
        this.add(kDTabbedPane1, null);
        btnConfirm.setBounds(new Rectangle(349, 306, 68, 19));
        this.add(btnConfirm, null);
        btnCancel.setBounds(new Rectangle(436, 306, 67, 19));
        this.add(btnCancel, null);
        //kDTabbedPane1
        kDTabbedPane1.add(pnlSaleBlanceCheck, resHelper.getString("pnlSaleBlanceCheck.constraints"));
        //pnlSaleBlanceCheck
        pnlSaleBlanceCheck.setLayout(null);        contSincer.setBounds(new Rectangle(15, 17, 380, 19));
        pnlSaleBlanceCheck.add(contSincer, null);
        contNoAuditPur.setBounds(new Rectangle(430, 16, 380, 19));
        pnlSaleBlanceCheck.add(contNoAuditPur, null);
        contNoApplyPrePur.setBounds(new Rectangle(16, 62, 380, 19));
        pnlSaleBlanceCheck.add(contNoApplyPrePur, null);
        contFirstAmountPur.setBounds(new Rectangle(431, 61, 380, 19));
        pnlSaleBlanceCheck.add(contFirstAmountPur, null);
        contNoAuditQuitRoom.setBounds(new Rectangle(16, 110, 380, 19));
        pnlSaleBlanceCheck.add(contNoAuditQuitRoom, null);
        contCanRefundmentAmount.setBounds(new Rectangle(430, 108, 380, 19));
        pnlSaleBlanceCheck.add(contCanRefundmentAmount, null);
        contNoAuditPurChange.setBounds(new Rectangle(16, 153, 380, 19));
        pnlSaleBlanceCheck.add(contNoAuditPurChange, null);
        contNoAuditChangeRoom.setBounds(new Rectangle(429, 154, 380, 19));
        pnlSaleBlanceCheck.add(contNoAuditChangeRoom, null);
        contLoanNoRev.setBounds(new Rectangle(15, 190, 380, 19));
        pnlSaleBlanceCheck.add(contLoanNoRev, null);
        contAccFundNoRev.setBounds(new Rectangle(427, 188, 380, 19));
        pnlSaleBlanceCheck.add(contAccFundNoRev, null);
        //contSincer
        contSincer.setBoundEditor(comboSincer);
        //contNoAuditPur
        contNoAuditPur.setBoundEditor(comboNoAuditPur);
        //contNoApplyPrePur
        contNoApplyPrePur.setBoundEditor(comboNoApplyPrePur);
        //contFirstAmountPur
        contFirstAmountPur.setBoundEditor(comboFirstAmountPur);
        //contNoAuditQuitRoom
        contNoAuditQuitRoom.setBoundEditor(comboNoAuditQuitRoom);
        //contCanRefundmentAmount
        contCanRefundmentAmount.setBoundEditor(comboCanRefundmentAmount);
        //contNoAuditPurChange
        contNoAuditPurChange.setBoundEditor(comboNoAuditPurChange);
        //contNoAuditChangeRoom
        contNoAuditChangeRoom.setBoundEditor(comboNoAuditChangeRoom);
        //contLoanNoRev
        contLoanNoRev.setBoundEditor(comboLoanNoRev);
        //contAccFundNoRev
        contAccFundNoRev.setBoundEditor(comboAccFundNoRev);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SaleBlanceSettingUIHandler";
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SaleBlanceSettingUI");
    }




}