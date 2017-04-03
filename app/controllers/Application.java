package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.inject.Inject;

import controllers.vo.AgreeTag;
import controllers.vo.CreateBirthdayCard;
import controllers.vo.CreateNewsCard;
import controllers.vo.CreateTag;
import controllers.vo.Email;
import controllers.vo.Login;
import controllers.vo.RegisterGroup;
import controllers.vo.RegisterMember;
import controllers.vo.RegisterUser;
import controllers.vo.UpdateMemberProfile;
import controllers.vo.Verified;
import domain.GroupCardComment;
import domain.GroupMemberProfile;
import domain.UserPushInfo;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.libs.ws.WSClient;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;
import s.l.y.dao.Selector;
import sbb.seed.SimpleController;
import sbb.seed.consts.SimpleConstants;
import sbb.seed.device.PushManager;
import sbb.seed.exception.SBBMessage;
import sbb.seed.util.QuietlyUtils;
import sbb.seed.util.Val;
import sbb.seed.util.Val2;
import service.CardService;
import service.GroupService;
import service.UserService;
import service.vo.CardComment;
import service.vo.NewsCard;
import service.vo.CardTag;
import service.vo.CardThumbnail;

public class Application extends SimpleController {

	public Result registerUser() {

		return okWithDS(RegisterUser.class, (c, ru) ->{
			
			Email email = Email.of(ru.getEmail());
			
			if(!UserService.isRegisteredUser(c, email))
				UserService.register(c, email, ru.getPassword());
			else
				throw new SBBMessage(999);//TODO
			
		});

	}

	public Result login() {

		Login result = new Login();

		runWithDS(Login.class, (c, l) -> {

			Optional.ofNullable(UserService.login(c, l)).ifPresent(s -> result.setUserGuid(s));

			if (!UserService.isVerifiedEmail(c, l.getEmail()))
				throw new SBBMessage(999); // TODO define code

			result.setName(UserService.loggedIn(c, l.getEmail()));
			
			result.setGroupGuids(GroupService.getGroupGuids(c, result.getUserGuid()));
			
			if(result.getGroupGuids().length == 0){
				Optional
				.ofNullable(GroupService.getGroupGuid(c, Email.of(l.getEmail()).getDomain()))
				.ifPresent(a -> {
					try {
						GroupService.join(c, a, result.getUserGuid());
						result.setGroupGuids(new String[]{a});
						
						UpdateMemberProfile ump = new UpdateMemberProfile();
						ump.setProfile(new GroupMemberProfile());

						ump.setGroupGuid(a);
						ump.setEmail(l.getEmail());

						ump.getProfile().setUserGuid(result.getUserGuid());
						
						GroupService.updateMemberProfile(c, ump);
						
					} catch (Exception e) {	throw new RuntimeException(e);	}
				});
			}
			
			
		});

		return ok(Json.toJson(wrap(result)));
	}
	
	@Inject MailerClient mailerClient;

	public Result verifyEmail() {
		
		

		return okWithDS(Email.class, (c, e) -> {
			
			UserService.verifyEmail(this.mailerClient,e.getEmail());
			
		});

	}

	public Result setVerifiedEmail() {

		return okWithDS(Email.class, UserService::setVerifiedEmail);

	}

	public Result isVerifiedEmail(String email) {

		Val<Boolean> result = new Val<>(false);

		runWithDS((c) -> result.set(UserService.isVerifiedEmail(c, email)));
		
		return ok(Json.toJson(wrap(new Verified(result.get()))));
	}
	
	public Result changePassword() {
		
		return okWithDS(RegisterUser.class, (c, ru) ->{
			UserService.updatePassword(c, ru.getEmail(), ru.getPassword());
		});
	}

	public Result createGroup() {
		
		Val<String> guid = new Val<>();

		runWithDS(RegisterGroup.class, (c, rg) -> {
			
			System.out.println(rg);
			
			Email email = Email.of(rg.getEmail());
			
			if(!GroupService.isRegisteredGroup(c, email.getDomain()))
				guid.set(GroupService.register(c, rg.getName(), rg.getPublicYn(), rg.getAutoJoinYn(), rg.getEmail()));
			else
				throw new SBBMessage(999);//TODO
			
		});
		
		return ok(Json.toJson(wrap(new Object(){
			
			final String groupGuid = guid.get();
			
			@SuppressWarnings("unused")
			public String getGroupGuid(){
				return this.groupGuid;
			}
		})));

	}
	
