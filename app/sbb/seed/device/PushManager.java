package sbb.seed.device;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;

public class PushManager {

	final private WSClient ws = WS.client();
	
	private volatile static PushManager manager;
	
	private PushManager(){}
	
	public static PushManager getInstance(){
		
		if(manager == null)
			synchronized (PushManager.class) {
				if(manager == null)
					manager = new PushManager();
			}
		
		return manager;
	}
	
	class GCMJson{
		
		class Data{
			int cardCount;

			public int getCardCount() {
				return cardCount;
			}

			public void setCardCount(int cardCount) {
				this.cardCount = cardCount;
			}
			
		}
		
		private String to;
		
		final private Data data = new Data();
		
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public Data getData() {
			return data;
		}
		
	}
	
	public void pushNewCardsToGCM(String pushId, int cardCount){
		
		GCMJson j = new GCMJson();
		
		j.setTo(pushId);
		
		j.getData().setCardCount(cardCount);
		
		ws.url("https://gcm-http.googleapis.com/gcm/send")
		.setContentType("application/json")
		.setHeader("Authorization", "key=AIzaSyDTgs0YrRsO5DCqSSXd7fGszM6OGoZetTw")
		.post(Json.toJson(j));
		
		
	}
	
}
