package net.anthavio.disquo.browser;

import java.io.Serializable;

/**
 * 
 * @author martin.vanek
 *
 */
public class IndexSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;

	private String forumShort;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getForumShort() {
		return forumShort;
	}

	public void setForumShort(String forumShort) {
		this.forumShort = forumShort;
	}

}
