package com.eshore.upsweb.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerifyCodeUtil {
	private static String[] num;
	private static String[] numCN;
	private static String[] method;
	private static String[] methodCN;
	private static Map<String, Integer> numMap = new HashMap<String, Integer>();
	private static Map<String, Integer> methodMap = new HashMap<String, Integer>();
	private static final long serialVersionUID = -1805762803411510509L;
	private static Font font = new Font("Arial", Font.PLAIN, 12);
	private static int width = 110;
	private static int height = 28;

	static {
		num = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		numCN = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		method = new String[] { "+", "-", "*" };
		methodCN = new String[] { "加", "减", "乘" };
	}

	static {
		numMap.put("1", 1);
		numMap.put("2", 2);
		numMap.put("3", 3);
		numMap.put("4", 4);
		numMap.put("5", 5);
		numMap.put("6", 6);
		numMap.put("7", 7);
		numMap.put("8", 8);
		numMap.put("9", 9);
		numMap.put("10", 10);
		numMap.put("一", 1);
		numMap.put("二", 2);
		numMap.put("三", 3);
		numMap.put("四", 4);
		numMap.put("五", 5);
		numMap.put("六", 6);
		numMap.put("七", 7);
		numMap.put("八", 8);
		numMap.put("九", 9);
		numMap.put("十", 10);
		methodMap.put("+", 0);
		methodMap.put("-", 1);
		methodMap.put("*", 2);
		methodMap.put("加", 0);
		methodMap.put("减", 1);
		methodMap.put("乘", 2);
	}
	
	public static BufferedImage  getVerifyCode(String code){
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(font);
		g.setColor(getRandColor(160, 230));

		// 生成随机干扰线
		for (int i = 0; i < 255; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		//备选字体  
	    String[] fontTypes =  
	    { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53",  
	            "\u6977\u4f53", "\u96b6\u4e66" }; 
		// 生成随机字符
	  //  String code = getCheckCodeStr();
		String sRand = code + " = ?";
		String[] codes = sRand.split(" ");
		for (int i = 0; i < 5; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.setFont(new Font(fontTypes[random.nextInt(5)],  
	                Font.BOLD, 18 + random.nextInt(6)));
			g.drawString(codes[i], 22 * i + 6, 20);
		}
		// 关闭画笔
		g.dispose();
		
/*		//图象生效   
		ByteArrayInputStream inputStream=null;   
		ByteArrayOutputStream output = new ByteArrayOutputStream();   
		ImageOutputStream imageOut;
		try {
			imageOut = ImageIO.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);   
			imageOut.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}   
		inputStream = new ByteArrayInputStream(output.toByteArray()); 
		return inputStream;  */
		return image;
	}
	
	
	
	
	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	public static String getCheckCodeStr() {		
		Random random = new Random();
		int numType1 = random.nextInt(2);		//阿拉伯数字/中文数字
		int numType2 = random.nextInt(2);		//阿拉伯数字/中文数字
		int methodType = random.nextInt(2);	//运算符号/中文表达
		
		String num1 = (numType1 == 0 ? num[random.nextInt(10)] : numCN[random.nextInt(10)]);
		String num2 = (numType2 == 0 ? num[random.nextInt(10)] : numCN[random.nextInt(10)]);
		String methods = (methodType == 0 ? method[random.nextInt(3)] : methodCN[random.nextInt(3)]);
		
		return num1 + " " + methods + " " +  num2;
	}
	
	public static int getCheckCodeResult(String code) {		
		String[] strs = code.split(" ");
		int num1 		= numMap.get(strs[0]);
		int method 		= methodMap.get(strs[1]);
		int num2 		= numMap.get(strs[2]);
		
		switch(method) {
			case 0:	//加
				return num1 + num2;
			case 1:	//减
				return num1 - num2;
			case 2:	//乘
				return num1 * num2;
		}
		return 0;
	}
	
	public static void main(String[] args){
	}
}
	

