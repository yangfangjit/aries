/*
 * Copyright 2018-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yangfang.aries.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfang.aires.dubbo.provider.service.api.AnnotateService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO 类描述
 *
 * @author 幽明
 * @serial 2018/12/18
 */
@Service(interfaceClass = AnnotateService.class, timeout = 1000)
@Component
public class AnnotateServiceImpl implements AnnotateService {

    @Override
    public String helloDubbo() {
        return "Hello Dubbo";
    }


    public static void main(String[] args) {
        System.out.println(System.getProperties());
        ExecutorService executorService = new ThreadPoolExecutor(Integer.MAX_VALUE, Integer.MAX_VALUE, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10));

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int i = 0; i < 10000; i++) {
            System.out.println();
            Thread thread = new Thread(() -> {
                System.out.println(atomicInteger.incrementAndGet());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    // do nothing
                }});
            executorService.execute(thread);
        }




        /// mark("/Users/ym/Desktop/src.png", "/Users/ym/Desktop/src2.png", Color.BLUE, "来个水印");
    }

    /**
     * 图片添加水印
     *
     * @param srcImgPath       需要添加水印的图片的路径
     * @param outImgPath       添加水印后图片输出路径
     * @param markContentColor 水印文字的颜色
     * @param waterMarkContent 水印的文字
     */
    public static void mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {
        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            // Font font = new Font("Courier New", Font.PLAIN, 12);
            Font font = new Font("宋体", Font.PLAIN, 80);
            // 根据图片的背景设置水印颜色
            g.setColor(markContentColor);

            g.setFont(font);
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;
            int y = srcImgHeight - 3;
            // int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;
            // int y = srcImgHeight / 2;
            g.drawString(waterMarkContent, x, y);
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取水印文字总长度
     *
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

}
