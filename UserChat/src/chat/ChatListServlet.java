package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatSubmitServlet
 */
//@WebServlet("/ChatSubmitServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		String chatContent = request.getParameter("chatContent");
		if (fromID == null || fromID.equals("") || toID == null || toID.equals("") 
				|| listType == null || listType.equals("")) {
			response.getWriter().write("0");
		} else if (listType.equals("ten")) {
			response.getWriter().write(getTen(fromID, toID));
		} else {
			try {
				response.getWriter().write(getID(fromID, toID, listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 10);
		if (chatList.size() == 0)
			return "";
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("[{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("[{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("[{\"value\": \"" + chatList.get(i).getChatTime() + "\"}] ");
			if (i != chatList.size() - 1) result.append(","); 
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}
	
	public String getID(String fromID, String toID, String listType) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 10);
		if (chatList.size() == 0)
			return "";
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("[{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("[{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("[{\"value\": \"" + chatList.get(i).getChatTime() + "\"}] ");
			if (i != chatList.size() - 1) result.append(","); 
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}

}
