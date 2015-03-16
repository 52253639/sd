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
public abstract class AbstractChequeVCEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChequeVCEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPicker;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPickeDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStart;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radPart;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radAll;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBatchNumber;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup selectGroup;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7VCer;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker VCDate;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinnerEnd;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinnerStart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox F7Cheque;
    /**
     * output class constructor
     */
    public AbstractChequeVCEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChequeVCEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contPicker = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPickeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.radPart = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contBatchNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.selectGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.F7VCer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.VCDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spinnerEnd = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinnerStart = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.F7Cheque = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contPicker.setName("contPicker");
        this.contPickeDate.setName("contPickeDate");
        this.contEnd.setName("contEnd");
        this.contStart.setName("contStart");
        this.radPart.setName("radPart");
        this.radAll.setName("radAll");
        this.contBatchNumber.setName("contBatchNumber");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.F7VCer.setName("F7VCer");
        this.VCDate.setName("VCDate");
        this.spinnerEnd.setName("spinnerEnd");
        this.spinnerStart.setName("spinnerStart");
        this.F7Cheque.setName("F7Cheque");
        // CoreUI		
        this.setPreferredSize(new Dimension(650,200));
        // contPicker		
        this.contPicker.setBoundLabelText(resHelper.getString("contPicker.boundLabelText"));		
        this.contPicker.setBoundLabelLength(100);		
        this.contPicker.setBoundLabelUnderline(true);
        // contPickeDate		
        this.contPickeDate.setBoundLabelText(resHelper.getString("contPickeDate.boundLabelText"));		
        this.contPickeDate.setBoundLabelLength(100);		
        this.contPickeDate.setBoundLabelUnderline(true);
        // contEnd		
        this.contEnd.setBoundLabelText(resHelper.getString("contEnd.boundLabelText"));		
        this.contEnd.setBoundLabelLength(100);		
        this.contEnd.setBoundLabelUnderline(true);
        // contStart		
        this.contStart.setBoundLabelText(resHelper.getString("contStart.boundLabelText"));		
        this.contStart.setBoundLabelLength(100);		
        this.contStart.setBoundLabelUnderline(true);
        // radPart		
        this.radPart.setText(resHelper.getString("radPart.text"));
        this.radPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radPart_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radAll		
        this.radAll.setText(resHelper.getString("radAll.text"));		
        this.radAll.setSelected(true);
        this.radAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contBatchNumber		
        this.contBatchNumber.setBoundLabelText(resHelper.getString("contBatchNumber.boundLabelText"));		
        this.contBatchNumber.setBoundLabelLength(100);		
        this.contBatchNumber.setBoundLabelUnderline(true);
        // btnConfirm		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));		
        this.btnConfirm.setToolTipText(resHelper.getString("btnConfirm.toolTipText"));
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
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));
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
        // selectGroup
        this.selectGroup.add(this.radPart);
        this.selectGroup.add(this.radAll);
        // F7VCer		
        this.F7VCer.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.F7VCer.setRequired(true);		
        this.F7VCer.setDisplayFormat("$name$");		
        this.F7VCer.setEditFormat("$number$");
        // VCDate		
        this.VCDate.setRequired(true);
        // spinnerEnd
        // spinnerStart
        // F7Cheque		
        this.F7Cheque.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CRMChequeQuery");		
        this.F7Cheque.setRequired(true);		
        this.F7Cheque.setDisplayFormat("$chequeBathNumber$");		
        this.F7Cheque.setEditFormat("$chequeBathNumber$");
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
        this.setBounds(new Rectangle(10, 10, 650, 200));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 200));
        contPicker.setBounds(new Rectangle(28, 121, 270, 19));
        this.add(contPicker, new KDLayout.Constraints(28, 121, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPickeDate.setBounds(new Rectangle(358, 121, 270, 19));
        this.add(contPickeDate, new KDLayout.Constraints(358, 121, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEnd.setBounds(new Rectangle(358, 80, 270, 19));
        this.add(contEnd, new KDLayout.Constraints(358, 80, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStart.setBounds(new Rectangle(28, 80, 270, 19));
        this.add(contStart, new KDLayout.Constraints(28, 80, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        radPart.setBounds(new Rectangle(156, 53, 140, 19));
        this.add(radPart, new KDLayout.Constraints(156, 53, 140, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        radAll.setBounds(new Rectangle(29, 53, 110, 19));
        this.add(radAll, new KDLayout.Constraints(29, 53, 110, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBatchNumber.setBounds(new Rectangle(28, 23, 270, 19));
        this.add(contBatchNumber, new KDLayout.Constraints(28, 23, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnConfirm.setBounds(new Rectangle(211, 153, 73, 21));
        this.add(btnConfirm, new KDLayout.Constraints(211, 153, 73, 21, 0));
        btnCancel.setBounds(new Rectangle(308, 153, 73, 21));
        this.add(btnCancel, new KDLayout.Constraints(308, 153, 73, 21, 0));
        //contPicker
        contPicker.setBoundEditor(F7VCer);
        //contPickeDate
        contPickeDate.setBoundEditor(VCDate);
        //contEnd
        contEnd.setBoundEditor(spinnerEnd);
        //contStart
        contStart.setBoundEditor(spinnerStart);
        //contBatchNumber
        contBatchNumber.setBoundEditor(F7Cheque);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.ChequeVCEditUIHandler";
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
     * output radPart_actionPerformed method
     */
    protected void radPart_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output radAll_actionPerformed method
     */
    protected void radAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChequeVCEditUI");
    }




}