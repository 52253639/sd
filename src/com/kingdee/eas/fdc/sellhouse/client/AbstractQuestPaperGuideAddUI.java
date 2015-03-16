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
public abstract class AbstractQuestPaperGuideAddUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuestPaperGuideAddUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizScene;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAddNew;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioCopyNew;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSelectAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNext;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSure;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combBizScene;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane treeScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree orgUnitTree;
    /**
     * output class constructor
     */
    public AbstractQuestPaperGuideAddUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuestPaperGuideAddUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contBizScene = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.radioAddNew = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioCopyNew = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSelectAdd = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnNext = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnSure = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.combBizScene = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.treeScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.orgUnitTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.contBizScene.setName("contBizScene");
        this.radioAddNew.setName("radioAddNew");
        this.radioCopyNew.setName("radioCopyNew");
        this.radioSelectAdd.setName("radioSelectAdd");
        this.btnNext.setName("btnNext");
        this.btnSure.setName("btnSure");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.combBizScene.setName("combBizScene");
        this.tblMain.setName("tblMain");
        this.treeScrollPane1.setName("treeScrollPane1");
        this.orgUnitTree.setName("orgUnitTree");
        // CoreUI
        // contBizScene		
        this.contBizScene.setBoundLabelText(resHelper.getString("contBizScene.boundLabelText"));		
        this.contBizScene.setBoundLabelLength(100);		
        this.contBizScene.setBoundLabelUnderline(true);
        // radioAddNew		
        this.radioAddNew.setText(resHelper.getString("radioAddNew.text"));		
        this.radioAddNew.setSelected(true);
        // radioCopyNew		
        this.radioCopyNew.setText(resHelper.getString("radioCopyNew.text"));
        // radioSelectAdd		
        this.radioSelectAdd.setText(resHelper.getString("radioSelectAdd.text"));
        // btnNext		
        this.btnNext.setText(resHelper.getString("btnNext.text"));
        this.btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNext_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.radioAddNew);
        this.kDButtonGroup1.add(this.radioCopyNew);
        this.kDButtonGroup1.add(this.radioSelectAdd);
        // btnSure		
        this.btnSure.setText(resHelper.getString("btnSure.text"));		
        this.btnSure.setVisible(false);
        this.btnSure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSure_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(240);
        // combBizScene		
        this.combBizScene.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum").toArray());
        // tblMain		
        this.tblMain.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"itemTopic\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" /><t:Column t:key=\"documentTopic\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"questionTopic\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"questionType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"questionNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"subjectClass\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">分组标题</t:Cell><t:Cell t:configured=\"false\">题目标题</t:Cell><t:Cell t:configured=\"false\">问卷主题</t:Cell><t:Cell t:configured=\"false\">问卷类别</t:Cell><t:Cell t:configured=\"false\">问卷编码</t:Cell><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">题目类别</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

        // treeScrollPane1		
        this.treeScrollPane1.setEnabled(false);		
        this.treeScrollPane1.setVisible(false);
        // orgUnitTree		
        this.orgUnitTree.setEnabled(false);		
        this.orgUnitTree.setVisible(false);
        this.orgUnitTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    orgUnitTree_valueChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 500, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 500, 300));
        contBizScene.setBounds(new Rectangle(46, 43, 270, 19));
        this.add(contBizScene, new KDLayout.Constraints(46, 43, 270, 19, 0));
        radioAddNew.setBounds(new Rectangle(46, 80, 140, 19));
        this.add(radioAddNew, new KDLayout.Constraints(46, 80, 140, 19, 0));
        radioCopyNew.setBounds(new Rectangle(46, 116, 140, 19));
        this.add(radioCopyNew, new KDLayout.Constraints(46, 116, 140, 19, 0));
        radioSelectAdd.setBounds(new Rectangle(46, 156, 140, 19));
        this.add(radioSelectAdd, new KDLayout.Constraints(46, 156, 140, 19, 0));
        btnNext.setBounds(new Rectangle(324, 275, 73, 19));
        this.add(btnNext, new KDLayout.Constraints(324, 275, 73, 19, 0));
        btnSure.setBounds(new Rectangle(408, 275, 73, 19));
        this.add(btnSure, new KDLayout.Constraints(408, 275, 73, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSplitPane1.setBounds(new Rectangle(11, 10, 471, 259));
        this.add(kDSplitPane1, new KDLayout.Constraints(11, 10, 471, 259, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBizScene
        contBizScene.setBoundEditor(combBizScene);
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(treeScrollPane1, "left");
        //treeScrollPane1
        treeScrollPane1.getViewport().add(orgUnitTree, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.QuestPaperGuideAddUIHandler";
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
	 * ????????У??
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
     * output btnNext_actionPerformed method
     */
    protected void btnNext_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnSure_actionPerformed method
     */
    protected void btnSure_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output orgUnitTree_valueChanged method
     */
    protected void orgUnitTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "QuestPaperGuideAddUI");
    }




}