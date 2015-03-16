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
public abstract class AbstractBatchAdjustRowUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchAdjustRowUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdContainer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRowAdjust;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSymbol;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtYuan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYuan;
    protected com.kingdee.bos.ctrl.swing.KDLabel labTip;
    /**
     * output class constructor
     */
    public AbstractBatchAdjustRowUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchAdjustRowUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kdContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contRowAdjust = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSymbol = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtYuan = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contYuan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labTip = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDPanel1.setName("kDPanel1");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.kdContainer.setName("kdContainer");
        this.contRowAdjust.setName("contRowAdjust");
        this.comboSymbol.setName("comboSymbol");
        this.txtYuan.setName("txtYuan");
        this.contYuan.setName("contYuan");
        this.labTip.setName("labTip");
        // CoreUI
        // kDPanel1
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
        // kdContainer		
        this.kdContainer.setTitle(resHelper.getString("kdContainer.title"));
        // contRowAdjust		
        this.contRowAdjust.setBoundLabelText(resHelper.getString("contRowAdjust.boundLabelText"));		
        this.contRowAdjust.setBoundLabelLength(53);		
        this.contRowAdjust.setBoundLabelUnderline(true);
        // comboSymbol		
        this.comboSymbol.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SymbolEnum").toArray());		
        this.comboSymbol.setRequired(true);
        this.comboSymbol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSymbol_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtYuan		
        this.txtYuan.setDataType(1);
        // contYuan		
        this.contYuan.setBoundLabelText(resHelper.getString("contYuan.boundLabelText"));		
        this.contYuan.setBoundLabelLength(20);		
        this.contYuan.setBoundLabelUnderline(true);
        // labTip		
        this.labTip.setText(resHelper.getString("labTip.text"));		
        this.labTip.setBackground(new java.awt.Color(255,255,255));		
        this.labTip.setForeground(new java.awt.Color(255,0,0));
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
        kDPanel1.setBounds(new Rectangle(10, 10, 630, 160));
        this.add(kDPanel1, null);
        btnYes.setBounds(new Rectangle(486, 183, 73, 21));
        this.add(btnYes, null);
        btnNo.setBounds(new Rectangle(567, 183, 73, 21));
        this.add(btnNo, null);
        //kDPanel1
        kDPanel1.setLayout(null);        kdContainer.setBounds(new Rectangle(-1, 0, 630, 158));
        kDPanel1.add(kdContainer, null);
        //kdContainer
        kdContainer.getContentPane().setLayout(new KDLayout());
        kdContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(-1, 0, 630, 158));        contRowAdjust.setBounds(new Rectangle(35, 43, 62, 19));
        kdContainer.getContentPane().add(contRowAdjust, new KDLayout.Constraints(35, 43, 62, 19, 0));
        comboSymbol.setBounds(new Rectangle(98, 43, 170, 19));
        kdContainer.getContentPane().add(comboSymbol, new KDLayout.Constraints(98, 43, 170, 19, 0));
        txtYuan.setBounds(new Rectangle(279, 43, 170, 19));
        kdContainer.getContentPane().add(txtYuan, new KDLayout.Constraints(279, 43, 170, 19, 0));
        contYuan.setBounds(new Rectangle(459, 43, 20, 19));
        kdContainer.getContentPane().add(contYuan, new KDLayout.Constraints(459, 43, 20, 19, 0));
        labTip.setBounds(new Rectangle(35, 80, 126, 19));
        kdContainer.getContentPane().add(labTip, new KDLayout.Constraints(35, 80, 126, 19, 0));

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
	    return "com.kingdee.eas.fdc.sellhouse.app.BatchAdjustRowUIHandler";
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
     * output comboSymbol_itemStateChanged method
     */
    protected void comboSymbol_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BatchAdjustRowUI");
    }




}