	public Result setCreatedGroup(){
		
		
		return okWithDS(Email.class, (c, email) -> GroupService.setCreatedGroup(c, email.getDomain()));
		
		
	}
	
	public Result isCreatedGroup(String email){
		
		Val<Boolean> result = new Val<>();
		
		runWithDS(c -> {
			result.set(GroupService.isCreatedGroup(c, Email.of(email).getDomain()));
		});
		
		return ok(Json.toJson(wrap(new Object(){
			final boolean isCreated = result.get();

			@SuppressWarnings("unused")
			public boolean isCreated() {
				return isCreated;
			}
			
		})));
		
	}

	public Result registerMember() {

		return okWithDS(RegisterMember.class, (c, rm) -> {

			GroupService.join(c, rm.getGroupGuid(), rm.getUserGuid());

			UpdateMemberProfile ump = new UpdateMemberProfile();
			ump.setProfile(new GroupMemberProfile());

			ump.setGroupGuid(rm.getGroupGuid());
			ump.setEmail(rm.getEmail());

			ump.getProfile().setName(rm.getName());
			ump.getProfile().setUserGuid(rm.getUserGuid());
			
			GroupService.updateMemberProfile(c, ump);

		});

	}

	public Result updateMemberProfile() {

		return okWithDS(UpdateMemberProfile.class, GroupService::updateMemberProfile);

	}

	public Result getMemberProfile(String groupGuid, String email) {

		Val<GroupMemberProfile> result = new Val<>();

		runWithDS(c -> result.set(GroupService.getMemberProfile(c, groupGuid, email)));

		return ok(Json.toJson(wrap(result.get())));

	}

	public Result updateMemberPhoto() {

		Optional.ofNullable(request().body().asMultipartFormData()).ifPresent(body -> {

			FilePart fp = body.getFile("picture");

			String[] fnTokens = fp.getFilename().split("[.]");

			String ext = fnTokens[fnTokens.length - 1];

			String groupGuid = Optional.ofNullable(body.asFormUrlEncoded().get("groupGuid")).filter(s -> s.length == 1)
					.flatMap(s -> Optional.ofNullable(s[0])).get();

			String email = Optional.ofNullable(body.asFormUrlEncoded().get("email")).filter(s -> s.length == 1)
					.flatMap(s -> Optional.ofNullable(s[0])).get();

			File file = fp.getFile();

			final Val<BufferedInputStream> bis = new Val<>();

			try {
				bis.set(new BufferedInputStream(new FileInputStream(file)));

				runWithDS(c -> {

					Selector.forUpdate(c,
							() -> "update group_member_photo set photo = ?, ext = ? where group_guid = ? and email = ?",
							p -> {
						p.bind(bis.get(), file.length()).bind(ext).bind(groupGuid).bind(email);
					});

				});
			} catch (Exception e) {

				throw new RuntimeException(e);

			} finally {

				QuietlyUtils.close(bis.get());

			}

		});

		return ok();
	}

	public Result getMemberPhoto(String groupGuid, String email) {

		Val<byte[]> result = new Val<>();

		runWithDS(c -> {

			Val2<byte[], String> ret = GroupService.getMemberPhoto(c, groupGuid, email);

			result.set(ret.getA());

			if (ret.getB() != null)
				response().setContentType("image/" + ret.getB());

		});

		ByteChunks.whenReady(o -> o.write(result.get()));

		if (result.get() != null)
			return ok(result.get());
		else
			return ok("no image");
	}

	public Result createPeopleBirthdayCard() {
		return okWithDS(CreateBirthdayCard.class,
				(c, cbc) -> CardService.createPeopleBirthDayCards(c, cbc.getYear(), cbc.getMonth(), cbc.getDay()));
	}
	
