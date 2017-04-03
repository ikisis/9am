package service;

import java.sql.Connection;
import java.sql.SQLException;

import controllers.vo.Email;
import controllers.vo.Login;
import domain.User;
import domain.UserEmail;
import domain.UserPushInfo;
import play.libs.mailer.MailerClient;
import s.l.y.dao.Inserter;
import s.l.y.dao.Selector;
import sbb.seed.consts.SimpleConstants;
import sbb.seed.util.GUID;
import sbb.seed.util.RandomUtils;
import sbb.seed.util.TimeUtils;
import sbb.seed.util.Val;

public class UserService {

	public static void register(Connection c, Email email, String password) throws SQLException{
		
		String guid = GUID.next();
		
		Inserter.insert(c, new User[]{new User(guid, password, TimeUtils.timeStamp())});
		
		Inserter.insert(c, new UserEmail(guid, email.getId(), email.getDomain(), "N"));
	}
	
	public static boolean isRegisteredUser(Connection c, Email email) throws SQLException{
		
		Val<Boolean> ret = new Val<>(false);
		
		Selector.single(c, () -> "select user_guid from user_email where id = ? and domain = ? ", 
				p -> p.bind(email.getId()).bind(email.getDomain()), 
				r -> r.getStringOpt("user_guid").ifPresent((a) -> ret.set(true)));
		
		return ret.get();
	}
	
	public static String login(Connection c, Login l) throws SQLException{
		
		final Val<String> result = new Val<>();
		
		Email email = Email.of(l.getEmail());
		
		Selector.single(c, 
				()->
				"select "
				+ "a.guid as guid "
				+ "from user a, user_email b "
				+ "where a.guid = b.user_guid "
				+ "and a.password = ? "
				+ "and b.id = ? "
				+ "and b.domain = ?",
				p -> p
					.bind(l.getPassword())
					.bind(email.getId())
					.bind(email.getDomain()),
				r -> r.getStringOpt("guid").ifPresent( s -> result.set(s) )
				);
		
		return result.get();
	}
	
	public static String loggedIn(Connection c, String email) throws SQLException{
		
		Val<String> ret = Val.of(null);
		
		Selector.single(c, ()->"select name from group_member_profile where email = ?",
				p->p.bind(email), 
				r-> ret.set(r.getString("name"))
				);
		
		return ret.get();
	}
	
	public static void setVerifiedEmail(Connection c, Email email)  throws SQLException{
		Selector.forUpdate(
				c,
				()-> 
					"update user_email set verified = ? where id = ? and domain = ?",
				p-> p
					.bind("Y")
					.bind(email.getId())
					.bind(email.getDomain())
				);
	}
	
	public static boolean isVerifiedEmail(Connection c, String email)  throws SQLException{
    	
		Email e = Email.of(email);
		
		final Val<Boolean> result = new Val<>(false);
		
    	Selector.single(
				c, 
				() -> "select verified from user_email where id = ? and domain = ? ", 
				p -> p
					.bind(e.getId())
					.bind(e.getDomain()), 
				r -> r
					.getStringOpt("verified")
					.ifPresent( s -> result.set(s.equals("Y")) )
				);
		
		return result.get();
	}
	
	public static void updatePassword(Connection c, String emailAddress, String password) throws SQLException{
				
		Email email = Email.of(emailAddress);
		
		Selector.single(c, () -> "select user_guid from user_email where id = ? and domain = ? ", 
				p -> p.bind(email.getId()).bind(email.getDomain()),
				r -> {
					r.getStringOpt("user_guid").ifPresent(a -> {
						
						try {
							Selector.forUpdate(c, () -> "update user set password = ? where guid = ? " , p -> p.bind(password).bind(a));
						} catch (Exception e) {throw new RuntimeException(e);}
						
					});
					
				});
		
		
	}
	
	public static void resetPassword(Connection c, MailerClient mailerClient, String emailAddress) throws SQLException{
		
		String newPassword = RandomUtils.getString(6);
		
		updatePassword(c, emailAddress, newPassword);
		
		play.libs.mailer.Email email = new play.libs.mailer.Email();
		
		email.setSubject("[9am] Confirm your new password");
		email.setFrom("noreply<sangwook.park@bankwareglobal.com>");
		email.addTo("<"+emailAddress+">");
		email.setBodyText("Your Email is changed : " + newPassword);
		
		mailerClient.send(email);

	}
	
	
	public static void verifyEmail(MailerClient mailerClient, String sendTo){
		
		play.libs.mailer.Email email = new play.libs.mailer.Email();
		
		email.setSubject("[9am] Verify your Email address please!");
		email.setFrom("noreply<sangwook.park@bankwareglobal.com>");
		email.addTo("<"+sendTo+">");
		email.setBodyHtml("<a href='http://"+SimpleConstants.HOST_NAME+":" + SimpleConstants.HOST_PORT +"/email/verified/true/"+ sendTo +"'>Verify Your Email</a>");
		
		
		//email.setBodyHtml("<a href='http://192.168.20.45:" + SimpleConstants.HOST_PORT +"/email/verified/true/"+ sendTo +"'>Verify Your Email</a>");

		mailerClient.send(email);		
		
	}
	
	public static void registerPushInfo(Connection c, UserPushInfo info) throws SQLException{
		Inserter.insert(c, info);
	}
}
