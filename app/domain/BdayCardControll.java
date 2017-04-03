package domain;

public class BdayCardControll {

	private String groupGuid;
	private String birthday;
	private String status;
	private String guid;
	private Long startTs;
	private Long endTs;
	
	public BdayCardControll(String groupGuid, String birthday, String status, String guid, Long startTs, Long endTs) {
		super();
		this.groupGuid = groupGuid;
		this.birthday = birthday;
		this.status = status;
		this.guid = guid;
		this.startTs = startTs;
		this.endTs = endTs;
	}
	public String getGroupGuid() {
		return groupGuid;
	}
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Long getStartTs() {
		return startTs;
	}
	public void setStartTs(Long startTs) {
		this.startTs = startTs;
	}
	public Long getEndTs() {
		return endTs;
	}
	public void setEndTs(Long endTs) {
		this.endTs = endTs;
	}
	
	
	
}
