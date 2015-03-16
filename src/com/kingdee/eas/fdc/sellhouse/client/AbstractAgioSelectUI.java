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
public abstract class AbstractAgioSelectUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAgioSelectUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUp;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDown;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAutoToInteger;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDigit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceAccount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsBasePriceSell;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialAgio;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSpecialAgio;
    protected ActionYes actionYes = null;
    protected ActionNo actionNo = null;
    /**
     * output class constructor
     */
    public AbstractAgioSelectUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAgioSelectUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionYes
        this.actionYes = new ActionYes(this);
        getActionManager().registerAction("actionYes", actionYes);
         this.actionYes.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNo
        this.actionNo = new ActionNo(this);
        getActionManager().registerAction("actionNo", actionNo);
         this.actionNo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblSelect = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnUp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIsAutoToInteger = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contToIntegerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDigit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsBasePriceSell = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSpecialAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRelate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboToIntegerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboPriceAccount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSpecialAgio = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblSelect.setName("tblSelect");
        this.btnUp.setName("btnUp");
        this.btnDown.setName("btnDown");
        this.chkIsAutoToInteger.setName("chkIsAutoToInteger");
        this.contToIntegerType.setName("contToIntegerType");
        this.contDigit.setName("contDigit");
        this.contPriceAccount.setName("contPriceAccount");
        this.chkIsBasePriceSell.setName("chkIsBasePriceSell");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.contSpecialAgio.setName("contSpecialAgio");
        this.btnRelate.setName("btnRelate");
        this.comboToIntegerType.setName("comboToIntegerType");
        this.comboDigit.setName("comboDigit");
        this.comboPriceAccount.setName("comboPriceAccount");
        this.prmtSpecialAgio.setName("prmtSpecialAgio");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,400));
        // tblSelect
		String tblSelectStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"agioName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"calType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pro\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"cancelDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"isEspecial\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{agioName}</t:Cell><t:Cell>$Resource{calType}</t:Cell><t:Cell>$Resource{pro}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{operate}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{cancelDate}</t:Cell><t:Cell>$Resource{isEspecial}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSelect.setFormatXml(resHelper.translateString("tblSelect",tblSelectStrXML));
        this.tblSelect.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblSelect_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblSelect.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblSelect_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnUp		
        this.btnUp.setText(resHelper.getString("btnUp.text"));
        this.btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUp_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDown		
        this.btnDown.setText(resHelper.getString("btnDown.text"));
        this.btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDown_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsAutoToInteger		
        this.chkIsAutoToInteger.setText(resHelper.getString("chkIsAutoToInteger.text"));		
        this.chkIsAutoToInteger.setVisible(false);
        this.chkIsAutoToInteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAutoToInteger_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contToIntegerType		
        this.contToIntegerType.setBoundLabelText(resHelper.getString("contToIntegerType.boundLabelText"));		
        this.contToIntegerType.setBoundLabelLength(100);		
        this.contToIntegerType.setBoundLabelUnderline(true);		
        this.contToIntegerType.setVisible(false);
        // contDigit		
        this.contDigit.setBoundLabelText(resHelper.getString("contDigit.boundLabelText"));		
        this.contDigit.setBoundLabelLength(100);		
        this.contDigit.setBoundLabelUnderline(true);		
        this.contDigit.setVisible(false);
        // contPriceAccount		
        this.contPriceAccount.setBoundLabelText(resHelper.getString("contPriceAccount.boundLabelText"));		
        this.contPriceAccount.setBoundLabelLength(80);		
        this.contPriceAccount.setBoundLabelUnderline(true);		
        this.contPriceAccount.setVisible(false);
        // chkIsBasePriceSell		
        this.chkIsBasePriceSell.setText(resHelper.getString("chkIsBasePriceSell.text"));		
        this.chkIsBasePriceSell.setVisible(false);
        this.chkIsBasePriceSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsBasePriceSell_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnYes
        this.btnYes.setAction((IItemAction)ActionProxyFactory.getProxy(actionYes, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        // btnNo
        this.btnNo.setAction((IItemAction)ActionProxyFactory.getProxy(actionNo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        // contSpecialAgio		
        this.contSpecialAgio.setBoundLabelText(resHelper.getString("contSpecialAgio.boundLabelText"));		
        this.contSpecialAgio.setBoundLabelLength(100);		
        this.contSpecialAgio.setBoundLabelUnderline(true);
        // btnRelate		
        this.btnRelate.setText(resHelper.getString("btnRelate.text"));
        this.btnRelate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRelate_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comboToIntegerType		
        this.comboToIntegerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum").toArray());		
        this.comboToIntegerType.setVisible(false);
        // comboDigit		
        this.comboDigit.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.DigitEnum").toArray());		
        this.comboDigit.setVisible(false);
        // comboPriceAccount		
        this.comboPriceAccount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum").toArray());		
        this.comboPriceAccount.setVisible(false);
        // prmtSpecialAgio		
        this.prmtSpecialAgio.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SpecialDiscountQuery");		
        this.prmtSpecialAgio.setDisplayFormat("$unitPriceName$");		
        this.prmtSpecialAgio.setEditFormat("$number$");		
        this.prmtSpecialAgio.setCommitFormat("$number$");
        this.prmtSpecialAgio.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSpecialAgio_dataChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 700, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 400));
        tblSelect.setBounds(new Rectangle(9, 7, 578, 325));
        this.add(tblSelect, new KDLayout.Constraints(9, 7, 578, 325, 0));
        btnUp.setBounds(new Rectangle(620, 104, 62, 33));
        this.add(btnUp, new KDLayout.Constraints(620, 104, 62, 33, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDown.setBounds(new Rectangle(620, 180, 62, 33));
        this.add(btnDown, new KDLayout.Constraints(620, 180, 62, 33, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsAutoToInteger.setBounds(new Rectangle(646, 355, 110, 19));
        this.add(chkIsAutoToInteger, new KDLayout.Constraints(646, 355, 110, 19, 0));
        contToIntegerType.setBounds(new Rectangle(650, 307, 270, 19));
        this.add(contToIntegerType, new KDLayout.Constraints(650, 307, 270, 19, 0));
        contDigit.setBounds(new Rectangle(647, 331, 270, 19));
        this.add(contDigit, new KDLayout.Constraints(647, 331, 270, 19, 0));
        contPriceAccount.setBounds(new Rectangle(644, 289, 220, 19));
        this.add(contPriceAccount, new KDLayout.Constraints(644, 289, 220, 19, 0));
        chkIsBasePriceSell.setBounds(new Rectangle(643, 370, 117, 19));
        this.add(chkIsBasePriceSell, new KDLayout.Constraints(643, 370, 117, 19, 0));
        btnYes.setBounds(new Rectangle(417, 342, 78, 19));
        this.add(btnYes, new KDLayout.Constraints(417, 342, 78, 19, 0));
        btnNo.setBounds(new Rectangle(509, 342, 78, 19));
        this.add(btnNo, new KDLayout.Constraints(509, 342, 78, 19, 0));
        contSpecialAgio.setBounds(new Rectangle(9, 342, 270, 19));
        this.add(contSpecialAgio, new KDLayout.Constraints(9, 342, 270, 19, 0));
        btnRelate.setBounds(new Rectangle(292, 342, 86, 19));
        this.add(btnRelate, new KDLayout.Constraints(292, 342, 86, 19, 0));
        //contToIntegerType
        contToIntegerType.setBoundEditor(comboToIntegerType);
        //contDigit
        contDigit.setBoundEditor(comboDigit);
        //contPriceAccount
        contPriceAccount.setBoundEditor(comboPriceAccount);
        //contSpecialAgio
        contSpecialAgio.setBoundEditor(prmtSpecialAgio);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.AgioSelectUIHandler";
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
     * output tblSelect_editValueChanged method
     */
    protected void tblSelect_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblSelect_tableClicked method
     */
    protected void tblSelect_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnUp_actionPerformed method
     */
    protected void btnUp_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDown_actionPerformed method
     */
    protected void btnDown_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsAutoToInteger_actionPerformed method
     */
    protected void chkIsAutoToInteger_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsBasePriceSell_actionPerformed method
     */
    protected void chkIsBasePriceSell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRelate_actionPerformed method
     */
    protected void btnRelate_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtSpecialAgio_dataChanged method
     */
    protected void prmtSpecialAgio_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    	

    /**
     * output actionYes_actionPerformed method
     */
    public void actionYes_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNo_actionPerformed method
     */
    public void actionNo_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionYes(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionYes() {
    	return false;
    }
	public RequestContext prepareActionNo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNo() {
    	return false;
    }

    /**
     * output ActionYes class
     */     
    protected class ActionYes extends ItemAction {     
    
        public ActionYes()
        {
            this(null);
        }

        public ActionYes(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionYes.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionYes.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionYes.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAgioSelectUI.this, "ActionYes", "actionYes_actionPerformed", e);
        }
    }

    /**
     * output ActionNo class
     */     
    protected class ActionNo extends ItemAction {     
    
        public ActionNo()
        {
            this(null);
        }

        public ActionNo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAgioSelectUI.this, "ActionNo", "actionNo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "AgioSelectUI");
    }




}