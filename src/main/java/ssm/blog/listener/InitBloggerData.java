package ssm.blog.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import ssm.blog.entity.Blog;
import ssm.blog.entity.BlogType;
import ssm.blog.entity.Blogger;
import ssm.blog.entity.Link;
import ssm.blog.service.BlogService;
import ssm.blog.service.BlogTypeService;
import ssm.blog.service.BloggerService;
import ssm.blog.service.LinkService;

@Component
public class InitBloggerData implements ServletContextListener, ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println(applicationContext);
		//�Ȼ�ȡservlet������
		ServletContext application = sce.getServletContext();
		
		//����spring�������Ļ�ȡbloggerService���bean
		BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
		//��ȡ������Ϣ
		Blogger blogger = bloggerService.getBloggerData();
		//��������Ҳ��ȡ���ˣ��Ƚ����У�����Ҳ����Ҫ��������԰�������յ�
		//blogger.setPassword("lp12580");
		//��������Ϣ����application����
		application.setAttribute("blogger", blogger);
		
		//ͬ�ϣ���ȡ����������Ϣ
		LinkService linkService = (LinkService) applicationContext.getBean("linkService");
		List<Link> linkList = linkService.getLinkData(); 
		application.setAttribute("linkList", linkList);
		
		//ͬ�ϣ���ȡ���������Ϣ
		BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
		application.setAttribute("blogTypeList", blogTypeList);
		
		//ͬ�ϣ���ȡ������Ϣ������ʱ������
		BlogService blogService = (BlogService) applicationContext.getBean("blogService");
		List<Blog> blogTimeList = blogService.getBlogData();
		application.setAttribute("blogTimeList", blogTimeList);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) 
			throws BeansException {
		InitBloggerData.applicationContext = applicationContext;
	}

}
