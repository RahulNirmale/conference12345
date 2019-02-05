package com.google.devrel.training.conference.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.google.devrel.training.conference.service.OfyService.ofy;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.common.base.Joiner;
import com.google.devrel.training.conference.Constants;
import com.google.devrel.training.conference.domain.Conference;

/**
 * Servlet implementation class SetAnnouncementServlet
 */
public class SetAnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAnnouncementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		Iterable<Conference> iterable = ofy().load().type(Conference.class)
				.filter("seatsAvailable <",5);
		
				
				
	     List<String> conferenceNames = new ArrayList<>(0);
		  for(Conference conference : iterable) {
			  conferenceNames.add(conference.getName());
		  }
		  
		  if (conferenceNames.size() > 0) {
			  
			  StringBuilder announcementStringBuilder =new StringBuilder(
					 "Oh look! last chance to attend! The following conferences are nearly sold out: " );
			  
			    Joiner joiner =Joiner.on(", ").skipNulls();
			    announcementStringBuilder.append(joiner.join(conferenceNames));
			    
			    
			    MemcacheService memcacheService =MemcacheServiceFactory.getMemcacheService();
			    
			    String announcementKey = Constants.MEMCACHE_ANNOUNCEMENTS_KEY;
			    String announcementText = announcementStringBuilder.toString();
			    
			    memcacheService.put(announcementKey, announcementText);
			  
			  
		  }
		  
		response.setStatus(204);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
