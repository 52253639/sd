package com.kingdee.eas.fdc.market.client;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.hr.base.OutputExcelUtil;
import com.kingdee.eas.util.SysUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class roomPicPanel extends KDPanel{
	
	private static final long serialVersionUID = 4040175233420235445L;

	public BufferedImage selectImage = null;

	private File selectFile = null;

	private boolean isDelete = false;
	
	private String oprtStat = null;

	public roomPicPanel() {
		super();
		initial();
	}
	
	public void setOprtStat(String oprtStat)
	{
		this.oprtStat = oprtStat; 
		this.isDelete = false;
	}

	private void initial() 
	{
		this.setBorder(BorderFactory.createEtchedBorder());
		this.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseClicked(java.awt.event.MouseEvent e) 
			{
				try 
				{
					photePanel_mouseClicked(e);
				} 
				catch (Exception exc) 
				{
					exc.printStackTrace();
				}
			}
		});
	}

	
	
	public void grabFocus() {
		super.grabFocus();
	}

	protected void photePanel_mouseClicked(MouseEvent e) throws Exception 
	{
//		if (e.getClickCount() == 2) 
//		{
//			showZoomPhotoDialog();
//		} 
//		else 
//		{
			if (e.getButton() == MouseEvent.BUTTON3) 
			{
				KDPopupMenu popupMenu = CreatePopuMenu();
				this.add(popupMenu);
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
//		}
	}

	private KDPopupMenu CreatePopuMenu() 
	{		
		KDPopupMenu popuMenu = new KDPopupMenu();
		
		if(this.oprtStat != null && !this.oprtStat.equals(OprtState.VIEW))
		{
			KDMenuItem mItemAdd = new KDMenuItem("Ìí¼ÓÕÕÆ¬");
			ActionListener lstAdd = new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					try 
					{
						showAddImageDialog();
						isDelete = false;
					} 
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}
				}
			};
			mItemAdd.addActionListener(lstAdd);
			popuMenu.add(mItemAdd);
			
			KDMenuItem mItemDelete = new KDMenuItem("É¾³ýÕÕÆ¬");
			ActionListener lstDelete = new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					selectImage = null;
					isDelete = true;
					repaint();
				}
			};
			mItemDelete.addActionListener(lstDelete);
			popuMenu.add(mItemDelete);
		}
		
		KDMenuItem mItemSave = new KDMenuItem("±£´æÕÕÆ¬");
		ActionListener lstSave = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (selectImage != null)
					showSaveDialog();
			}
		};
		mItemSave.addActionListener(lstSave);
		popuMenu.add(mItemSave);
		
		return popuMenu;
	}

	private void showSaveDialog() 
	{
		KDFileChooser m_chooserSave = new KDFileChooser();
		m_chooserSave.setAcceptAllFileFilterUsed(false);
		m_chooserSave.setFileFilter(new photoFileFilter());
		
		int result = m_chooserSave.showSaveDialog(this);
		if (result != KDFileChooser.APPROVE_OPTION)
			return;
		File f = m_chooserSave.getSelectedFile();
		saveComponentToJPEG(f.getAbsolutePath()  + ".jpg");
	}

	private void saveComponentToJPEG(String fileName) 
	{
		try 
		{
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(fileName));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			JPEGEncodeParam jep = encoder
					.getDefaultJPEGEncodeParam(selectImage);
			jep.setQuality(1.0f, false);
			encoder.setJPEGEncodeParam(jep);
			encoder.encode(selectImage);
			bos.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void showAddImageDialog() throws Exception 
	{
		KDFileChooser chsFile = new KDFileChooser();
		String[] jpgs = new String[]{"jpg","tiff","gif","png"};
		SimpleFileFilter Filter_jpg = new SimpleFileFilter(jpgs, "");
		chsFile.addChoosableFileFilter(Filter_jpg);
		int ret = chsFile.showOpenDialog(this);
		if (ret != JFileChooser.APPROVE_OPTION)
			SysUtil.abort();
		
		
//		FileDialog fd=new FileDialog(new Frame(),"",FileDialog.LOAD); 

		
		
//		fd.setFilenameFilter(new photoFileNameFilter()); 

		
		
//		fd.setVisible(true);
		selectFile = chsFile.getSelectedFile();
		
		selectImage = ImageIO.read(selectFile);
		
		this.repaint();
	}

//	private void showZoomPhotoDialog() 
//	{
//		UIContext map = new UIContext(this);
//		if (selectImage == null)
//			return;
//		map.put("selectImage", selectImage);
//		IUIFactory uiFactory = null;
//		try 
//		{
//			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
//			IUIWindow curDialog = uiFactory.create(EmployeePhoto.class
//					.getName(), map);
//			curDialog.show();
//		} 
//		catch (UIException ex1) 
//		{
//			ex1.printStackTrace();
//		}
//	}

	public void paint(Graphics arg0) 
	{
		super.paint(arg0);
		if (!isDelete && selectImage != null) 
		{
			int imgWidth = selectImage.getWidth();
			int imgHeight = selectImage.getHeight();
			if (imgWidth > this.getWidth() && imgHeight < this.getHeight()) 
			{
				int pWidth = (this.getWidth() * imgHeight) / this.getHeight();
				arg0.drawImage(selectImage, (this.getWidth() - pWidth) / 2,
						(this.getHeight() - imgHeight) / 2
						,pWidth, imgHeight, null,
						null);

			} 
			else if (imgWidth < this.getWidth()&& imgHeight > this.getHeight()) 
			{
				int pHeight = (this.getHeight() * imgWidth) / this.getWidth();
				arg0.drawImage(selectImage,
						(this.getWidth() - imgWidth) / 2,
						(this.getHeight() - pHeight) / 2,imgWidth,pHeight, null,
						null);

			} 
			else if (imgWidth < this.getWidth()&& imgHeight < this.getHeight()) 
			{
				arg0.drawImage(selectImage, (this.getWidth() - imgWidth) / 2,
						(this.getHeight() - imgHeight) / 2, imgWidth,
						imgHeight, null, null);
			} 
			else 
			{
				arg0.drawImage(selectImage, 0, 0, this.getWidth(), this
						.getHeight(), null, null);
			}
		}
		else
		{
			if(this.oprtStat != null && !this.oprtStat.equals(OprtState.VIEW))
			{
				arg0.drawString("Êó±êÓÒ¼üÌí¼ÓÕÕÆ¬",6,75);
			}
		}
	}
	public BufferedImage getSelectImage() {
		return selectImage;
	}

	public void setSelectImage(BufferedImage image) {
		this.selectImage = image;
		repaint();
	}

	public File getSelectFile() 
	{
		return selectFile;
	}
	
	public byte[] getSelectImageBytes()
	{
		try 
		{
	        int w = this.getWidth();
	        int h = this.getHeight();
	        BufferedImage image = (BufferedImage) this.createImage(w, h);
	        Graphics2D g2 = image.createGraphics();
	        this.print(g2);
			
			ByteArrayOutputStream outs = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(outs);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(image);
			jep.setQuality(1.0f, false);
			encoder.setJPEGEncodeParam(jep);
			encoder.encode(image);
			bos.close();
			return outs.toByteArray();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}

class photoFileNameFilter implements FilenameFilter 
{
	public boolean isTiff(String file) 
	{    
	    if (file.toLowerCase().endsWith(".tiff"))
	    {    
	      return true;    
	    }
	    else
	    {    
	      return false;    
	    }    
	  } 
	public boolean isGif(String file) 
	{    
	    if (file.toLowerCase().endsWith(".gif"))
	    {    
	      return true;    
	    }
	    else
	    {    
	      return false;    
	    }    
	  } 
	
	  public boolean isJpg(String file)
	  {    
	    if (file.toLowerCase().endsWith(".jpg"))
	    {    
	      return true;    
	    }
	    else
	    {    
	      return false;    
	    }    
	  }    
	   
	  public boolean isPng(String file)
	  {    
	    if (file.toLowerCase().endsWith(".png"))
	    {    
	      return true;    
	    }
	    else
	    {    
	      return false;    
	    }    
	     
	  }    

	  public boolean accept(File dir,String fname)
	  {    
	    return (isTiff(fname) || isGif(fname) || isJpg(fname) || isPng(fname));    
	  } 
	}

class photoFileFilter extends FileFilter 
{
    File file=null;
	public boolean accept(File f) 
	{
		file = f;
		if (f.isDirectory()) 
		{
			return true;
		}
		String extension = OutputExcelUtil.getExtension(f);
		if (extension != null) 
		{
			if (extension.equals(OutputExcelUtil.tiff) || extension.equals(OutputExcelUtil.tif)
					|| extension.equals(OutputExcelUtil.gif)
					|| extension.equals(OutputExcelUtil.jpeg)
					|| extension.equals(OutputExcelUtil.jpg)
					|| extension.equals(OutputExcelUtil.png)

			) 
			{
				return true;
			} 
			else 
			{
				return false;
			}
		}
		return false;
	}

	public String getDescription() {
		return "JPEG ( *.jpg;*.jpeg ), PNG, Gif, Tiff";
	}

}
