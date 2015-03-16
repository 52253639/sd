/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractImportExcelPriceUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractImportExcelPriceUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHeadType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7HeadType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFileName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFileName;
    protected com.kingdee.bos.ctrl.swing.KDButton btnChoose;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPrice;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDButton kDButton;
    /**
     * output class constructor
     */
    public AbstractImportExcelPriceUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractImportExcelPriceUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contHeadType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7HeadType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contFileName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFileName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnChoose = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kdtPrice = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDButton = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contHeadType.setName("contHeadType");
        this.f7HeadType.setName("f7HeadType");
        this.contFileName.setName("contFileName");
        this.txtFileName.setName("txtFileName");
        this.btnChoose.setName("btnChoose");
        this.kdtPrice.setName("kdtPrice");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.kDButton.setName("kDButton");
        // CoreUI
        // contHeadType		
        this.contHeadType.setBoundLabelText(resHelper.getString("contHeadType.boundLabelText"));		
        this.contHeadType.setBoundLabelLength(100);		
        this.contHeadType.setBoundLabelUnderline(true);
        // f7HeadType		
        this.f7HeadType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7HeadTypeQuery");		
        this.f7HeadType.setRequired(true);
        this.f7HeadType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7HeadType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contFileName		
        this.contFileName.setBoundLabelText(resHelper.getString("contFileName.boundLabelText"));		
        this.contFileName.setBoundLabelLength(100);		
        this.contFileName.setBoundLabelUnderline(true);
        // txtFileName		
        this.txtFileName.setRequired(true);
        // btnChoose		
        this.btnChoose.setText(resHelper.getString("btnChoose.text"));
        this.btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChoose_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdtPrice

        

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
        // kDButton		
        this.kDButton.setText(resHelper.getString("kDButton.text"));
        this.kDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDButton_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        contHeadType.setBounds(new Rectangle(16, 12, 209, 19));
        this.add(contHeadType, new KDLayout.Constraints(16, 12, 209, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contFileName.setBounds(new Rectangle(432, 12, 232, 19));
        this.add(contFileName, new KDLayout.Constraints(432, 12, 232, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnChoose.setBounds(new Rectangle(685, 12, 98, 22));
        this.add(btnChoose, new KDLayout.Constraints(685, 12, 98, 22, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kdtPrice.setBounds(new Rectangle(12, 46, 778, 515));
        this.add(kdtPrice, new KDLayout.Constraints(12, 46, 778, 515, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnYes.setBounds(new Rectangle(554, 569, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(554, 569, 73, 21, 0));
        btnNo.setBounds(new Rectangle(673, 569, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(673, 569, 73, 21, 0));
        kDButton.setBounds(new Rectangle(262, 12, 130, 21));
        this.add(kDButton, new KDLayout.Constraints(262, 12, 130, 21, 0));
        //contHeadType
        contHeadType.setBoundEditor(f7HeadType);
        //contFileName
        contFileName.setBoundEditor(txtFileName);

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
	    return "com.kingdee.eas.fdc.invite.app.ImportExcelPriceUIHandler";
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
     * output f7HeadType_dataChanged method
     */
    protected void f7HeadType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnChoose_actionPerformed method
     */
    protected void btnChoose_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kDButton_actionPerformed method
     */
    protected void kDButton_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "ImportExcelPriceUI");
    }




}