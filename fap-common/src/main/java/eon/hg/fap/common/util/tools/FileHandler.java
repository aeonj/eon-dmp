package eon.hg.fap.common.util.tools;

import eon.hg.fap.common.properties.PropertiesBean;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 系统文件处理类
 * @author AEON
 *
 */
public class FileHandler {

	public static String getAbsoluteFileName(String path, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesBean.UPLOAD_FOLDER).append(path).append(File.separator).append(name);
		return sb.toString();
	}

	public static String getAbsoluteFilePath(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesBean.UPLOAD_FOLDER).append(path);
		return sb.toString();
	}

	/**
	 * saveFileToServer 上传文件保存到服务器
	 *
	 * @param file 为上传文件
	 * @param saveFilePathName 为文件保存的相对路径，基于UPLOAD_FOLDER
	 * @param saveFileName 为保存的文件
	 * @param extendes 为允许的文件扩展名
	 * @return 返回一个map，map中有4个值，第一个为保存的文件名fileName,第二个为保存的文件大小fileSize,,
	 *         第三个为保存文件时错误信息errors,如果生成缩略图则map中保存smallFileName，表示缩略图的全路径
	 */
	public static Dto saveFileToServer(MultipartFile file, String saveFilePathName, String saveFileName,
									   String... extendes) throws IOException {
		Dto map = new HashDto();
		if (file != null && !file.isEmpty()) {
			if (saveFilePathName==null) {
				saveFilePathName = "";
			}
			// System.out.println("文件名为：" + file.getOriginalFilename());
			String extend = file.getOriginalFilename()
					.substring(file.getOriginalFilename().lastIndexOf(".") + 1)
					.toLowerCase();
			if (saveFileName == null || saveFileName.trim().equals("")) {
				saveFileName = generateFileName() + "." + extend;
			}
			if (saveFileName.lastIndexOf(".") < 0) {
				saveFileName = saveFileName + "." + extend;
			}
			float fileSize = Float.valueOf(file.getSize());// 返回文件大小，单位为k
			boolean flag = false;
			if (extendes == null || extendes.length==0) {
				flag = true;
			} else {
				for (String s : extendes) {
					if (extend.toLowerCase().equals(s))
						flag = true;
				}
			}
			if (flag) {
				byte[] bytes = file.getBytes();
				Path paths = Paths.get(PropertiesBean.UPLOAD_FOLDER + saveFilePathName
						+ saveFileName);
				try {
					Files.createDirectories(paths.getParent());
				} catch (FileAlreadyExistsException e) {

				}
				Files.write(paths, bytes);
				String fileMd5 = getByteMD5(bytes);

				if (isImg(extend)) {
					File img = new File(PropertiesBean.UPLOAD_FOLDER + saveFilePathName
							+ saveFileName);
					try {
						BufferedImage bis = ImageIO.read(img);
						int w = bis.getWidth();
						int h = bis.getHeight();
						map.put("width", w);
						map.put("height", h);
					} catch (Exception e) {
						// map.put("width", 200);
						// map.put("heigh", 100);
					}
				}
				map.put("mime", extend);
				map.put("fileName", saveFileName);
				map.put("filePath", PropertiesBean.UPLOAD_FOLDER + saveFilePathName);
				map.put("fileSize", fileSize);
				map.put("fileMd5", fileMd5);
				map.put("oldName", file.getOriginalFilename());
				// System.out.println("上传结束，生成的文件名为:" + fileName);
				return map;
			} else {
				// System.out.println("不允许的扩展名");
				map.put("error","不允许的扩展名");
			}
		} else {
			map.put("error","文件为空");
		}
		map.put("width", 0);
		map.put("height", 0);
		map.put("mime", "");
		map.put("fileName", "");
		map.put("filePath", "");
		map.put("fileSize", 0.0f);
		map.put("fileMd5", "");
		map.put("oldName", "");
		return map;
	}

	public static boolean isImg(String extend) {
		boolean ret = false;
		List<String> list = new java.util.ArrayList<>();
		list.add("jpg");
		list.add("jpeg");
		list.add("bmp");
		list.add("gif");
		list.add("png");
		list.add("tif");
		list.add("tbi");
		for (String s : list) {
			if (s.equals(extend))
				ret = true;
		}
		return ret;
	}

	public static String getLicenseFilePath() {
		return PropertiesBean.UPLOAD_FOLDER;
	}

	/**
	 * 图片水印，一般使用gif png格式，其中png质量较好
	 *
	 * @param pressImg
	 *            水印文件
	 * @param targetImg
	 *            目标文件
	 * @param pos
	 *            水印位置，使用九宫格控制
	 * @param alpha
	 *            水印图片透明度
	 */
	public final static void waterMarkWithImage(String pressImg,
												String targetImg, int pos, float alpha) {
		try {
			// 目标文件
			Image theImg = Toolkit.getDefaultToolkit().getImage(targetImg);
			theImg.flush();
			BufferedImage bis = toBufferedImage(theImg);
			int width = theImg.getWidth(null);
			int height = theImg.getHeight(null);
			bis = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bis.createGraphics();
			g.drawImage(theImg, 0, 0, width, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha / 100));
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			int x = 0;
			int y = 0;
			if (pos == 1) {

			}
			if (pos == 2) {
				x = (width - width_biao) / 2;
				y = 0;
			}
			if (pos == 3) {
				x = width - width_biao;
				y = 0;
			}
			if (pos == 4) {
				x = width - width_biao;
				y = (height - height_biao) / 2;
			}
			if (pos == 5) {
				x = width - width_biao;
				y = height - height_biao;
			}
			if (pos == 6) {
				x = (width - width_biao) / 2;
				y = height - height_biao;
			}
			if (pos == 7) {
				x = 0;
				y = height - height_biao;
			}
			if (pos == 8) {
				x = 0;
				y = (height - height_biao) / 2;
			}
			if (pos == 9) {
				x = (width - width_biao) / 2;
				y = (height - height_biao) / 2;
			}
			g.drawImage(src_biao, x, y, width_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			ImageIO.write(bis, "JPEG", out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取图片为bufferedimage,修正图片读取ICC信息丢失而导致出现红色遮罩
	 *
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		// boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			/*
			 * if (hasAlpha) { transparency = Transparency.BITMASK; }
			 */

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	//获取文件的md5码
	public static String getFileMD5(String file) {
		return getFileMD5(new File(file));
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		String md5 = bigInt.toString(16);
		while (md5.length() < 32) md5 = "0" + md5;
		return md5;
	}
	//获取byte数组的md5码
	public static String getByteMD5(byte[] content) {
		MessageDigest digest=null;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(content);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		String md5 = bigInt.toString(16);
		while (md5.length() < 32) md5 = "0" + md5;
		return md5;

	}

	/**
	 * 生成随机文件名
	 * @return
	 */
	public static String generateFileName() {
		String sources = "012345ABCD"; // 生成伪随机数
		Random rand = new Random();
		StringBuffer name = new StringBuffer();
		for (int i = 0; i <= 6; i++) {

			for (int j = 0; j < 6; j++) {
				name.append(sources.charAt(rand.nextInt(9)) + "");
			}
		}
		return name.toString() + (new Date().getTime());
	}

	/**
	 * <p>
	 * 获取文件扩展名
	 * </p>
	 *
	 * @param filename
	 * @return
	 * @author FB
	 * @since 2018-08-20
	 */
	public static String getFileSuffix(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

}
