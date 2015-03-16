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
public abstract class AbstractBatchAdjustColUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchAdjustColUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdContainer;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kdRadioGroup;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioColInc;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioColAdd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIncFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIncTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIncFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboIncSymbol;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAddSymbol;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIncYuan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIncYuan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddYuan;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAddYuan;
    protected com.kingdee.bos.ctrl.swing.KDLabel labTip;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAddFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAddTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIncFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIncTo;
    /**
     * output class constructor
     */
    public AbstractBatchAdjustColUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchAdjustColUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdRadioGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.radioColInc = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioColAdd = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contAddFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIncFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIncTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIncFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboIncSymbol = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboAddSymbol = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtIncYuan = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contIncYuan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddYuan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAddYuan = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.labTip = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtAddFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAddTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIncFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIncTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.kDPanel1.setName("kDPanel1");
        this.kdContainer.setName("kdContainer");
        this.radioColInc.setName("radioColInc");
        this.radioColAdd.setName("radioColAdd");
        this.contAddFrom.setName("contAddFrom");
        this.contAddTo.setName("contAddTo");
        this.contIncFrom.setName("contIncFrom");
        this.contIncTo.setName("contIncTo");
        this.contIncFloor.setName("contIncFloor");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.comboIncSymbol.setName("comboIncSymbol");
        this.comboAddSymbol.setName("comboAddSymbol");
        this.txtIncYuan.setName("txtIncYuan");
        this.contIncYuan.setName("contIncYuan");
        this.contAddYuan.setName("contAddYuan");
        this.txtAddYuan.setName("txtAddYuan");
        this.labTip.setName("labTip");
        this.txtAddFrom.setName("txtAddFrom");
        this.txtAddTo.setName("txtAddTo");
        this.txtIncFrom.setName("txtIncFrom");
        this.txtIncTo.setName("txtIncTo");
        // CoreUI
        // btnYes		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        this.btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDPanel1
        // kdContainer		
        this.kdContainer.setToolTipText(resHelper.getString("kdContainer.toolTipText"));		
        this.kdContainer.setTitle(resHelper.getString("kdContainer.title"));
        // kdRadioGroup
        this.kdRadioGroup.add(this.radioColInc);
        this.kdRadioGroup.add(this.radioColAdd);
        // radioColInc		
        this.radioColInc.setText(resHelper.getString("radioColInc.text"));
        this.radioColInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioColInc_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioColAdd		
        this.radioColAdd.setText(resHelper.getString("radioColAdd.text"));
        this.radioColAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioColAdd_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contAddFrom		
        this.contAddFrom.setBoundLabelText(resHelper.getString("contAddFrom.boundLabelText"));		
        this.contAddFrom.setBoundLabelLength(20);		
        this.contAddFrom.setBoundLabelUnderline(true);
        // contAddTo		
        this.contAddTo.setBoundLabelText(resHelper.getString("contAddTo.boundLabelText"));		
        this.contAddTo.setBoundLabelLength(20);		
        this.contAddTo.setBoundLabelUnderline(true);
        // contIncFrom		
        this.contIncFrom.setBoundLabelText(resHelper.getString("contIncFrom.boundLabelText"));		
        this.contIncFrom.setBoundLabelLength(20);		
        this.contIncFrom.setBoundLabelUnderline(true);
        // contIncTo		
        this.contIncTo.setBoundLabelText(resHelper.getString("contIncTo.boundLabelText"));		
        this.contIncTo.setBoundLabelLength(20);		
        this.contIncTo.setBoundLabelUnderline(true);
        // contIncFloor		
        this.contIncFloor.setBoundLabelText(resHelper.getString("contIncFloor.boundLabelText"));		
        this.contIncFloor.setBoundLabelLength(20);		
        this.contIncFloor.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // comboIncSymbol		
        this.comboIncSymbol.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SymbolEnum").toArray());
        this.comboIncSymbol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboIncSymbol_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboAddSymbol		
        this.comboAddSymbol.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SymbolEnum").toArray());
        this.comboAddSymbol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboAddSymbol_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtIncYuan		
        this.txtIncYuan.setDataType(1);
        // contIncYuan		
        this.contIncYuan.setBoundLabelText(resHelper.getString("contIncYuan.boundLabelText"));		
        this.contIncYuan.setBoundLabelLength(20);		
        this.contIncYuan.setBoundLabelUnderline(true);
        // contAddYuan		
        this.contAddYuan.setBoundLabelText(resHelper.getString("contAddYuan.boundLabelText"));		
        this.contAddYuan.setBoundLabelLength(20);		
        this.contAddYuan.setBoundLabelUnderline(true);
        // txtAddYuan		
        this.txtAddYuan.setDataType(1);
        // labTip		
        this.labTip.setText(resHelper.getString("labTip.text"));		
        this.labTip.setForeground(new java.awt.Color(255,0,0));
        // txtAddFrom
        // txtAddTo
        // txtIncFrom
        // txtIncTo
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
        this.setBounds(new Rectangle(10, 10, 650, 220));
        this.setLayout(null);
        btnYes.setBounds(new Rectangle(485, 184, 73, 21));
        this.add(btnYes, null);
        btnNo.setBounds(new Rectangle(567, 184, 73, 21));
        this.add(btnNo, null);
        kDPanel1.setBounds(new Rectangle(10, 10, 630, 163));
        this.add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(10, 10, 630, 163));        kdContainer.setBounds(new Rectangle(0, -1, 629, 163));
        kDPanel1.add(kdContainer, new KDLayout.Constraints(0, -1, 629, 163, 0));
        //kdContainer
        kdContainer.getContentPane().setLayout(new KDLayout());
        kdContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, -1, 629, 163));        radioColInc.setBounds(new Rectangle(20, 36, 71, 19));
        kdContainer.getContentPane().add(radioColInc, new KDLayout.Constraints(20, 36, 71, 19, 0));
        radioColAdd.setBounds(new Rectangle(20, 68, 73, 19));
        kdContainer.getContentPane().add(radioColAdd, new KDLayout.Constraints(20, 68, 73, 19, 0));
        contAddFrom.setBounds(new Rectangle(119, 68, 60, 19));
        kdContainer.getContentPane().add(contAddFrom, new KDLayout.Constraints(119, 68, 60, 19, 0));
        contAddTo.setBounds(new Rectangle(193, 68, 60, 19));
        kdContainer.getContentPane().add(contAddTo, new KDLayout.Constraints(193, 68, 60, 19, 0));
        contIncFrom.setBounds(new Rectangle(119, 36, 60, 19));
        kdContainer.getContentPane().add(contIncFrom, new KDLayout.Constraints(119, 36, 60, 19, 0));
        contIncTo.setBounds(new Rectangle(193, 36, 60, 19));
        kdContainer.getContentPane().add(contIncTo, new KDLayout.Constraints(193, 36, 60, 19, 0));
        contIncFloor.setBounds(new Rectangle(269, 36, 20, 19));
        kdContainer.getContentPane().add(contIncFloor, new KDLayout.Constraints(269, 36, 20, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(269, 68, 20, 19));
        kdContainer.getContentPane().add(kDLabelContainer2, new KDLayout.Constraints(269, 68, 20, 19, 0));
        comboIncSymbol.setBounds(new Rectangle(298, 36, 170, 19));
        kdContainer.getContentPane().add(comboIncSymbol, new KDLayout.Constraints(298, 36, 170, 19, 0));
        comboAddSymbol.setBounds(new Rectangle(298, 68, 170, 19));
        kdContainer.getContentPane().add(comboAddSymbol, new KDLayout.Constraints(298, 68, 170, 19, 0));
        txtIncYuan.setBounds(new Rectangle(480, 36, 60, 19));
        kdContainer.getContentPane().add(txtIncYuan, new KDLayout.Constraints(480, 36, 60, 19, 0));
        contIncYuan.setBounds(new Rectangle(549, 36, 20, 19));
        kdContainer.getContentPane().add(contIncYuan, new KDLayout.Constraints(549, 36, 20, 19, 0));
        contAddYuan.setBounds(new Rectangle(549, 68, 20, 19));
        kdContainer.getContentPane().add(contAddYuan, new KDLayout.Constraints(549, 68, 20, 19, 0));
        txtAddYuan.setBounds(new Rectangle(480, 68, 60, 19));
        kdContainer.getContentPane().add(txtAddYuan, new KDLayout.Constraints(480, 68, 60, 19, 0));
        labTip.setBounds(new Rectangle(24, 101, 194, 19));
        kdContainer.getContentPane().add(labTip, new KDLayout.Constraints(24, 101, 194, 19, 0));
        //contAddFrom
        contAddFrom.setBoundEditor(txtAddFrom);
        //contAddTo
        contAddTo.setBoundEditor(txtAddTo);
        //contIncFrom
        contIncFrom.setBoundEditor(txtIncFrom);
        //contIncTo
        contIncTo.setBoundEditor(txtIncTo);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.BatchAdjustColUIHandler";
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
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output radioColInc_actionPerformed method
     */
    protected void radioColInc_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output radioColAdd_actionPerformed method
     */
    protected void radioColAdd_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboIncSymbol_itemStateChanged method
     */
    protected void comboIncSymbol_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboAddSymbol_itemStateChanged method
     */
    protected void comboAddSymbol_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BatchAdjustColUI");
    }




}