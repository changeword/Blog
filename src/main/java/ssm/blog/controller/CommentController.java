package ssm.blog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.Blog;
import ssm.blog.entity.Comment;
import ssm.blog.service.BlogService;
import ssm.blog.service.CommentService;
import ssm.blog.util.ResponseUtil;

/**
 * @Description ���۵�controller
 * @author Ni Shengwu
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Resource
	private CommentService commentService;
	@Resource
	private BlogService blogService;
	
	//��ӻ����޸�����
	@RequestMapping("/save")
	public String save(
			Comment comment, 
			@RequestParam("imageCode")String imageCode, //ǰ̨��������֤��
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		String sRand = (String) session.getAttribute("sRand");//��ȡsession����ȷ����֤�룬��֤��������浽session�е�
		JSONObject result = new JSONObject();
		int resultTotal = 0; //ִ�м�¼��
		if(!imageCode.equals(sRand)) {
			result.put("success", false);
			result.put("errorInfo", "��֤������");
		} else {
			String userIp = request.getRemoteAddr(); //��ȡ�����û���ip
			comment.setUserIp(userIp);  //��userIp���ý�ȥ
			if(comment.getId() == null) { //û��id��ʾ���
				resultTotal = commentService.addComment(comment); //�������
				Blog blog = blogService.findById(comment.getBlog().getId()); //����һ�²��͵����۴���
				blog.setReplyHit(blog.getReplyHit() + 1);
				blogService.update(blog);
			} else { //��id��ʾ�޸�
				
			}
		}		
		//�ж��Ƿ���ӳɹ�
		if(resultTotal > 0) {
			result.put("success", true);
		}		
		ResponseUtil.write(response, result);		
		return null;
	}
}