	public Result createCardTag(){
		
		Val<String> ret = new Val<>();
		
		runWithDS(CreateTag.class, (c, ct) ->{
			ret.set(CardService.createTag(c, ct.getGroupGuid(), ct.getCardGuid(), ct.getTag(), ct.getUserGuid()));
		});
		
		return ok(Json.toJson(wrap(new Object(){
			
			final private String tagGuid = ret.get();

			@SuppressWarnings("unused")
			public String getTagGuid() {
				return tagGuid;
			}
			
		})));
	}
	
	public Result addTagAgreement(){
		
		runWithDS(AgreeTag.class, (c, at) -> {
			CardService.agreeTag(c, at.getTagGuid(), at.getUserGuid());
		});
		
		return ok();
	}
	
	public Result deleteTagAgreement(String tagGuid, String userGuid){
		
		runWithDS(c -> {
			CardService.cancelTagAgreement(c, tagGuid, userGuid);
		});
		
		return ok();
	}
	
	public Result registerCardComment(){
		
		Val<String> ret = new Val<>();
		
		runWithDS(GroupCardComment.class, (c, gcc) -> ret.set(CardService.registerCardComment(c, gcc)));
				
		return ok(Json.toJson(wrap(new Object(){
			final private String commentGuid = ret.get();
			@SuppressWarnings("unused")
			public String getCommentGuid(){
				return commentGuid;
			}
		})));
	}
	
	public Result deleteCardComment(String commentGuid){
		
		return okWithDS(c -> CardService.deleteCardComment(c, commentGuid));
		
	}
	
	public Result getCards(String groupGuid, String userGuid, String category){
		
		Val<List<CardThumbnail>> result = new Val<>();
		
		runWithDS(c -> {
			if(category.equals(SimpleConstants.CARD_CONTENTS_ALL))
				result.set(CardService.getAllCards(c, groupGuid, userGuid));
			else
				result.set(CardService.getCardsByCategory(c, groupGuid, userGuid, category));
			
		});
		
		return ok(Json.toJson(wrap(result.get())));
	}
	
	public Result getCardTagSet(String cardGuid, String userGuid){
		
		Val<Set<CardTag>> ret = new Val<>();
		
		runWithDS(c -> 
			ret.set(CardService.getCardTagSet(c, cardGuid, userGuid))
			);
		
		return ok(Json.toJson(wrap(ret.get())));
	}
	
	public Result getCardCommentList(String cardGuid, String groupGuid){
		
		Val<List<CardComment>> ret = new Val<>();
		
		runWithDS(c -> 
			ret.set(CardService.getCardComments(c, cardGuid, groupGuid))
		);
				
		return ok(Json.toJson(wrap(ret.get())));
	}
	
	public Result emailVerifiedByGet(String email){
				
		runWithDS(c -> {
			UserService.setVerifiedEmail(c, Email.of(email));
		});
		
		return ok("verified!");
	}
	
	public Result createNewsCard(){
		return okWithDS(CreateNewsCard.class, (c, cnc) ->{
			CardService.createNewsCard(c, cnc.getGroupGuid(), new NewsCard(cnc.getSourceUrl(), cnc.getTitle(), cnc.getImageUrl(), cnc.getThumbnail()));
		});
		
	}
	
	public Result getNewsCardContents(String cardGuid){
		Val<String> result = new Val<>();
		
		runWithDS(c -> {
			result.set(CardService.getNewsCardContents(c, cardGuid));
		});
		
		return ok(Json.toJson(wrap(new Object(){
			
			final String contents = result.get();

			@SuppressWarnings("unused")
			public String getContents() {
				return contents;
			}
			
		})));
	}
	
	public Result resetPassword(){
		return okWithDS(Email.class,(c, email) -> {
			UserService.resetPassword(c, mailerClient, email.getEmail());
		});
	}
	
	public Result registerUserPushInfo(){
		return okWithDS(UserPushInfo.class, (c, upi) -> {
			UserService.registerPushInfo(c, upi);
		});
	}
	
	@Inject WSClient ws;
	
	
	public Result pushTest(){
		
		okWithDS(UserPushInfo.class, (c, upi) -> {
			
			PushManager.getInstance().pushNewCardsToGCM(upi.getPushId(), 3);
		});
		
		
		return ok();
	}
	
}
