package com.bloom.domain.wechat.common.service.impl;

import javax.annotation.PostConstruct;

import com.bloom.domain.wechat.common.consumer.bean.interesttip.InterestTipConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bloom.domain.wechat.common.config.WechatAccount;
import com.bloom.domain.wechat.common.consumer.bean.bind.BindGardenerReminder;
import com.bloom.domain.wechat.common.consumer.bean.petal.CreatePetalLinkReminder;
import com.bloom.domain.wechat.common.consumer.bean.petal.TodayFiredPetal;
import com.bloom.domain.wechat.common.consumer.bean.test.KeFuMsgNewsTest;
import com.bloom.domain.wechat.common.consumer.bean.test.TemplateMsgTest;
import com.bloom.domain.wechat.common.router.WxMpServiceGenerator;
import com.bloom.domain.wechat.common.service.WxMpMenuInitService;

import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

@Component
public class GrasswortMenuInitiateBean implements WxMpMenuInitService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxMpServiceGenerator wxMpServiceGenerator;
	
	@Override
	@PostConstruct
	public void init() {
		WxMpService grasswort = wxMpServiceGenerator.get(WechatAccount.GRASSWORT).get();
		
		WxMenu menu = new WxMenu();
		
		WxMenuButton button1 = new WxMenuButton();
		button1.setName("菜单");
		menu.getButtons().add(button1);
		
			WxMenuButton button11 = new WxMenuButton();
			button11.setType(MenuButtonType.CLICK);
			button11.setName("账号绑定");
			button11.setKey(BindGardenerReminder.KEY);
			
			WxMenuButton button12 = new WxMenuButton();
			button12.setType(MenuButtonType.CLICK);
			button12.setName("添加叶子");
			button12.setKey(CreatePetalLinkReminder.KEY);
			
			WxMenuButton button13 = new WxMenuButton();
			button13.setType(MenuButtonType.VIEW);
			button13.setName("百度一下");
			button13.setUrl("https://www.baidu.com");;

			WxMenuButton button14 = new WxMenuButton();
			button14.setType(MenuButtonType.CLICK);
			button14.setName("趣味提醒");
			button14.setKey(InterestTipConfig.KEY);
			
		button1.getSubButtons().add(button11);	
		button1.getSubButtons().add(button12);
		button1.getSubButtons().add(button13);
		button1.getSubButtons().add(button14);
		
		WxMenuButton button2 = new WxMenuButton();
		button2.setType(MenuButtonType.CLICK);
		button2.setName("飞叶");
		button2.setKey(TodayFiredPetal.KEY);
		menu.getButtons().add(button2);
		
		WxMenuButton button3 = new WxMenuButton();
		button3.setName("功能");
		menu.getButtons().add(button3);
		
			WxMenuButton button31 = new WxMenuButton();
			button31.setType(MenuButtonType.CLICK);
			button31.setName("图文消息");
			button31.setKey(KeFuMsgNewsTest.KEY);
			
			WxMenuButton button32 = new WxMenuButton();
			button32.setType(MenuButtonType.CLICK);
			button32.setName("模板消息");
			button32.setKey(TemplateMsgTest.KEY);
			
			WxMenuButton button33 = new WxMenuButton();
			button33.setType(MenuButtonType.VIEW);
			button33.setName("源码仓库");
			button33.setUrl("https://github.com/xuliangliang1995");
		
		button3.getSubButtons().add(button31);
		button3.getSubButtons().add(button32);	
		button3.getSubButtons().add(button33);
		
		try {
			grasswort.getMenuService().menuCreate(menu);
			this.logger.info("\n微信公众号【{}】菜单初始化成功！","grasswort");
		} catch (WxErrorException e) {
			this.logger.error("\n微信公众号【{}】菜单初始化失败！","grasswort");
			e.printStackTrace();
		}
	}

}
