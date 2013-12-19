package net.anthavio.disquo.api.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusImportDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String forum;

	private String statusName;

	private String platform;

	private Date finishedAt;

	private Date startedAt;

	private Integer chunksDone;

	private Integer statusCode;

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Integer getChunksDone() {
		return chunksDone;
	}

	public void setChunksDone(Integer chunksDone) {
		this.chunksDone = chunksDone;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
