package com.ant.util;

import javax.imageio.ImageIO;
import javax.swing.*;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageUtil {

	public static void resize(File originalFile, File resizedFile,
			int newWidth ,int newHeight) throws IOException {

		int tempWidth = newWidth;
		int tempHeight = newHeight;
		
		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();

		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);
		
		double wRate = (newWidth + 0.0)/(iWidth + 0.0);	//宽度缩放的比例
		double hRate = (newHeight + 0.0)/(iHeight + 0.0);	//高度缩放的比例

		if (wRate > hRate) {
			/**以宽度为准*/
			newHeight = (newWidth * iHeight) / iWidth;
		} else {
			/**以高度为准*/
			newWidth = (newHeight * iWidth) / iHeight;
//			Thumbnails.of(originalFile.getPath()).scale(wRate)
//				.toFile(resizedFile.getPath());
		}
		Thumbnails.of(originalFile.getPath()).size(newWidth, newHeight)
			.toFile(resizedFile.getPath());
		
		
		cutImage(resizedFile, resizedFile,  tempWidth, tempHeight);
	} // Example usage


	/**
	 * 裁剪成正方形
	 * 
	 * @param originalFile
	 * @param resizedFile
	 * @param newWidth
	 * @param quality
	 * @throws IOException
	 */
	public static void resizeSquare(File originalFile, File resizedFile,
			int newSize) throws IOException {

		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();

		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);

		int newWidth = 0;
		int newHeight = 0;
		if (iWidth < iHeight) {
			/**http://rensanning.iteye.com/blog/1545708*/
//			if(iWidth < newSize){
				newWidth = newSize;
//			}else{
//				newWidth = iWidth;
//			}
			newHeight = (newWidth * iHeight) / iWidth;
		} else {
//			if(iHeight < newSize){
				newHeight = newSize;
//			}else{
//				newHeight = iHeight;
//			}
			newWidth = (newHeight * iWidth) / iHeight;
		}
		Thumbnails.of(originalFile.getPath()).size(newWidth,newHeight).toFile(resizedFile.getPath()); 

		cutImage(resizedFile, resizedFile,  newSize, newSize);
	} // Example usage

	/*
	 * 图片裁剪通用接口
	 */

	public static void cutImage(File src, File dest,  int w, int h)
			throws IOException {


			BufferedImage source = ImageIO.read(src);
			int sWidth = source.getWidth(); // 图片宽度
			int sHeight = source.getHeight(); // 图片高度

			//指定坐标  
			Thumbnails.of(src.getPath())  
			        .sourceRegion((sWidth - w) / 2,(sHeight - h) / 2, w, h)  
			        .size(w, h)  
//			        .keepAspectRatio(false)   
			        .toFile(dest.getPath());  
			

	}

}
