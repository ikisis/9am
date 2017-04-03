package service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import domain.BdayCardControll;
import domain.GroupCardComment;
import domain.GroupCardNews;
import domain.GroupCardPeople;
import domain.GroupCardTag;
import domain.GroupCardTagAgree;
import domain.GroupCardTimeline;
import s.l.y.dao.Inserter;
import s.l.y.dao.Selector;
import sbb.seed.concurrent.ThreadPooledExecutor;
import sbb.seed.consts.SimpleConstants;
import sbb.seed.exception.SBBMessage;
import sbb.seed.util.DateUtils;
import sbb.seed.util.GUID;
import sbb.seed.util.QuietlyUtils;
import sbb.seed.util.TimeUtils;
import sbb.seed.util.Val;
import service.vo.CardComment;
import service.vo.NewsCard;
import service.vo.CardTag;
import service.vo.CardThumbnail;

public class CardService {

	public static void createPeopleBirthDayCards(Connection c, int year, int month, int day) throws SQLException {

		Selector.fetch(c, () -> "select guid from group_account", p -> {
		} , r -> {

			String groupGuid = r.getString("guid");

			String bday = DateUtils.formatString8(year, month, day);

			Selector.single(c,
					() -> "select count(*) as count from bday_card_controll where group_guid = ? and birthday = ?",

					p -> p.bind(groupGuid).bind(bday),

					r2 -> {
				try {
					if (r2.getInteger("count") == 0) {

						String guid = GUID.next();

						Inserter.insert(c,
								new BdayCardControll(groupGuid, bday, "S", guid, TimeUtils.timeStamp(), null));

						ThreadPooledExecutor.run(() -> {

							try {
								createPeopleBirthDayCards(c, groupGuid, month, day, guid);
							} catch (SQLException e) {

								throw new SBBMessage("failed creating birthday card");

							}

							updateBdayCardControll(c, TimeUtils.timeStamp(), groupGuid, bday);

						});

					}
				} catch (SQLException ignored) {
					ignored.getSQLState();
					System.out.println(ignored.getMessage() + "//" + ignored.getErrorCode());

				}
			});

			return true;

		});

	}

