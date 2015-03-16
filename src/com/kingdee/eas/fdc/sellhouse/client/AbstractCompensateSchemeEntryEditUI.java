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
public abstract class AbstractCompensateSchemeEntryEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCompensateSchemeEntryEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSeq;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSeq;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsCompensate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboIsCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelTerm;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinValue;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbMinValueCompare;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbMaxValueCompare;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxValue;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFactor;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFactor;
    protected com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCompensateSchemeEntryEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCompensateSchemeEntryEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSeq = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSeq = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contIsCompensate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboIsCompensate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancle = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelTerm = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtMinValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cmbMinValueCompare = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cmbMaxValueCompare = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtMaxValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFactor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFactor = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contSeq.setName("contSeq");
        this.txtSeq.setName("txtSeq");
        this.contIsCompensate.setName("contIsCompensate");
        this.comboIsCompensate.setName("comboIsCompensate");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.kDSeparator5.setName("kDSeparator5");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancle.setName("btnCancle");
        this.contSellProject.setName("contSellProject");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.labelTerm.setName("labelTerm");
        this.txtMinValue.setName("txtMinValue");
        this.kDLabel1.setName("kDLabel1");
        this.cmbMinValueCompare.setName("cmbMinValueCompare");
        this.kDLabel2.setName("kDLabel2");
        this.cmbMaxValueCompare.setName("cmbMaxValueCompare");
        this.kDLabel3.setName("kDLabel3");
        this.txtMaxValue.setName("txtMaxValue");
        this.kDLabel4.setName("kDLabel4");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.contFactor.setName("contFactor");
        this.txtFactor.setName("txtFactor");
        // CoreUI		
        this.setPreferredSize(new Dimension(490,335));
        // contSeq		
        this.contSeq.setBoundLabelText(resHelper.getString("contSeq.boundLabelText"));		
        this.contSeq.setBoundLabelLength(100);		
        this.contSeq.setBoundLabelUnderline(true);		
        this.contSeq.setEnabled(false);
        // txtSeq		
        this.txtSeq.setEnabled(false);
        // contIsCompensate		
        this.contIsCompensate.setBoundLabelText(resHelper.getString("contIsCompensate.boundLabelText"));		
        this.contIsCompensate.setBoundLabelLength(100);		
        this.contIsCompensate.setBoundLabelUnderline(true);
        // comboIsCompensate		
        this.comboIsCompensate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.BooleanEnum").toArray());		
        this.comboIsCompensate.setRequired(true);
        this.comboIsCompensate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    comboIsCompensate_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // kDSeparator5
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
        // btnCancle		
        this.btnCancle.setText(resHelper.getString("btnCancle.text"));
        this.btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancle_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);
        // labelTerm		
        this.labelTerm.setText(resHelper.getString("labelTerm.text"));
        // txtMinValue		
        this.txtMinValue.setDataType(1);		
        this.txtMinValue.setPrecision(2);		
        this.txtMinValue.setRequired(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setForeground(new java.awt.Color(0,0,255));
        // cmbMinValueCompare		
        this.cmbMinValueCompare.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum").toArray());		
        this.cmbMinValueCompare.setRequired(true);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setForeground(new java.awt.Color(0,0,255));
        // cmbMaxValueCompare		
        this.cmbMaxValueCompare.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum").toArray());		
        this.cmbMaxValueCompare.setRequired(true);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));		
        this.kDLabel3.setForeground(new java.awt.Color(0,0,255));
        // txtMaxValue		
        this.txtMaxValue.setDataType(1);		
        this.txtMaxValue.setPrecision(2);		
        this.txtMaxValue.setRequired(true);
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));		
        this.kDLabel4.setForeground(new java.awt.Color(0,0,255));
        // prmtSellProject		
        this.prmtSellProject.setEnabled(false);
        // txtNumber		
        this.txtNumber.setEnabled(false);
        // txtName		
        this.txtName.setEnabled(false);
        // contFactor		
        this.contFactor.setBoundLabelText(resHelper.getString("contFactor.boundLabelText"));		
        this.contFactor.setBoundLabelLength(340);
        // txtFactor		
        this.txtFactor.setRequired(true);		
        this.txtFactor.setDataType(1);
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
        this.setBounds(new Rectangle(10, 10, 490, 335));
        this.setLayout(null);
        contSeq.setBounds(new Rectangle(10, 10, 470, 19));
        this.add(contSeq, null);
        contIsCompensate.setBounds(new Rectangle(10, 205, 470, 19));
        this.add(contIsCompensate, null);
        contDescription.setBounds(new Rectangle(10, 230, 470, 19));
        this.add(contDescription, null);
        kDSeparator5.setBounds(new Rectangle(10, 280, 470, 5));
        this.add(kDSeparator5, null);
        btnConfirm.setBounds(new Rectangle(300, 300, 73, 21));
        this.add(btnConfirm, null);
        btnCancle.setBounds(new Rectangle(390, 300, 73, 21));
        this.add(btnCancle, null);
        contSellProject.setBounds(new Rectangle(10, 35, 470, 19));
        this.add(contSellProject, null);
        contNumber.setBounds(new Rectangle(10, 80, 470, 19));
        this.add(contNumber, null);
        contName.setBounds(new Rectangle(10, 105, 470, 19));
        this.add(contName, null);
        labelTerm.setBounds(new Rectangle(10, 130, 100, 19));
        this.add(labelTerm, null);
        txtMinValue.setBounds(new Rectangle(110, 130, 80, 19));
        this.add(txtMinValue, null);
        kDLabel1.setBounds(new Rectangle(190, 130, 20, 19));
        this.add(kDLabel1, null);
        cmbMinValueCompare.setBounds(new Rectangle(210, 130, 60, 19));
        this.add(cmbMinValueCompare, null);
        kDLabel2.setBounds(new Rectangle(270, 130, 60, 19));
        this.add(kDLabel2, null);
        cmbMaxValueCompare.setBounds(new Rectangle(330, 130, 60, 19));
        this.add(cmbMaxValueCompare, null);
        kDLabel3.setBounds(new Rectangle(470, 130, 20, 19));
        this.add(kDLabel3, null);
        txtMaxValue.setBounds(new Rectangle(390, 130, 80, 19));
        this.add(txtMaxValue, null);
        kDLabel4.setBounds(new Rectangle(10, 155, 270, 19));
        this.add(kDLabel4, null);
        contFactor.setBounds(new Rectangle(10, 178, 470, 19));
        this.add(contFactor, null);
        //contSeq
        contSeq.setBoundEditor(txtSeq);
        //contIsCompensate
        contIsCompensate.setBoundEditor(comboIsCompensate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contFactor
        contFactor.setBoundEditor(txtFactor);

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
        this.toolBar.add(btnReset);
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
		dataBinder.registerBinding("seq", int.class, this.txtSeq, "value");
		dataBinder.registerBinding("isCompensate", com.kingdee.eas.fdc.sellhouse.BooleanEnum.class, this.comboIsCompensate, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("minValue", java.math.BigDecimal.class, this.txtMinValue, "value");
		dataBinder.registerBinding("minCompareType", com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum.class, this.cmbMinValueCompare, "selectedItem");
		dataBinder.registerBinding("maxCompareType", com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum.class, this.cmbMaxValueCompare, "selectedItem");
		dataBinder.registerBinding("maxValue", java.math.BigDecimal.class, this.txtMaxValue, "value");
		dataBinder.registerBinding("factor", java.math.BigDecimal.class, this.txtFactor, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CompensateSchemeEntryEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryInfo)ov;
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
		getValidateHelper().registerBindProperty("seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minCompareType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxCompareType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factor", ValidateHelper.ON_SAVE);    		
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
     * output comboIsCompensate_propertyChange method
     */
    protected void comboIsCompensate_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancle_actionPerformed method
     */
    protected void btnCancle_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("seq"));
        sic.add(new SelectorItemInfo("isCompensate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("minValue"));
        sic.add(new SelectorItemInfo("minCompareType"));
        sic.add(new SelectorItemInfo("maxCompareType"));
        sic.add(new SelectorItemInfo("maxValue"));
        sic.add(new SelectorItemInfo("factor"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CompensateSchemeEntryEditUI");
    }




}