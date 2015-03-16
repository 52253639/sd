/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractActivityValueHistoryListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractActivityValueHistoryListUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable versionTable;
    /**
     * output class constructor
     */
    public AbstractActivityValueHistoryListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractActivityValueHistoryListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "ActivityValueReportQuery");
        this.versionTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.versionTable.setName("versionTable");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol38\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol39\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol40\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"projectlongnumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"projectname\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"productConstitute\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"typename\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"areaRange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"tararea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"tarcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"tarunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"taramount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"yeararea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"yearcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"yearunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"yearamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"zjarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"zjcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"zjunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"zjamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"wqzarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"wqzcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"wqzunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"wqzamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"wqzydjarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"wqzydjcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"wqzydjunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"wqzydjamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"yqzwdjarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"yqzwdjcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"yqzwdjunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"yqzwdjamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"yqzydjarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"yqzydjcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"yqzydjunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"yqzydjamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"ysarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"yscount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"ysunitprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"ysamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" t:styleID=\"sCol38\" /><t:Column t:key=\"projectid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" t:styleID=\"sCol39\" /><t:Column t:key=\"simpleName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" t:styleID=\"sCol40\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{projectlongnumber}</t:Cell><t:Cell>$Resource{projectname}</t:Cell><t:Cell>$Resource{productConstitute}</t:Cell><t:Cell>$Resource{typename}</t:Cell><t:Cell>$Resource{areaRange}</t:Cell><t:Cell>$Resource{tararea}</t:Cell><t:Cell>$Resource{tarcount}</t:Cell><t:Cell>$Resource{tarunitprice}</t:Cell><t:Cell>$Resource{taramount}</t:Cell><t:Cell>$Resource{yeararea}</t:Cell><t:Cell>$Resource{yearcount}</t:Cell><t:Cell>$Resource{yearunitprice}</t:Cell><t:Cell>$Resource{yearamount}</t:Cell><t:Cell>$Resource{zjarea}</t:Cell><t:Cell>$Resource{zjcount}</t:Cell><t:Cell>$Resource{zjunitprice}</t:Cell><t:Cell>$Resource{zjamount}</t:Cell><t:Cell>$Resource{wqzarea}</t:Cell><t:Cell>$Resource{wqzcount}</t:Cell><t:Cell>$Resource{wqzunitprice}</t:Cell><t:Cell>$Resource{wqzamount}</t:Cell><t:Cell>$Resource{wqzydjarea}</t:Cell><t:Cell>$Resource{wqzydjcount}</t:Cell><t:Cell>$Resource{wqzydjunitprice}</t:Cell><t:Cell>$Resource{wqzydjamount}</t:Cell><t:Cell>$Resource{yqzwdjarea}</t:Cell><t:Cell>$Resource{yqzwdjcount}</t:Cell><t:Cell>$Resource{yqzwdjunitprice}</t:Cell><t:Cell>$Resource{yqzwdjamount}</t:Cell><t:Cell>$Resource{yqzydjarea}</t:Cell><t:Cell>$Resource{yqzydjcount}</t:Cell><t:Cell>$Resource{yqzydjunitprice}</t:Cell><t:Cell>$Resource{yqzydjamount}</t:Cell><t:Cell>$Resource{ysarea}</t:Cell><t:Cell>$Resource{yscount}</t:Cell><t:Cell>$Resource{ysunitprice}</t:Cell><t:Cell>$Resource{ysamount}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectid}</t:Cell><t:Cell>$Resource{simpleName}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{description_Row2}</t:Cell><t:Cell>$Resource{projectlongnumber_Row2}</t:Cell><t:Cell>$Resource{projectname_Row2}</t:Cell><t:Cell>$Resource{productConstitute_Row2}</t:Cell><t:Cell>$Resource{typename_Row2}</t:Cell><t:Cell>$Resource{areaRange_Row2}</t:Cell><t:Cell>$Resource{tararea_Row2}</t:Cell><t:Cell>$Resource{tarcount_Row2}</t:Cell><t:Cell>$Resource{tarunitprice_Row2}</t:Cell><t:Cell>$Resource{taramount_Row2}</t:Cell><t:Cell>$Resource{yeararea_Row2}</t:Cell><t:Cell>$Resource{yearcount_Row2}</t:Cell><t:Cell>$Resource{yearunitprice_Row2}</t:Cell><t:Cell>$Resource{yearamount_Row2}</t:Cell><t:Cell>$Resource{zjarea_Row2}</t:Cell><t:Cell>$Resource{zjcount_Row2}</t:Cell><t:Cell>$Resource{zjunitprice_Row2}</t:Cell><t:Cell>$Resource{zjamount_Row2}</t:Cell><t:Cell>$Resource{wqzarea_Row2}</t:Cell><t:Cell>$Resource{wqzcount_Row2}</t:Cell><t:Cell>$Resource{wqzunitprice_Row2}</t:Cell><t:Cell>$Resource{wqzamount_Row2}</t:Cell><t:Cell>$Resource{wqzydjarea_Row2}</t:Cell><t:Cell>$Resource{wqzydjcount_Row2}</t:Cell><t:Cell>$Resource{wqzydjunitprice_Row2}</t:Cell><t:Cell>$Resource{wqzydjamount_Row2}</t:Cell><t:Cell>$Resource{yqzwdjarea_Row2}</t:Cell><t:Cell>$Resource{yqzwdjcount_Row2}</t:Cell><t:Cell>$Resource{yqzwdjunitprice_Row2}</t:Cell><t:Cell>$Resource{yqzwdjamount_Row2}</t:Cell><t:Cell>$Resource{yqzydjarea_Row2}</t:Cell><t:Cell>$Resource{yqzydjcount_Row2}</t:Cell><t:Cell>$Resource{yqzydjunitprice_Row2}</t:Cell><t:Cell>$Resource{yqzydjamount_Row2}</t:Cell><t:Cell>$Resource{ysarea_Row2}</t:Cell><t:Cell>$Resource{yscount_Row2}</t:Cell><t:Cell>$Resource{ysunitprice_Row2}</t:Cell><t:Cell>$Resource{ysamount_Row2}</t:Cell><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{projectid_Row2}</t:Cell><t:Cell>$Resource{simpleName_Row2}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{description_Row3}</t:Cell><t:Cell>$Resource{projectlongnumber_Row3}</t:Cell><t:Cell>$Resource{projectname_Row3}</t:Cell><t:Cell>$Resource{productConstitute_Row3}</t:Cell><t:Cell>$Resource{typename_Row3}</t:Cell><t:Cell>$Resource{areaRange_Row3}</t:Cell><t:Cell>$Resource{tararea_Row3}</t:Cell><t:Cell>$Resource{tarcount_Row3}</t:Cell><t:Cell>$Resource{tarunitprice_Row3}</t:Cell><t:Cell>$Resource{taramount_Row3}</t:Cell><t:Cell>$Resource{yeararea_Row3}</t:Cell><t:Cell>$Resource{yearcount_Row3}</t:Cell><t:Cell>$Resource{yearunitprice_Row3}</t:Cell><t:Cell>$Resource{yearamount_Row3}</t:Cell><t:Cell>$Resource{zjarea_Row3}</t:Cell><t:Cell>$Resource{zjcount_Row3}</t:Cell><t:Cell>$Resource{zjunitprice_Row3}</t:Cell><t:Cell>$Resource{zjamount_Row3}</t:Cell><t:Cell>$Resource{wqzarea_Row3}</t:Cell><t:Cell>$Resource{wqzcount_Row3}</t:Cell><t:Cell>$Resource{wqzunitprice_Row3}</t:Cell><t:Cell>$Resource{wqzamount_Row3}</t:Cell><t:Cell>$Resource{wqzydjarea_Row3}</t:Cell><t:Cell>$Resource{wqzydjcount_Row3}</t:Cell><t:Cell>$Resource{wqzydjunitprice_Row3}</t:Cell><t:Cell>$Resource{wqzydjamount_Row3}</t:Cell><t:Cell>$Resource{yqzwdjarea_Row3}</t:Cell><t:Cell>$Resource{yqzwdjcount_Row3}</t:Cell><t:Cell>$Resource{yqzwdjunitprice_Row3}</t:Cell><t:Cell>$Resource{yqzwdjamount_Row3}</t:Cell><t:Cell>$Resource{yqzydjarea_Row3}</t:Cell><t:Cell>$Resource{yqzydjcount_Row3}</t:Cell><t:Cell>$Resource{yqzydjunitprice_Row3}</t:Cell><t:Cell>$Resource{yqzydjamount_Row3}</t:Cell><t:Cell>$Resource{ysarea_Row3}</t:Cell><t:Cell>$Resource{yscount_Row3}</t:Cell><t:Cell>$Resource{ysunitprice_Row3}</t:Cell><t:Cell>$Resource{ysamount_Row3}</t:Cell><t:Cell>$Resource{id_Row3}</t:Cell><t:Cell>$Resource{projectid_Row3}</t:Cell><t:Cell>$Resource{simpleName_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"2\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"2\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"1\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"0\" t:right=\"40\" /><t:Block t:top=\"1\" t:left=\"14\" t:bottom=\"1\" t:right=\"17\" /><t:Block t:top=\"1\" t:left=\"18\" t:bottom=\"1\" t:right=\"21\" /><t:Block t:top=\"1\" t:left=\"22\" t:bottom=\"1\" t:right=\"25\" /><t:Block t:top=\"1\" t:left=\"26\" t:bottom=\"1\" t:right=\"29\" /><t:Block t:top=\"1\" t:left=\"30\" t:bottom=\"1\" t:right=\"33\" /><t:Block t:top=\"1\" t:left=\"34\" t:bottom=\"1\" t:right=\"40\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"description","projectlongnumber","projectname","productConstitute","typename","areaRange","tararea","tarcount","tarunitprice","taramount","yeararea","yearcount","yearunitprice","yearamount","zjarea","zjcount","zjunitprice","zjamount","wqzarea","wqzcount","wqzunitprice","wqzamount","wqzydjarea","wqzydjcount","wqzydjunitprice","wqzydjamount","yqzwdjarea","yqzwdjcount","yqzwdjunitprice","yqzwdjamount","yqzydjarea","yqzydjcount","yqzydjunitprice","yqzydjamount","ysarea","yscount","ysunitprice","ysamount","id","projectid","simpleName"});


        // versionTable
		String versionTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"version\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quickName\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quickDes\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{version}</t:Cell><t:Cell>$Resource{quickName}</t:Cell><t:Cell>$Resource{quickDes}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.versionTable.setFormatXml(resHelper.translateString("versionTable",versionTableStrXML));
        this.versionTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    versionTable_tableClicked(e);
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        tblMain.setBounds(new Rectangle(12, 99, 998, 491));
        this.add(tblMain, null);
        versionTable.setBounds(new Rectangle(12, 10, 997, 82));
        this.add(versionTable, null);

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.ActivityValueHistoryListUIHandler";
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
     * output versionTable_tableClicked method
     */
    protected void versionTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here123
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("projectlongnumber"));
        sic.add(new SelectorItemInfo("projectname"));
        sic.add(new SelectorItemInfo("productConstitute"));
        sic.add(new SelectorItemInfo("typename"));
        sic.add(new SelectorItemInfo("tararea"));
        sic.add(new SelectorItemInfo("tarcount"));
        sic.add(new SelectorItemInfo("tarunitprice"));
        sic.add(new SelectorItemInfo("taramount"));
        sic.add(new SelectorItemInfo("zjarea"));
        sic.add(new SelectorItemInfo("zjcount"));
        sic.add(new SelectorItemInfo("zjunitprice"));
        sic.add(new SelectorItemInfo("zjamount"));
        sic.add(new SelectorItemInfo("wqzarea"));
        sic.add(new SelectorItemInfo("wqzcount"));
        sic.add(new SelectorItemInfo("wqzunitprice"));
        sic.add(new SelectorItemInfo("wqzamount"));
        sic.add(new SelectorItemInfo("wqzydjarea"));
        sic.add(new SelectorItemInfo("wqzydjcount"));
        sic.add(new SelectorItemInfo("wqzydjunitprice"));
        sic.add(new SelectorItemInfo("wqzydjamount"));
        sic.add(new SelectorItemInfo("yqzwdjarea"));
        sic.add(new SelectorItemInfo("yqzwdjcount"));
        sic.add(new SelectorItemInfo("yqzwdjunitprice"));
        sic.add(new SelectorItemInfo("yqzwdjamount"));
        sic.add(new SelectorItemInfo("yqzydjarea"));
        sic.add(new SelectorItemInfo("yqzydjcount"));
        sic.add(new SelectorItemInfo("yqzydjunitprice"));
        sic.add(new SelectorItemInfo("yqzydjamount"));
        sic.add(new SelectorItemInfo("ysarea"));
        sic.add(new SelectorItemInfo("yscount"));
        sic.add(new SelectorItemInfo("ysunitprice"));
        sic.add(new SelectorItemInfo("ysamount"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("projectid"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("areaRange"));
        sic.add(new SelectorItemInfo("yeararea"));
        sic.add(new SelectorItemInfo("yearcount"));
        sic.add(new SelectorItemInfo("yearunitprice"));
        sic.add(new SelectorItemInfo("yearamount"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "ActivityValueHistoryListUI");
    }




}