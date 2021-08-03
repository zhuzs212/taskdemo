package com.zhuzs.taskdemo.controller.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 *
 * @author zhu_zishuang
 * @date 7/20/21
 */
@Controller
@RequestMapping("/sendMessage")
public class SendMessageController {
	
	@Autowired
	private IMessageService messageServiceImpl;
	
	@RequestMapping("/sendMsg")
	@ResponseBody
	public String sendMsg(){
		//发送10条消息
		for (int i = 0; i < 10; i++) {
			String msg = "msg"+i;
			System.out.println("发送消息  msg："+msg);
			messageServiceImpl.sendMessage("", "shuiwuDirectQueue", msg);
			//每两秒发送一次
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		return "send ok";
	}
}