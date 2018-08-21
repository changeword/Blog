package ssm.blog.service;

import ssm.blog.entity.Blogger;

/**
 * @Description ����Service�ӿ�
 * @author Ni Shengwu
 *
 */
public interface BloggerService {

	public Blogger getByUsername(String username);

	public Blogger getBloggerData();

	// ���²���������Ϣ
	public Integer updateBlogger(Blogger blogger);
}
