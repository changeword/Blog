	package ssm.blog.dao;

import ssm.blog.entity.Blogger;

/**
 * @Description ����dao�ӿ�
 * @author Ni Shengwu
 *
 */
public interface BloggerDao {
	
	//ͨ���û�����ѯ����
	public Blogger getByUsername(String username);
	
	//��ȡ������Ϣ
	public Blogger getBloggerData();
	
	// ���²���������Ϣ
	public Integer updateBlogger(Blogger blogger);
}
