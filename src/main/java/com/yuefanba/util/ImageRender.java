package com.yuefanba.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jfinal.render.Render;

/**
 * 生成图片验证码
 * @author brotherbin
 *
 */
public class ImageRender extends Render {

	private int width = 70;
	
	private int height = 30;
	
	private int codeCount = 4;
	
	private Font font = new Font("Times New Roman", Font.PLAIN, 14);
	
	private Random rand = new Random();
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 创建图片对象
		BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图片画笔
		Graphics g = bufImg.getGraphics();
		// 填充矩形
		g.setColor(getRandomColor(200, 255));
		g.fillRect(1, 1, width-1, height-1);
		// 画矩形的边框
		g.setColor(getRandomColor(0, 125));
		g.drawRect(0, 0, width-1, height-1);
		
		g.setFont(font);
		g.setColor(getRandomColor(100, 200));
		
		// 画随机线
		for (int i = 0; i < 50; i++) {
			int x1 = rand.nextInt(width-10);
			int y1 = rand.nextInt(height-10);
			int x2 = x1 + rand.nextInt(2)+2;
			int y2 = y1 + rand.nextInt(2)+2;
			g.drawLine(x1, y1, x2, y2);
		}
		
		// 生成随机字符串
		String code = "";
		for (int i = 0; i < codeCount; i ++) {
			int r = rand.nextInt(10);
			code += r;
			g.setColor(getRandomColor(80, 150));
			g.drawString(String.valueOf(r), 15*i+10, 20);
		}
		g.dispose();
		request.getSession().setAttribute("validateCode", code);
		try {
			ImageIO.write(bufImg, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Color getRandomColor(int bound1, int bound2) {
		int r = rand.nextInt(bound2)%(bound2-bound1+1) + bound1;
		int g = rand.nextInt(bound2)%(bound2-bound1+1) + bound1;
		int b = rand.nextInt(bound2)%(bound2-bound1+1) + bound1;
		return new Color(r, g, b);
	}
}
