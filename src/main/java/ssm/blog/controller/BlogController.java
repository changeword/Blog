package ssm.blog.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ssm.blog.entity.Blog;
import ssm.blog.entity.Comment;
import ssm.blog.lucene.BlogIndex;
import ssm.blog.service.BlogService;
import ssm.blog.service.CommentService;
import ssm.blog.util.PageUtil;
import ssm.blog.util.StringUtil;

/**
 * @Description ����Controller��
 * @author Ni Shengwu
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	@Resource
	private CommentService commentService;

	private BlogIndex blogIndex = new BlogIndex();

	// ���󲩿���ϸ��Ϣ
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,
			HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		Blog blog = blogService.findById(id); // ����id��ȡ����

		// ��ȡ�ؼ���
		String keyWords = blog.getKeyWord();
		if (StringUtil.isNotEmpty(keyWords)) {
			String[] strArray = keyWords.split(" ");
			List<String> keyWordsList = StringUtil.filterWhite(Arrays
					.asList(strArray));
			modelAndView.addObject("keyWords", keyWordsList);
		} else {
			modelAndView.addObject("keyWords", null);
		}

		modelAndView.addObject("blog", blog);
		blog.setClickHit(blog.getClickHit() + 1); // �����ͷ�������1
		blogService.update(blog); // ���²���

		// ��ѯ������Ϣ
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogId", blog.getId());
		map.put("state", 1);
		List<Comment> commentList = commentService.getCommentData(map);

		modelAndView.addObject("commentList", commentList);
		modelAndView.addObject("commonPage", "foreground/blog/blogDetail.jsp");
		modelAndView.addObject("title", blog.getTitle() + " - ������Ĳ���");

		// ������һƪ����һƪ����ʾ����
		modelAndView.addObject("pageCode", PageUtil.getPrevAndNextPageCode(
				blogService.getPrevBlog(id), blogService.getNextBlog(id),
				request.getServletContext().getContextPath()));

		modelAndView.setViewName("mainTemp");

		return modelAndView;
	}

	// ���ݹؼ��ֲ�ѯ������Ϣ
	@RequestMapping("/search")
	public ModelAndView search(
			@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "page", required = false) String page,
			HttpServletRequest request) throws Exception {

		int pageSize = 10;
		ModelAndView modelAndView = new ModelAndView();
		List<Blog> blogIndexList = blogIndex.searchBlog(q);
		if(page == null) { //pageΪ�ձ�ʾ��һ������
			page = "1";
		}
		int fromIndex = (Integer.parseInt(page) - 1) * pageSize; // ��ʼ����
		int toIndex = blogIndexList.size() >= Integer.parseInt(page) * pageSize ? Integer
				.parseInt(page) * pageSize
				: blogIndexList.size();
		modelAndView.addObject("blogIndexList", blogIndexList.subList(fromIndex, toIndex));
		modelAndView.addObject("pageCode", PageUtil.getUpAndDownPageCode(
				Integer.parseInt(page), blogIndexList.size(), q, pageSize,
				request.getServletContext().getContextPath()));
		modelAndView.addObject("q", q); // �������ݵĻ���
		modelAndView.addObject("resultTotal", blogIndexList.size()); // ��ѯ�����ܼ�¼��
		modelAndView.addObject("commonPage", "foreground/blog/searchResult.jsp");
		modelAndView.addObject("title", "����'" + q + "'�Ľ�� - ������Ĳ���");
		modelAndView.setViewName("mainTemp");
		return modelAndView;
	}

}