	private static void updateBdayCardControll(Connection c, long endTime, String groupGuid, String bday) {
		try {
			Selector.forUpdate(c,
					() -> "update bday_card_controll set status = 'E' , end_ts = ? where group_guid = ? and birthday = ?",
					p -> p.bind(TimeUtils.timeStamp()).bind(groupGuid).bind(bday));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void createNewsCard(Connection c, String groupGuid, NewsCard newsCard) throws SQLException{
		
		String cardGuid = GUID.next();
		
		Inserter.insert(c, new GroupCardNews(groupGuid, cardGuid, "News", "Scrap", 
				newsCard.getSourceUrl(), newsCard.getTitle(), newsCard.getImageUrl(), newsCard.getThumbnail(), "system", TimeUtils.timeStamp()));
		
		
		Inserter.insert(c, new GroupCardTimeline(groupGuid, cardGuid, TimeUtils.timeStamp(), "News"));

	}

	public static void createPeopleBirthDayCards(Connection c, String groupGuid, int birthmonth, int birthday,
			String guid) throws SQLException {

		Selector.fetch(c,

				() -> "select email from group_member_profile where group_guid = ? and birthmonth = ? and birthday = ?", 
				
				p -> p.bind(groupGuid)
				.bind(birthmonth)
				.bind(birthday), 
				
				r -> {
					
					String cardGuid = GUID.next();
					
					String email = r.getString("email");
					
					Long ts = TimeUtils.timeStamp();
					
					Inserter.insert(c, new GroupCardPeople(groupGuid, cardGuid, "Congratulations", "Birthday", email, "", "system", ts));
					
					Inserter.insert(c, new GroupCardTimeline(groupGuid, cardGuid, ts, "People"));
					
					return true;

				});

	}
	
	public static String createTag(Connection c, String groupGuid, String cardGuid, String tag, String userGuid) throws SQLException{
		
		Val<String> tagGuid = new Val<>(GUID.next());
		
		try{
			
			Inserter.insert(c, new GroupCardTag(groupGuid, cardGuid, tag, tagGuid.get(), userGuid, TimeUtils.timeStamp()));
			
		}catch(BatchUpdateException e){
			
			Selector.single(c,
					
					()->"select tag_guid from group_card_tag where group_guid = ? and card_guid = ? and tag = ?", 
					
					p -> p.bind(groupGuid)
					.bind(cardGuid)
					.bind(tag), 
					
					r -> tagGuid.set(r.getString("tag_guid"))
			
					);
			
		}
		
		Inserter.insert(c, new GroupCardTagAgree(tagGuid.get(), userGuid));
		
		return tagGuid.get();
		
	}
	
	public static void agreeTag(Connection c, String tagGuid, String userGuid) throws SQLException{
		
		Inserter.insert(c, new GroupCardTagAgree(tagGuid, userGuid));

	}
	
	public static void cancelTagAgreement(Connection c, String tagGuid, String userGuid) throws SQLException{
				
		Selector.forUpdate(c, 
				() -> "delete from group_card_tag_agree where tag_guid = ? and user_guid = ?", 
				p -> p.bind(tagGuid).bind(userGuid) 
				);
	}
	
	public static String registerCardComment(Connection c, GroupCardComment gcc) throws SQLException{
		
		gcc.setTs(TimeUtils.timeStamp());
		
		gcc.setCommentGuid(GUID.next());
		
		Inserter.insert(c, gcc);
		
		return gcc.getCommentGuid();
		
	}
	
	public static void deleteCardComment(Connection c, String commentGuid) throws SQLException{
		Selector.forUpdate(c, 
				() -> "delete from group_card_comment where comment_guid = ?", 
				p -> p.bind(commentGuid) 
				);
	}
	
	public static List<CardThumbnail> getCardsByCategory(Connection c, String groupGuid, String userGuid, String category) throws SQLException{
		
		ArrayList<CardThumbnail> result = new ArrayList<>();
		
		Selector.fetch(c, 
				() -> "select card_guid, category, ts from group_card_timeline where group_guid = ? and category = ?", 
				p -> p.bind(groupGuid).bind(category), 
				r -> {
			
					
					CardThumbnail card = new CardThumbnail();
					
					result.add(card);
					
					card.setCardGuid(r.getString("card_guid"));
					
					card.setCategory(r.getString("category"));
					
					card.setTs(Long.parseLong(r.getString("ts")));
					
					if(card.getCategory().equals(SimpleConstants.CARD_CONTENTS_PEOPLE)){
						
						fillPeopleCardContents(c, card, groupGuid, userGuid);
						
					}else if(card.getCategory().equals(SimpleConstants.CARD_CONTENTS_NEWS)){
						
						fillNewsCardContents(c, card, groupGuid, userGuid);
						
					}
					return true;
		
				});
		
		return result;
		
	}
	
	private static void fillNewsCardContents(Connection c, CardThumbnail ct, String groupGuid, String userGuid) throws SQLException {
		Selector.single(c, 
				() -> "select detailed_category, type, source_url, title, image_url, thumbnail from group_card_news where card_guid = ?", 
				p -> p.bind(ct.getCardGuid()), 
				r -> {
					
					ct.setDetailedCategory(r.getString("detailed_category"));
					ct.setType(r.getString("type"));
					
					NewsCard cardNews = new NewsCard();
					cardNews.setSourceUrl(r.getString("source_url"));
					cardNews.setTitle(r.getString("title"));
					cardNews.setImageUrl(r.getString("image_url"));
					cardNews.setThumbnail(r.getString("thumbnail"));
					
					ct.setContents(cardNews);
				});
		
		Selector.single(c, 
				() -> {					
					return new StringBuilder()
							.append("select count(*) as c from(")
							.append("select a.tag_guid from group_card_tag a, group_card_tag_agree b ")
							.append("where a.tag_guid = b.tag_guid and a.card_guid = ? ")
							.append("group by a.tag_guid)a")
							.toString();
					}, 
				p -> p.bind(ct.getCardGuid()), 
				r -> {
					ct.setExpressionCount(r.getInteger("c"));
					});
		
		Selector.single(c, () -> "select count(*) as c from group_card_comment where card_guid = ?", p -> p.bind(ct.getCardGuid()), r -> {
			ct.setCommentCount(r.getInteger("c"));
		});
	}

	public static List<CardThumbnail> getAllCards(Connection c, String groupGuid, String userGuid) throws SQLException{
		
		ArrayList<CardThumbnail> result = new ArrayList<>();
		
		Selector.fetch(c, 
				() -> "select card_guid, category, ts from group_card_timeline where group_guid = ?", 
				p -> p.bind(groupGuid), 
				r -> {
			
					
					CardThumbnail card = new CardThumbnail();
					
					result.add(card);
					
					card.setCardGuid(r.getString("card_guid"));
					
					card.setCategory(r.getString("category"));
					
					card.setTs(Long.parseLong(r.getString("ts")));
					
					if(card.getCategory().equals(SimpleConstants.CARD_CONTENTS_PEOPLE))
						fillPeopleCardContents(c, card, groupGuid, userGuid);	
					if(card.getCategory().equals(SimpleConstants.CARD_CONTENTS_NEWS))
						fillNewsCardContents(c, card, groupGuid, userGuid);
					return true;
		
				});
		
		return result;
		
	}
	
	private static void fillPeopleCardContents(Connection c, CardThumbnail ct, String groupGuid, String userGuid) throws SQLException{
		
		Val<String> email = new Val<>();
		
		Selector.single(c, 
				() -> "select detailed_category, type, email from group_card_people where card_guid = ?", 
				
				p -> p.bind(ct.getCardGuid()), 
				
				r -> {
					
					ct.setDetailedCategory(r.getString("detailed_category"));
					ct.setType(r.getString("type"));
					email.set(r.getString("email"));
					
				});
		
		ct.setContents(GroupService.getMemberProfile(c, groupGuid, email.get()));
		
		Selector.single(c, 
				() -> {					
					return new StringBuilder()
							.append("select count(*) as c from(")
							.append("select a.tag_guid from group_card_tag a, group_card_tag_agree b ")
							.append("where a.tag_guid = b.tag_guid and a.card_guid = ? ")
							.append("group by a.tag_guid)a")
							.toString();
					}, 
				p -> p.bind(ct.getCardGuid()), 
				r -> {
					ct.setExpressionCount(r.getInteger("c"));
					});
		
		Selector.single(c, () -> "select count(*) as c from group_card_comment where card_guid = ?", p -> p.bind(ct.getCardGuid()), r -> {
			ct.setCommentCount(r.getInteger("c"));
		});
		
	}
	
	public static Set<CardTag> getCardTagSet(Connection c, String cardGuid, String userGuid ) throws SQLException{
		
		Set<CardTag> ret = new LinkedHashSet<>();
		
		Selector.fetch(c, ()->"select tag_guid, tag from group_card_tag where card_guid = ? order by ts", p -> p.bind(cardGuid), r -> {
			
			String tagGuid = r.getString("tag_guid");
			
			Selector.single(c, () -> "select count(*) as c from group_card_tag_agree where tag_guid = ?", p -> p.bind(tagGuid), r2 -> {
				
				int count = r2.getInteger("c");
				
				if(count > 0){
					
					CardTag tag = new CardTag();
					
					tag.setTagGuid(tagGuid);
					
					tag.setTag(r.getString("tag"));
					
					tag.setAgreeCount(count);
					
					Selector.single(c, () -> "select user_guid from group_card_tag_agree where tag_guid = ? and user_guid = ?", p -> p.bind(tagGuid).bind(userGuid), r3 -> tag.setAgreed(true));
				
					ret.add(tag);
				}
				
			});
			
			return true;
		});	
				
		return ret;
	}
	
	public static List<CardComment> getCardComments(Connection c, String cardGuid, String groupGuid) throws SQLException{
		
		List<CardComment> ret = new ArrayList<>();
		
		Selector.fetch(c, () -> "select comment_guid, comment, user_guid, ts from group_card_comment where card_guid = ? order by ts", p -> p.bind(cardGuid), r -> {
			
			CardComment cc = new CardComment();
			
			cc.setCommentGuid(r.getString("comment_guid"));
			
			cc.setComment(r.getString("comment"));
			
			cc.setUserGuid(r.getString("user_guid"));
			
			cc.setTs(Long.parseLong(r.getString("ts")));
			
			Selector.single(c, 
					() -> "select email, name from group_member_profile where group_guid = ? and user_guid = ?", 
					p -> p.bind(groupGuid).bind(cc.getUserGuid()), 
					r2 -> {
						cc.setEmail(r2.getString("email"));
						cc.setName(r2.getString("name"));
					}
					);
			
			ret.add(cc);
						
			return true;
		});
		
		return ret;
		
	}
	
	public static String getNewsCardContents(Connection c, String cardGuid) throws SQLException{
		
		Val<String> ret = new Val<>();
		
		Selector.single(c, 
				() -> "select contents from group_card_news_contents where card_guid = ?", 
				p -> p.bind(cardGuid), 
				r -> {
					
					InputStream is = null;
					
					try {
						is = r.getBinaryStream("contents");
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						
						byte[] buff = new byte[1024];
						
						int len = -1;
					
						while((len = is.read(buff))!= -1){
							baos.write(buff, 0, len);
						}
						
						ret.set(new String(baos.toByteArray()));
						
					} catch (Exception e) {
						throw new RuntimeException(e);
					} finally{
						QuietlyUtils.close(is);
					}
					
				});
		
		return ret.get();
	}
	

}
