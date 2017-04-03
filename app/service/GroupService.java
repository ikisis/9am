package service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.vo.Email;
import controllers.vo.UpdateMemberProfile;
import domain.GroupAccount;
import domain.GroupDomain;
import domain.GroupMember;
import domain.GroupMemberPhoto;
import domain.GroupMemberProfile;
import s.l.y.dao.Inserter;
import s.l.y.dao.Selector;
import sbb.seed.util.GUID;
import sbb.seed.util.QuietlyUtils;
import sbb.seed.util.TimeUtils;
import sbb.seed.util.Val;
import sbb.seed.util.Val2;

public class GroupService {

	public static String register(Connection c, String groupName, String publicYn, String autuJoinYn, String emailAddress) throws SQLException{
		
		String guid = GUID.next();
						
		Inserter.insert(c, new GroupAccount(guid, groupName, publicYn, autuJoinYn, "N", TimeUtils.timeStamp()));
		
		Email email = Email.of(emailAddress);
		
		Inserter.insert(c, new GroupDomain(guid, email.getDomain()));
		
		Selector.single(
				c, 
				() -> "select user_guid from user_email where id = ? and domain = ?", 
				p -> p
					.bind(email.getId())
					.bind(email.getDomain()), 
				r -> Inserter.insert(c, new GroupMember(guid, r.getString("user_guid"), "Y", TimeUtils.timeStamp()))
				);
		
		return guid;
	}
	
	public static String getGroupGuid(Connection c, String emailDomain) throws SQLException{
		Val<String> ret = new Val<>();
		
		Selector.single(c, () -> "select group_guid from group_domain where domain = ?", 
				p -> p.bind(emailDomain),
				r -> r.getStringOpt("group_guid").ifPresent(a -> {
					ret.set(a);
				}));
		
		return ret.get();
	}
	
	public static void setCreatedGroup(Connection c, String emailDomain) throws SQLException{
		
		String guid = getGroupGuid(c, emailDomain);
		
		Selector.forUpdate(c, () -> "update group_account set created = 'Y' where guid = ?", p -> p.bind(guid));
	}
	
	public static boolean isCreatedGroup(Connection c, String emailDomain) throws SQLException{
		
		Val<Boolean> ret = new Val<>(false);
		
		Selector.single(c, 
				() -> "select a.guid as guid from group_account a, group_domain b where a.guid = b.group_guid and created = 'Y' and b.domain = ?", 
				p -> p.bind(emailDomain), 
				r -> r.getStringOpt("guid").ifPresent(a -> {
					ret.set(true);
				}));
		
		return ret.get();
	}
	
	public static boolean isRegisteredGroup(Connection c, String emailDomain) throws SQLException{
		Val<Boolean> ret = new Val<>(false);
		
		System.out.println(emailDomain);
		
		Selector.single(c, () -> "select group_guid from group_domain where domain = ?", 
				p -> p.bind(emailDomain),
				r -> r.getStringOpt("group_guid").ifPresent(a -> {
					ret.set(true);
				}));
		System.out.println(ret.get());
		return ret.get();
	}
	
	public static void join(Connection c, String groupGuid, String userGuid) throws SQLException{
				
		Inserter.insert(c, new GroupMember(groupGuid, userGuid, "N", TimeUtils.timeStamp()));
		
	}
	
	public static int updateMemberProfile(Connection c, UpdateMemberProfile ump) throws SQLException{
		
		GroupMemberProfile set = ump.getProfile(), where = new GroupMemberProfile();
		

		where.setGroupGuid(ump.getGroupGuid());
		
		where.setEmail(ump.getEmail());
		
		int ret = Selector.update(c, set, where);
		
		if(ret < 1){
			
			GroupMemberProfile gmp = ump.getProfile();
			
			gmp.setGroupGuid(ump.getGroupGuid());
			
			gmp.setEmail(ump.getEmail());
			
			Inserter.insert(c, gmp);
			
			Inserter.insert(c, new GroupMemberPhoto(ump.getGroupGuid(), ump.getEmail()));	

			
			ret = 1;
		}
		
		return ret;
	}
	

	
	public static GroupMemberProfile getMemberProfile(Connection c, String groupGuid, String email) throws SQLException{
		
		final Val<GroupMemberProfile> result = new Val<>();
		Selector.single(c, 
				()->"select * from group_member_profile where group_guid = ? and email = ?", 
				p ->p
					.bind(groupGuid)
					.bind(email), 
				r->{
					
					GroupMemberProfile gmp = new GroupMemberProfile();
					gmp.setEmail(email);
					gmp.setName(r.getString("name"));
					gmp.setBirthyear(r.getInteger("birthyear"));
					gmp.setBirthmonth(r.getInteger("birthmonth"));
					gmp.setBirthday(r.getInteger("birthday"));
					gmp.setWhatido(r.getString("whatido"));
					gmp.setTeam(r.getString("team"));
					gmp.setPosition(r.getString("position"));
					gmp.setResidence(r.getString("residence"));
					gmp.setPhone(r.getString("phone"));
					gmp.setSex(r.getString("sex"));
					
					result.set(gmp);
				}
				);
		
		
		return result.get();
		
	}
	
	public static Val2<byte[],String> getMemberPhoto(Connection c, String guid, String email) throws SQLException{
		
		Val2<byte[], String> ret = new Val2<>();
		

		Selector.single(c, 
				
				() -> "select photo, ext from group_member_photo where group_guid = ? and email = ?", 
				
				p -> p.bind(guid).bind(email), 
				
				r->{
					
					ret.setB(r.getString("ext"));
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					InputStream is = r.getBinaryStream("photo");
					
					byte[] buff = new byte[1024];
					
					int len = -1;
					
					try {
						
						while((len = is.read(buff)) != -1)
							baos.write(buff, 0, len);
						
						ret.setA(baos.toByteArray());
						
					} catch (Exception e) {
						
						throw new RuntimeException(e);
						
					} finally{		
						QuietlyUtils.close(is);
					}
					
				}
				);
		
		return ret;
	}
	
	public static String[] getGroupGuids(Connection c, String userGuid) throws SQLException{
		
		
		ArrayList<String> list = new ArrayList<>();
		
		Selector.fetch(c, () -> "select group_guid from group_member where user_guid = ?", p -> p.bind(userGuid), r -> {
			
			r.getStringOpt("group_guid")
			.ifPresent( s -> list.add(s));
			
			return true;
		});
		
		return list.toArray(new String[list.size()]);
		
	}
	
}
