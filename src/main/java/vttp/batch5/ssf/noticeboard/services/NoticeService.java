package vttp.batch5.ssf.noticeboard.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonArray;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {
	@Autowired
	private NoticeRepository noticeRepo;
	//public static final String HOST_URL="https://publishing-production-d35a.up.railway.app/"+"/notice";
	@Value("${host.url}")
    private String hostUrl;

	// // TODO: Task 3
	// // You can change the signature of this method by adding any number of parameters
	// // and return any type
	public List<Notice> notices;

	public JsonObject postToNoticeServer(Notice n) {
		//2. recreate json payload 
		
    	JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		//the prob is the category

		JsonArray category = Json.createArrayBuilder()
								.add("public notice")
								.add("Sport")
								.add("Meeting")
								.add("Garage Sale")
								.add("Others")
								.build();
    	for(Notice notice: notices){
        JsonObject j = Json.createObjectBuilder()
                            .add("title",notice.getTitle())
                            .add("poster",notice.getPoster())
                            .add("postDate",notice.getPostDate().getTime())
                            .add("categories", Json.createArrayBuilder()
														.add(category))
                            .add("text", notice.getText())
                            .build();
		arrBuilder.add(j);
		noticeRepo.insertNotices(j.getString("title"), j.toString());
		
		}

		//3. create requestentity
		RequestEntity<String> req = RequestEntity.post(hostUrl+"/notice")
												.header("Accept","application.json")
												.header("Content-Type","application.json")
												.body(arrBuilder.toString(),String.class); //put the request in here, change the void if necessary

		//4. resttemplate
		RestTemplate template = new RestTemplate();


		//5. response
		try{
			ResponseEntity<String> resp = template.exchange(req, String.class);
			String payload = resp.getBody();
			System.out.printf(">>>check payload: %s/n", payload); //from request


			//build the response
			String id = UUID.randomUUID().toString();
			long timestamp = java.time.Instant.now().getEpochSecond();
			if(payload==null){
				//unsuccessful
				String message = "Unsuccessful post creation";
				JsonObject unsuccessful = Json.createObjectBuilder()
											.add("message", message)
											.add("timestamp", timestamp)
											.build();
				return unsuccessful;

			} else {
				//successful //.ok
				JsonObject result = Json.createObjectBuilder()
										.add("id",id)
										.add("timestamp",timestamp)
										.build();
				
				
				return result;
			}				
		
		} catch (Exception ex){
			ex.printStackTrace();
			}
					return null;


	}

	//idk where to put for health check
	
	public String getRandomKey(){
		
		String key= noticeRepo.generateRandomKey();
		if(key!=null){
			return "successful";
		} else {
			return "unsuccessful";}
	}

}


