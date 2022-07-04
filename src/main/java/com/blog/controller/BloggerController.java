package com.blog.controller;

import com.blog.entity.Blogger;
/*  4:   */ import com.blog.service.BloggerService;
/*  5:   */ import com.blog.util.CryptographyUtil;
/*  6:   */ import javax.annotation.Resource;
/*  7:   */ import javax.servlet.http.HttpServletRequest;
/*  8:   */ import org.apache.shiro.SecurityUtils;
/*  9:   */ import org.apache.shiro.authc.UsernamePasswordToken;
/* 10:   */ import org.apache.shiro.subject.Subject;
/* 11:   */ import org.springframework.stereotype.Controller;
/* 12:   */ import org.springframework.web.bind.annotation.RequestMapping;
/* 13:   */ import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/blogger"})
public class BloggerController
{
@Resource
private BloggerService bloggerService;
	@RequestMapping({"/login"})
        public String login(Blogger blogger, HttpServletRequest request)
     {
         Subject subject = SecurityUtils.getSubject();
         UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(),
		         CryptographyUtil.md5(blogger.getPassword(), "darryl"));
        try
  {
	        subject.login(token);
         return "redirect:/admin/main.jsp";
  } catch (Exception e)
  {
     e.printStackTrace();
     request.setAttribute("blogger", blogger);
     request.setAttribute("errorInfo", "用户名或密码错误！");
  }
   return "login";
  }
@RequestMapping({"/aboutMe"})
    public ModelAndView aboutMe()
         throws Exception
    {
         ModelAndView mav = new ModelAndView();
             mav.addObject("blogger", this.bloggerService.find());
             mav.addObject("mainPage", "foreground/blogger/info.jsp");
             mav.addObject("pageTitle", "关于苏先生的Java开源博客系统");
             mav.setViewName("mainTemp");
         return mav;
	 }
}
