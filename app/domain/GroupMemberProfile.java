package domain;

public class GroupMemberProfile {
	private String groupGuid;
	private String email;
	private String userGuid;
	private String name;
	private Integer birthyear;
	private Integer birthmonth;
	private Integer birthday;
	private String whatido;
	private String team;
	private String position;
	private String residence;
	private String phone;
	private String sex;
	
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getGroupGuid() {
		return groupGuid;
	}
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBirthyear() {
		return birthyear;
	}
	public void setBirthyear(Integer birthyear) {
		this.birthyear = birthyear;
	}
	public Integer getBirthmonth() {
		return birthmonth;
	}
	public void setBirthmonth(Integer birthmonth) {
		this.birthmonth = birthmonth;
	}
	public Integer getBirthday() {
		return birthday;
	}
	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}
	public String getWhatido() {
		return whatido;
	}
	public void setWhatido(String whatido) {
		this.whatido = whatido;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "GroupMemberProfile [groupGuid=" + groupGuid + ", email=" + email + ", userGuid=" + userGuid + ", name="
				+ name + ", birthyear=" + birthyear + ", birthmonth=" + birthmonth + ", birthday=" + birthday
				+ ", whatido=" + whatido + ", team=" + team + ", position=" + position + ", residence=" + residence
				+ ", phone=" + phone + ", sex=" + sex + "]";
	}
	
	
	
	
}
